package com.comfostays.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRejectTenantAdaptor extends ArrayAdapter<String> {

    List<String> tenantIds;
    List<HashMap<String,String>> listOfMapOfTenantsForRegistrationApproval;
    static boolean isAcceptSelected=false;
    static boolean isRejectSelected=false;

    List<Integer> acceptedTenantIds=new ArrayList<>();
    List<Integer> rejectedTenantIds=new ArrayList<>();

    public AcceptRejectTenantAdaptor(Context context, List<String> tenantIds,List<HashMap<String,String>> listOfMapOfTenantsForRegistrationApproval) {

        super(context, R.layout.layout_accept_reject_tenant, tenantIds);
        this.tenantIds=tenantIds;
        this.listOfMapOfTenantsForRegistrationApproval=listOfMapOfTenantsForRegistrationApproval;

        acceptedTenantIds=new ArrayList<>();
        rejectedTenantIds=new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view;
        if(inflater!=null){

            view=inflater.inflate(R.layout.layout_accept_reject_tenant,parent,false);

            HashMap<String,String> map=listOfMapOfTenantsForRegistrationApproval.get(position);

            TextView name=(TextView)view.findViewById(R.id.textView_name);
            TextView roomNumber=(TextView)view.findViewById(R.id.textView_roomNumber);

            final ImageView acceptImage=(ImageView)view.findViewById(R.id.imageView_accept);
            final ImageView rejectImage=(ImageView)view.findViewById(R.id.imageView_reject);

            if(acceptImage!=null) {

                acceptImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(isRejectSelected && rejectImage!=null){
                            rejectImage.setColorFilter(null);

                            acceptImage.setColorFilter(Color.argb(150,200,200,200));
                            isRejectSelected=false;
                            isAcceptSelected=true;
                        }else if(isAcceptSelected){

                            acceptImage.setColorFilter(null);
                            isAcceptSelected=false;
                        }else{
                            acceptImage.setColorFilter(Color.argb(150,200,200,200));
                            isAcceptSelected=true;
                            isRejectSelected=false;
                        }
                    }
                });
            }

            if(rejectImage!=null) {

                rejectImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(isAcceptSelected && acceptImage!=null){
                            acceptImage.setColorFilter(null);

                            rejectImage.setColorFilter(Color.argb(150,200,200,200));
                            isRejectSelected=true;
                            isAcceptSelected=false;
                        }else if(isRejectSelected){

                            rejectImage.setColorFilter(null);
                            isRejectSelected=false;
                        }else{
                            rejectImage.setColorFilter(Color.argb(150,200,200,200));
                            isAcceptSelected=false;
                            isRejectSelected=true;
                        }
                    }
                });
            }

            if(name!=null && roomNumber!=null){

                name.setText(map.get("name"));
                roomNumber.setText(map.get("roomNumber"));
            }
            return view;
        }else{
            return convertView;
        }


    }
}
