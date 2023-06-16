package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IndividualTaskList {

    private int tugasindividuid;
    private int userid;
    private String nama_tugas;
    private String deskripsi_tugas;
    private String tanggal_pengerjaan;
    private String status;

    public static final DateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");

    public int getTaskId() {return tugasindividuid; }
    public int getUserID() {return userid; }
    public String getTaskName() {return nama_tugas; }
    public String getTaskDescription() {return deskripsi_tugas; }
    public String  getTaskStatus() {return status; }
    public String getTaskDate() {return tanggal_pengerjaan; }
    @NonNull
    public String toString() {
        Date date = null;
        try {
            date = dateFormatInput.parse(tanggal_pengerjaan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nama_tugas + "\nDue Date: " + dateFormatOutput.format(date) + "\nStatus: " + status;
    }
}
