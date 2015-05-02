package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by User on 3/13/2015.
 */
public class CeilometerListHeader extends LinearLayout {
    public TextView resource;
    public TextView project;
    public TextView user;
    public TextView source;
    public TextView time;

    public CeilometerListHeader(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(Color.parseColor("#424944"));

        resource = new TextView(context);
        resource.setTextColor(Color.WHITE);
        project = new TextView(context);
        project.setTextColor(Color.WHITE);
        user = new TextView(context);
        user.setTextColor(Color.WHITE);
        source = new TextView(context);
        source.setTextColor(Color.WHITE);
        time = new TextView(context);
        time.setTextColor(Color.WHITE);

        addView(resource);
        addView(project);
        addView(user);
        addView(source);
        addView(time);
    }

    public void setData(String resourceContent, String projectContent, String userContent, String sourceContent, String timeContent) {
        resource.setText("Resource: " + resourceContent);
        project.setText("Project:    " + projectContent);
        user.setText("User:          " + userContent);
        source.setText("Source:     " + sourceContent);
        time.setText("Time:        " + timeContent);
    }
}
