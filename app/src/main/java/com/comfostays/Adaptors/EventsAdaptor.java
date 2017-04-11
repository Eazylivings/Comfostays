package com.comfostays.Adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comfostays.R;
import com.comfostays.VOClass.EventsVO;

import java.util.ArrayList;


public class EventsAdaptor extends ArrayAdapter<String> {

    ArrayList<String> registeredProperties;
    ArrayList<EventsVO> listOfEventVO;

    public EventsAdaptor(Context context, ArrayList<String> registeredProperties, ArrayList<EventsVO> listOfEventVO) {

        super(context, R.layout.layout_events, registeredProperties);
        this.registeredProperties=registeredProperties;
        this.listOfEventVO=listOfEventVO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view;
        if(inflater!=null){

            view=inflater.inflate(R.layout.layout_events,parent,false);

            TextView eventName=(TextView)view.findViewById(R.id.textView_eventNameHeading);
            TextView organizer=(TextView)view.findViewById(R.id.textView_organizerName);
            TextView eventDate=(TextView)view.findViewById(R.id.textView_eventDate);
            TextView eventStatus=(TextView)view.findViewById(R.id.textView_eventStatus);

            if(eventName!=null && organizer!=null && eventDate!=null){

                eventName.setAllCaps(true);
                eventName.setText(listOfEventVO.get(position).getEventName());
                organizer.setText(listOfEventVO.get(position).getEventOrganiser());
                eventDate.setText(listOfEventVO.get(position).getEventDate());
                eventStatus.setText(listOfEventVO.get(position).getEventStatus());
            }

            return view;
        }else{
            return convertView;
        }
    }
}
