package com.comfostays.activities.customer_activities.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.comfostays.Adaptors.EventsAdaptor;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.EventsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.CustomerLocalDatabaseHandler;

import java.util.ArrayList;

public class UpcomingOrPastEventsCustomerActivity extends AppCompatActivity {

    ArrayList<String> listOfEvents=new ArrayList<>();
    ArrayList<EventsVO> listOfEventsVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_customer_upcoming_or_past_events);

            CommonFunctionality.setScreenForActivity(this, R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_EVENTS_LIST);

            //CustomerLocalDatabaseHandler databaseHandler=new CustomerLocalDatabaseHandler(getApplicationContext(),this);

            //listOfEventsVO=databaseHandler.getListOfEventsVO(0);

            populateList();

            populateListOfEventNames();

            ListAdapter listAdapter = new EventsAdaptor(this, listOfEvents,listOfEventsVO);

            ListView listView = (ListView) findViewById(R.id.listView_eventsList);
            if (listView != null) {
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(

                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent=new Intent(getApplicationContext(),CheckEventCustomerActivity.class);
                                intent.putExtra(Constants.INTENT_SELECTED_EVENT,listOfEventsVO.get(position));
                                startActivity(intent);
                                finish();
                            }
                        }
                );
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }


    private void populateListOfEventNames(){

        for(int i=0;i<listOfEventsVO.size();i++){

            listOfEvents.add(listOfEventsVO.get(i).getEventName());
        }
    }

    private void populateList(){

        listOfEventsVO=new ArrayList<>();

        EventsVO eventsVO=new EventsVO();
        eventsVO.setEventDate("21st Nov 2016");
        eventsVO.setEventName("Bike Trip");
        eventsVO.setEventOrganiser("Shwetang");
        eventsVO.setEventLocation("Our Pg to Chilkur Balaji");
        eventsVO.setEventType("Bike Trip");
        eventsVO.setEventPerHeadExpenditure("2000 /-");
        eventsVO.setOrganizerId(1);
        eventsVO.setEventStatus("Active");
        eventsVO.setEventDescription("Planning to organie a road trip on bikes to Chilkur Balaji. Anyone interested can join.");

        listOfEventsVO.add(eventsVO);
    }

}
