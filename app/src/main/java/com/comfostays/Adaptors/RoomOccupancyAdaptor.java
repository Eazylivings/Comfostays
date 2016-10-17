package com.comfostays.Adaptors;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.comfostays.R;

public class RoomOccupancyAdaptor extends BaseExpandableListAdapter {

    Context context;
    private LayoutInflater inflater;
    private List<String> categoryList;

    public RoomOccupancyAdaptor(Context context, List<String> categoryList) {

        this.context=context;
        this.categoryList = categoryList;
        this.inflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "size";
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        convertView = inflater.inflate(R.layout.layout_room_occupancy_parent, null);

        TextView roomNumber=(TextView)convertView.findViewById(R.id.layoutRoomOccupancyParent_textView_roomNumber);

        if(roomNumber!=null){
            roomNumber.setText(categoryList.get(groupPosition));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_room_occupancy_child, null);
        }


        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

