package com.rayhan.taskmaster.model;

import androidx.annotation.NonNull;

import java.sql.Date;

public class GroupEnrollmentList {
    private int userid;
    private int groupid;
    private String tanggal_masuk;
    private String nama_group;
    private String deskripsi;

    public int getUserId() {
        return userid;
    }

    public int getGroupID() {
        return groupid;
    }

    public String getEntryDate() {return tanggal_masuk; }

    public String getGroupName() {
        return nama_group;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return deskripsi;
    }


    @NonNull
    public String toString() {
        return nama_group + "\n Deskripsi: " + deskripsi;
    }



}
