package com.comfostays.activities.customer_activities.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.EventsVO;

public class CheckEventCustomerActivity extends AppCompatActivity {

    EventsVO eventVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_check_event);

            CommonFunctionality.setScreenForActivity(this, R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_EVENT_DESCRIPTION);

            Intent intent=getIntent();

            eventVO=(EventsVO) intent.getSerializableExtra(Constants.INTENT_SELECTED_EVENT);

            populateFields();

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,UpcomingOrPastEventsCustomerActivity.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,UpcomingOrPastEventsCustomerActivity.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    private void populateFields(){

        ImageView eventLogo=(ImageView)findViewById(R.id.imageView_eventLogo);
        TextView eventName=(TextView)findViewById(R.id.textView_eventOrganizer);
        TextView eventDate=(TextView)findViewById(R.id.textView_eventDate);
        TextView eventLocation=(TextView)findViewById(R.id.textView_eventLocationHeading);
        TextView eventExpenditure=(TextView)findViewById(R.id.textView_eventExpenditure);
        TextView eventDescription=(TextView)findViewById(R.id.textView_eventDescription);

        if(eventLogo!=null && eventName!=null && eventDate!=null && eventLocation!=null && eventExpenditure!=null && eventDescription!=null){

            String eventType=eventVO.getEventType();

            if(eventType.equalsIgnoreCase("Dinner")){

                eventLogo.setImageResource(R.drawable.dinner_event_icon);

            }else if(eventType.equalsIgnoreCase("Road Trip")){

                eventLogo.setImageResource(R.drawable.trip_event_icon);

            }else if(eventType.equalsIgnoreCase("Vacation")){

                eventLogo.setImageResource(R.drawable.trip_event_icon);

            }else if(eventType.equalsIgnoreCase("Movie")){

                eventLogo.setImageResource(R.drawable.movie_event_icon);

            }else if(eventType.equalsIgnoreCase("Birthday Celebration")){

                eventLogo.setImageResource(R.drawable.birthday_event_icon);

            }else{

                eventLogo.setImageResource(R.drawable.dinner_event_icon);
            }

            eventName.setText(eventVO.getEventName());
            eventDate.setText(eventVO.getEventDate());
            eventLocation.setText(eventVO.getEventLocation());
            eventExpenditure.setText(eventVO.getEventPerHeadExpenditure());
            eventDescription.setText(eventVO.getEventDescription());
        }
    }
}
