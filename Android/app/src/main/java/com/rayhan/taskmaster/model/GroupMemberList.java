package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupMemberList {
    private int userid;
    private String username;
    private String tanggal_masuk;

    public static final DateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");

    public int getUserId() {
        return userid;
    }


    public String getEntryDate() {return tanggal_masuk; }

    public String getUsername() {
        return username;
    }


    @NonNull
    public String toString() {
        Date date = null;
        try {
            date = dateFormatInput.parse(tanggal_masuk);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return username + "\n Entry Date: " + dateFormatOutput.format(date);
    }



}
