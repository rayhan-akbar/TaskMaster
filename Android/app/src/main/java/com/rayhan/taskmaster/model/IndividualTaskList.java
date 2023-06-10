package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class IndividualTaskList {

    private int tugasindividuid;
    private int userid;
    private String nama_tugas;
    private String deskripsi_tugas;
    private String tanggal_pengerjaan;
    private String status;

    public int getTaskId() {return tugasindividuid; }
    public int getUserID() {return userid; }
    public String getTaskName() {return nama_tugas; }
    public String getTaskDescription() {return deskripsi_tugas; }
    public String  getTaskStatus() {return status; }
    public String getTaskDate() {return tanggal_pengerjaan; }
    @NonNull
    public String toString() {
        return nama_tugas + "\n " + status;
    }
}
