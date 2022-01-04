package com.example.proj1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<ContactData> mArrayList; //데이터를 담을 어레이리스트
    private boolean activate;
    private boolean isNightModeOn;

    public ContactAdapter(Context context, ArrayList<ContactData> arrayList) {
        this.mArrayList = arrayList;
        this.mContext =context;
        this.activate = false;
        mArrayList = getAllContacts();
    }

    //리스트의 각 항목을 이루는 디자인(xml)을 적용.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate (R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    //리스트의 각 항목에 들어갈 데이터를 지정.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactData data = mArrayList.get (position);
//        Typeface face=Typeface.createFromAsset(mContext.getAssets(),"fonts/raleway_semibold");
//
        holder.tv_name.setText (data.getName ());
        holder.tv_number.setText (data.getNumber ());
        isNightModeOn = mContext.getSharedPreferences("AppSettingPrefs", 0).getBoolean("NightMode", false);
//        holder.tv_name.setTypeface(face);
//        holder.tv_number.setTypeface(face);

//        if (activate) {
//            holder.btn_remove.setVisibility(View.VISIBLE);
//        } else {
//            holder.btn_remove.setVisibility(View.INVISIBLE);
//        }
    }

    //화면에 보여줄 데이터의 갯수를 반환.
    @Override
    public int getItemCount() {
        String TAG = "ContactAdapter";
        Log.d (TAG, "getItemCount: "+mArrayList.size ());
        return mArrayList.size ();
    }

    //뷰홀더 객체에 저장되어 화면에 표시되고, 필요에 따라 생성 또는 재활용 된다.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_number;
        ImageButton btn_remove, btn_call, btn_message;
        int position;
        String phoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            this.tv_name = itemView.findViewById (R.id.tv_name);
            this.tv_number = itemView.findViewById (R.id.tv_number);
            this.btn_remove = itemView.findViewById(R.id.removeButton);
            this.btn_call = itemView.findViewById(R.id.callButton);
            this.btn_message = itemView.findViewById(R.id.messageButton);
            position = getAdapterPosition();
//            if (isNightModeOn){
//                btn_remove.setImageResource(R.drawable.person_dark);
//            }

            btn_remove.setOnClickListener(view -> {
                position = getAdapterPosition();
                String phone = mArrayList.get(position).getNumber();
                String name = mArrayList.get(position).getName();
                Log.d("remove start", phone.concat(name));
                deleteContact(btn_remove.getContext(), phone, name);
                mArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                /* Toast.makeText(mContext,Integer.toString(position),Toast.LENGTH_SHORT).show(); */

            });

            btn_call.setOnClickListener(view -> {
                position = getAdapterPosition();
                phoneNumber = mArrayList.get(position).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:".concat(phoneNumber)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            });

            btn_message.setOnClickListener(view -> {
                position = getAdapterPosition();
                phoneNumber = mArrayList.get(position).getNumber();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:".concat(phoneNumber)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            });

        }
    }

    public static boolean deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    String display_name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    Log.d("in cur", display_name);
                    if (display_name.equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                        ctx.getContentResolver().notifyChange(uri, null);
                        return true;
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            cur.close();
        }
        return false;
    }

    @SuppressLint("Recycle")
    private ArrayList<ContactData> getAllContacts() {
        Uri uri;
        Cursor cursor;
        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        /*
         int column_index_data;
         ArrayList<String> listContacts = new ArrayList<>();
         String absPath;
         String[] proj = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
         cursor = mContext.getContentResolver().query(uri, proj, null, null, null);
         ContentResolver contentResolver = getContentResolver ();
        */

        cursor =  mContext.getContentResolver().query (uri, null, null, null, null);

        // column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {

            String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactData contactData = new ContactData();
            contactData.setName(contactName);
            contactData.setNumber(phNumber);
            mArrayList.add (contactData);
        }

        Collections.reverse(mArrayList);
        return mArrayList;
    }

    public void activateButtons(boolean activate) {
        this.activate = activate;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }
}

