package com.example.proj1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Frag1 extends Fragment {
//    Typeface face=Typeface.createFromAsset(getContext().getAssets(),"fonts/raleway_semibold");
    private Context mContext; //FragmentContext
    private ArrayList<ContactData> mArrayList; //ContactArrayList
    private ContactAdapter mAdapter; //ContactAdapter
    private EditText dialog_name, dialog_number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_frag1, container, false);

        ConstraintLayout mLayout = myView.findViewById(R.id.layout);
        //ContactRecyclerView
        RecyclerView mRecyclerView = myView.findViewById(R.id.ContactRecyclerView);
        FloatingActionButton mFAB = myView.findViewById(R.id.floatingActionButton2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mArrayList = new ArrayList<>();
        mContext = requireActivity().getApplicationContext();
        mAdapter = new ContactAdapter(mContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        /*
        data instance
        ContactData data = new ContactData ();
        data.setName("Heewoo");
        data.setNumber("01058043832");
        mArrayList.add (data);
        mAdapter.notifyItemInserted (mArrayList.size ()-1);
        */

        // ask permission
        checkVerify();

        /*
        load contact by asynctask
        LoadContactsAsync lca = new LoadContactsAsync();
        lca.execute();
        */

        mFAB.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = requireActivity().getLayoutInflater();
            View dialog = inflater1.inflate(R.layout.contact_dialog_info, null);
            alertDialogBuilder.setView(dialog);
            dialog_name = dialog.findViewById (R.id.dialog_name);
            dialog_number = dialog.findViewById (R.id.dialog_number);

            alertDialogBuilder.setMessage("Add contact");
            alertDialogBuilder.setPositiveButton("Ok",
                    (arg0, arg1) -> {
                        if(dialog_name.getText ().length () == 0 && dialog_number.getText ().length () == 0){
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
                    });

            alertDialogBuilder.setNegativeButton("cancel",
                    (arg0, arg1) -> { });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        return myView;
    }

    public void startApp() { }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify() {
        // if permission not granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS }, 1);
        } else { startApp(); }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        // if permission denied
                        new AlertDialog.Builder(getActivity()).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", (dialog, which) -> {
                                    dialog.dismiss();
                                    requireActivity().finish();
                                }).setNegativeButton("권한 설정", (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                            .setData(Uri.parse("package:" + requireActivity().getApplicationContext().getPackageName()));
                                    requireActivity().getApplicationContext().startActivity(intent);
                                }).setCancelable(false).show();

                        return;
                    }
                }
                startApp();
            }
        }
    }



}