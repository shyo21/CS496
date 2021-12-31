package com.example.proj1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class Frag1 extends Fragment {
    private Context mContext;
    private ArrayList<ContactData> mArrayList;
    private ContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText dialog_name, dialog_number;
    private FloatingActionButton mFAB;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button loadBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.contact_layout, container, false);
        mContext = getActivity().getApplicationContext ();
        mRecyclerView = (RecyclerView) myView.findViewById (R.id.recycler);
        mFAB = myView.findViewById(R.id.floatingActionButton);
        mSwipeRefreshLayout = myView.findViewById(R.id.swiperefresh);
        //레이아웃메니저는 리사이클러뷰의 항목 배치를 어떻게 할지 정하고, 스크롤 동작도 정의한다.
        //수평/수직 리스트 LinearLayoutManager
        //그리드 리스트 GridLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager (mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager (layoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new ContactAdapter (mContext, mArrayList);
        mRecyclerView.setAdapter (mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        LoadContactsAyscn lca = new LoadContactsAyscn();
                        lca.execute();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        // permission and get contact
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkVerify();
        }
        else
        {
            startApp();
        }

        loadBtn = (Button) myView.findViewById(R.id.button1);
        loadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LoadContactsAyscn lca = new LoadContactsAyscn();
                lca.execute();
            }
        });

        // floating button - add contact
        mFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialog = inflater.inflate(R.layout.contact_dialog_info, null);
                alertDialogBuilder.setView(dialog);
                dialog_name = dialog.findViewById (R.id.dialog_name);
                dialog_number = dialog.findViewById (R.id.dialog_number);

                alertDialogBuilder.setMessage("Add contact");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if(dialog_name.getText ().length ()==0&&dialog_number.getText ().length ()==0){
                                    Toast.makeText (mContext,"이름과 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show ();
                                }else{
                                    String name = dialog_name.getText ().toString ();
                                    String number = dialog_number.getText ().toString ();
                                    dialog_name.setText ("");
                                    dialog_number.setText ("");
                                    ContactData data = new ContactData ();
                                    data.setName(name);
                                    data.setNumber(number);
                                    mArrayList.add (data);
                                    mAdapter.notifyItemInserted (mArrayList.size ()-1);
                                }
                            }
                        });

                alertDialogBuilder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return myView;
    }

    public void startApp()
    {

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if (
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
        {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS))
            {
                // ...
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS },
                    1);
        }
        else
        {
            startApp();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int i=0; i<grantResults.length; ++i)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        // 하나라도 거부한다면.
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

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<ContactData>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(getActivity(), "Loading Contacts",
                    "Please Wait");
        }

        @Override
        protected ArrayList<ContactData> doInBackground(Void... params) {
            // TODO Auto-generated method stub

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

        @Override
        protected void onPostExecute(ArrayList<ContactData> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(mArrayList);

            pd.cancel();

            mSwipeRefreshLayout.removeView(loadBtn);
            mAdapter.notifyItemInserted (mArrayList.size ()-1);
        }

    }
}