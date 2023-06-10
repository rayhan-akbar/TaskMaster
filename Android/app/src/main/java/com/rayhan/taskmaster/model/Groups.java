package com.rayhan.taskmaster.model;

public class Groups {
    private int groupid;
    private String nama_group;
    private String deskripsi;


    public Groups(int groupid, String nama_group, String deskripsi){
        this.groupid = groupid;
        this. nama_group = nama_group;
        this.deskripsi = deskripsi;
    }
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return groupid;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
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

    public String toString() {
        return nama_group + "\n Deskripsi: " + deskripsi;
    }


}
