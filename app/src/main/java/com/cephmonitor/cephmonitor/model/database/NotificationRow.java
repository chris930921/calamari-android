package com.cephmonitor.cephmonitor.model.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 6/8/2015.
 */
public class NotificationRow {
    private SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public int id;
    public String title;
    public String content;
    public Date createdDate;
    public boolean isSeen;
    public String status;

    public void setCreatedDate(String dateTimeText) {
        try {
            createdDate = inputFormat.parse(dateTimeText);
        } catch (ParseException e) {
            e.printStackTrace();
            createdDate.setTime(0);
        }
    }

    public String getCreatedDate() {
        return outputFormat.format(createdDate);
    }

}
