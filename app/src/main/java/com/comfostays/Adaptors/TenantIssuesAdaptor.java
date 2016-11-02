package com.comfostays.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.Constants;
import com.comfostays.R;

import java.util.ArrayList;
import java.util.List;

public class TenantIssuesAdaptor extends ArrayAdapter<String> {

    List<String> notifications;
    List<String> statusOfIssue=new ArrayList<>();

    static boolean isIssueAcknowledged=false;
    static boolean isIssueDismissed=false;

    static List<String> resolvedIssues=new ArrayList<>();
    static List<String> dismissedIssues=new ArrayList<>();

    public TenantIssuesAdaptor(Context context, List<String> notifications, List<String> statusOfIssue) {

        super(context, R.layout.layout_tenant_issues, notifications);
        this.notifications=notifications;
        this.statusOfIssue=statusOfIssue;

        resolvedIssues=new ArrayList<>();
        dismissedIssues=new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view;
        final String OPEN="Open";
        if(inflater!=null){

            view=inflater.inflate(R.layout.layout_tenant_issues,parent,false);

            final ImageView acceptImage=(ImageView)view.findViewById(R.id.imageView_acknowledge);
            final ImageView rejectImage=(ImageView)view.findViewById(R.id.imageView_dismiss);

            TextView issue=(TextView)view.findViewById(R.id.layoutNotification_textView_notification);
            final TextView statusView=(TextView)view.findViewById(R.id.textView_status);
            if(issue!=null && statusView!=null){
                issue.setText(notifications.get(position));

                String status=statusOfIssue.get(position);

                if(status.equalsIgnoreCase(Constants.ACKNOWLEDGED)){

                    issue.setTextColor(Color.LTGRAY);
                    rejectImage.setColorFilter(Color.LTGRAY);

                    statusView.setText(Constants.ACKNOWLEDGED);

                }else if(status.equalsIgnoreCase(Constants.DISMISSED)){

                    issue.setTextColor(Color.LTGRAY);
                    acceptImage.setColorFilter(Color.LTGRAY);

                    statusView.setText(Constants.DISMISSED);

                }else{

                    if(acceptImage!=null) {

                        acceptImage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                statusView.setText(Constants.ACKNOWLEDGED);

                                if(isIssueDismissed && rejectImage!=null){
                                    rejectImage.setColorFilter(null);

                                    acceptImage.setColorFilter(Color.DKGRAY);

                                    isIssueDismissed=false;
                                    isIssueAcknowledged=true;
                                }else if(isIssueAcknowledged){

                                    statusView.setText(OPEN);

                                    acceptImage.setColorFilter(null);
                                    isIssueAcknowledged=false;
                                }else{
                                    acceptImage.setColorFilter(Color.DKGRAY);
                                    isIssueAcknowledged=true;
                                    isIssueDismissed=false;
                                }
                            }
                        });
                    }

                    if(rejectImage!=null) {

                        rejectImage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                statusView.setText(Constants.DISMISSED);

                                if(isIssueAcknowledged && acceptImage!=null){

                                    acceptImage.setColorFilter(null);
                                    rejectImage.setColorFilter(Color.DKGRAY);

                                    isIssueDismissed=true;
                                    isIssueAcknowledged=false;
                                }else if(isIssueDismissed){

                                    statusView.setText(OPEN);

                                    rejectImage.setColorFilter(null);
                                    isIssueDismissed=false;
                                }else{
                                    rejectImage.setColorFilter(Color.DKGRAY);
                                    isIssueAcknowledged=false;
                                    isIssueDismissed=true;
                                }
                            }
                        });
                    }


                }
            }
            return view;
        }else{
            return convertView;
        }


    }
}
