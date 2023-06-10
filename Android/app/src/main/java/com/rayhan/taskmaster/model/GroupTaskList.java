package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.sql.Date;

public class GroupTaskList {
    private int tugasgrupid;
    private int groupid;
    private String nama_tugas;
    private String deskripsi_tugas;
    private String status;
    private String tanggal_pengerjaan;

    public int getTaskId() {return tugasgrupid; }
    public int getGroupID() {return groupid; }
    public String getTaskName() {return nama_tugas; }
    public String getTaskDescription() {return deskripsi_tugas; }
    public String getTaskStatus() {return status; }
    public String getTaskDate() {return tanggal_pengerjaan; }
    @NonNull
    public String toString() {return nama_tugas + "\n " + status;}
}
