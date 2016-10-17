package com.comfostays.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comfostays.R;

import java.util.List;

public class MyPropertiesAdaptor extends ArrayAdapter<String>{

    List<String> registeredProperties;

    public MyPropertiesAdaptor(Context context, List<String> registeredProperties) {

        super(context, R.layout.layout_my_properties, registeredProperties);
        this.registeredProperties=registeredProperties;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view;
        if(inflater!=null){

            view=inflater.inflate(R.layout.layout_my_properties,parent,false);

            TextView registeredPropertyName=(TextView)view.findViewById(R.id.layout_myProperties_textView_propertyName);
            if(registeredPropertyName!=null){
                registeredPropertyName.setText(registeredProperties.get(position));
            }
            return view;
        }else{
            return convertView;
        }


    }
}
