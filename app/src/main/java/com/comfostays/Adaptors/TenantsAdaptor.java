package com.comfostays.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;

import java.util.ArrayList;
import java.util.List;

public class TenantsAdaptor extends ArrayAdapter<String> {

    Context context;

    List<String> currentResidingTenantsName =new ArrayList<String>();
    ArrayList<TenantDetailsVO> listOfTenantDetailsVO=new ArrayList<>();

    public TenantsAdaptor(Context context, List<String> currentResidingTenantsName, ArrayList<TenantDetailsVO> listOfTenantDetailsVO) {

        super(context, R.layout.layout_tenants_activity, currentResidingTenantsName);

        this.context = context;
        this.currentResidingTenantsName = currentResidingTenantsName;
        this.listOfTenantDetailsVO=listOfTenantDetailsVO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view=null;
        if(inflater!=null) {
            view = inflater.inflate(R.layout.layout_tenants_activity, parent, false);
        }

        if(view!=null) {

            TenantDetailsVO tenantDetailsVO=listOfTenantDetailsVO.get(position);

            TextView tenantName = (TextView) view.findViewById(R.id.layoutTenantsActivity_textView_name);
            TextView roomNumber=(TextView)view.findViewById(R.id.layoutTenantsActivity_textView_roomNumber);
            ImageView rentPaidImageView=(ImageView)view.findViewById(R.id.layoutTenantsActivity_imageView_rentPaid);
            ImageView tenantProfilePic=(ImageView)view.findViewById(R.id.layoutTenantsActivity_imageView_profilePic);

            if (tenantName != null && roomNumber != null && rentPaidImageView != null && tenantProfilePic != null) {

                tenantName.setText(tenantDetailsVO.getTenantName());
                roomNumber.setText(tenantDetailsVO.getTenantRoomNumber());

                if(tenantDetailsVO.isRentPaid()){
                    rentPaidImageView.setImageResource(R.drawable.correct_icon);

                }else{
                    rentPaidImageView.setImageResource(R.drawable.incorrect_icon);

                }
                tenantProfilePic.setImageResource(Integer.parseInt(tenantDetailsVO.getTenantProfilePic()));
            }
        }
        return view;
    }
}
