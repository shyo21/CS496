package com.example.proj1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class Frag1 extends Fragment {
    private Context mContext; //FragmentContext
    private RecyclerView mRecyclerView; //ContactRecyclerView
    private ArrayList<ContactData> mArrayList; //ContactArrayList
    private ContactAdapter mAdapter; //ContactAdapter
    private ConstraintLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.contact_layout, container, false);

        mLayout = myView.findViewById(R.id.layout);
        mRecyclerView = myView.findViewById(R.id.ContactRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mArrayList = new ArrayList<>();
        mContext = getActivity().getApplicationContext();
        mAdapter = new ContactAdapter(mContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        // ask permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkVerify();
        } else {
            startApp();
        }

        // load contact by asynctask
        LoadContactsAsync lca = new LoadContactsAsync();
        lca.execute();

        return myView;
    }

    public void startApp() { }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify() {
        // if permission not granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS },
                    1);
        } else {
            startApp();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int i=0; i<grantResults.length; ++i)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        // if permission denied
                        new AlertDialog.Builder(getActivity()).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        getActivity().finish();
                                    }
                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getActivity().getApplicationContext().getPackageName()));
                                getActivity().getApplicationContext().startActivity(intent);
                            }
                        }).setCancelable(false).show();

                        return;
                    }
                }
                startApp();
            }
        }
    }

    class LoadContactsAsync extends AsyncTask<Void, Void, ArrayList<ContactData>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(getActivity(), "Loading Contacts",
                    "Please Wait");
        }

        @Override
        protected ArrayList<ContactData> doInBackground(Void... params) {
            mArrayList = getContacts();
            return mArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ContactData> contacts) {
            super.onPostExecute(mArrayList);
            pd.cancel();
            mAdapter.notifyItemInserted (mArrayList.size ()-1);
        }
    }

    private ArrayList<ContactData> getContacts(){
        Cursor c = getActivity().getApplicationContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        while (c.moveToNext()) {

            String contactName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactData contactData = new ContactData();
            contactData.setName(contactName);
            contactData.setNumber(phNumber);
            mArrayList.add (contactData);
        }
        c.close();
        return mArrayList;
    }
}


