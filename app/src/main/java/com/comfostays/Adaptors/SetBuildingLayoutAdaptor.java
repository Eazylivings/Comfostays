package com.comfostays.Adaptors;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.comfostays.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetBuildingLayoutAdaptor extends ArrayAdapter<String> {

    Context context;

    List<String> floorNames=new ArrayList<String>();
    public static HashMap<String, String> floorAndNumberOfRoomsMap = new HashMap<>();
    static EditText numberOfRooms;
    int count = 0;

    public SetBuildingLayoutAdaptor(Context context, List<String> floorNames) {

        super(context, R.layout.layout_set_building_layout, floorNames);

        this.context = context;
        this.floorNames = floorNames;
        floorAndNumberOfRoomsMap = new HashMap<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view=null;
        if(inflater!=null) {

            view = inflater.inflate(R.layout.layout_set_building_layout, parent, false);

            TextView floorNumber=(TextView)view.findViewById(R.id.setBuildingLayout_editText_floorNumber);
            if(floorNumber!=null){

                floorNumber.setText(floorNames.get(position));
            }

            numberOfRooms = (EditText) view.findViewById(R.id.setBuildingLayout_editText_numberOfRooms);

            numberOfRooms.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (count == position) {
                        floorAndNumberOfRoomsMap.put(String.valueOf(position), s.toString());
                        count++;
                    }
                }
            });
        }
        return view;
    }
}
