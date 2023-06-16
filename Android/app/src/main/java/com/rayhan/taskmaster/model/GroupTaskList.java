package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GroupTaskList {
    private int tugasgrupid;
    private int groupid;
    private String nama_tugas;
    private String deskripsi_tugas;
    private String status;
    private String tanggal_pengerjaan;

    public static final DateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");

    public int getTaskId() {return tugasgrupid; }
    public int getGroupID() {return groupid; }
    public String getTaskName() {return nama_tugas; }
    public String getTaskDescription() {return deskripsi_tugas; }
    public String getTaskStatus() {return status; }
    public String getTaskDate() {return tanggal_pengerjaan; }
    @NonNull
    public String toString() {return nama_tugas + "\n " + status;}
}
