package com.example.proj1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Frag1 extends Fragment {
    private Context mContext;
    private ArrayList<ContactData> mArrayList;
    private ContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText edit_name, edit_number;
    private Button btn_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.contact_layout, container, false);
        mContext = getActivity().getApplicationContext ();
        edit_name = myView.findViewById (R.id.edit_name);
        edit_number = myView.findViewById (R.id.edit_number);
        btn_save = myView.findViewById (R.id.btn_save);
        mRecyclerView = (RecyclerView) myView.findViewById (R.id.recycler);

        //레이아웃메니저는 리사이클러뷰의 항목 배치를 어떻게 할지 정하고, 스크롤 동작도 정의한다.
        //수평/수직 리스트 LinearLayoutManager
        //그리드 리스트 GridLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager (mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager (layoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new ContactAdapter (mContext, mArrayList);
        mRecyclerView.setAdapter (mAdapter);

        //버튼 클릭이벤트
        //이름과 전화번호를 입력한 후 버튼을 클릭하면 어레이리스트에 데이터를 담고 리사이클러뷰에 띄운다.
        btn_save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(edit_name.getText ().length ()==0&&edit_number.getText ().length ()==0){
                    Toast.makeText (mContext,"이름과 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show ();
                }else{
                    String name = edit_name.getText ().toString ();
                    String number = edit_number.getText ().toString ();
                    edit_name.setText ("");
                    edit_number.setText ("");
                    ContactData data = new ContactData ();
                    data.setName(name);
                    data.setNumber(number);
                    mArrayList.add (data);
                    mAdapter.notifyItemInserted (mArrayList.size ()-1);
                }
            }
        });
        return myView;
    }

}