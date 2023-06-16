package com.rayhan.taskmaster.request;

import com.rayhan.taskmaster.model.users;

import java.math.BigInteger;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * The interface Base api service.
 */
public interface BaseApiService {
    /**
     * Login request call.
     *
     * @param username the username
     * @param password the password
     * @return the call
     */
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    /**
     * Register request call.
     *
     * @param username the username
     * @param password the password
     * @return the call
     */
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(@Field("username") String username,
                                @Field("password") String password);

    /**
     * AddGroup Request Call
     *
     * @param Nama_Group the name
     * @param Deskripsi the description
     * @return the call
     */
    @FormUrlEncoded
    @POST("addgroup")
    Call<ResponseBody> addGroup(@Field("Nama_Group") String Nama_Group,
                                @Field("Deskripsi") String Deskripsi);

    /**
     * EnrollGroup Request call
     *
     * @param GroupID the Group ID
     * @param UserID the User ID
     * @return  the call
     */
    @FormUrlEncoded
    @POST("enrollgroup")
    Call<ResponseBody> enrollGroup(@Field("GroupID") Integer GroupID,
                                   @Field("UserID") Integer UserID);

    /**
     * Add Individual Task Request Call
     *
     * @param UserID The User ID
     * @param Nama_Tugas The name
     * @param Deskripsi_tugas The description
     * @param Tanggal_Pengerjaan The date
     * @return the call
     */
    @FormUrlEncoded
    @POST("addindividualtask")
    Call<ResponseBody> addIndividualTask(@Field("UserID") Integer UserID,
                                         @Field("Nama_Tugas") String Nama_Tugas,
                                         @Field("Deskripsi_tugas") String Deskripsi_tugas,
                                         @Field("Tanggal_pengerjaan") String Tanggal_Pengerjaan);

    /**
     * Add Group Task Request Call
     *
     * @param GroupID The Group ID
     * @param Nama_Tugas The name
     * @param Deskripsi_tugas The description
     * @param Tanggal_Pengerjaan The date
     * @return the call
     */
    @FormUrlEncoded
    @POST("addgrouptask")
    Call<ResponseBody> addGroupTask(@Field("GroupID") Integer GroupID,
                                    @Field("Nama_Tugas") String Nama_Tugas,
                                    @Field("Deskripsi_tugas") String Deskripsi_tugas,
                                    @Field("Tanggal_pengerjaan") String Tanggal_Pengerjaan);


    /**
     * Show Group Request Call
     * @return the call
     */
    @GET("showgroup")
    Call<ResponseBody> showGroup();

    /**
     * Show User Request Call
     *
     * @param UserID The User ID
     * @return the call
     */
    @FormUrlEncoded
    @GET("showuser")
    Call<ResponseBody> showUser(@Field("UserID") Integer UserID);

    /**
     * Search Group by name request call
     *
     * @param Nama_Group the name
     * @return the call
     */
    @FormUrlEncoded
    @GET("searchgroupbyname")
    Call<ResponseBody> searchGroupByName(@Field("Nama_Group") String Nama_Group);

    /**
     * Search Group by ID request call
     *
     * @param GroupID the name
     * @return the call
     */
    @FormUrlEncoded
    @GET("searchgroupbyid")
    Call<ResponseBody> searchGroupByID(@Field("GroupID") Integer GroupID);

    /**
     * Show Enrollment request call
     *
     * @param UserID the User ID
     * @return the call
     */
    @FormUrlEncoded
    @POST("showenrollment")
    Call<ResponseBody> showEnrollment(@Field("UserID") Integer UserID);

    /**
     * Show Individual Task request call
     *
     * @param UserID the user ID
     * @return the call
     */
    @FormUrlEncoded
    @POST("showindividualtask")
    Call<ResponseBody> showIndividualTask(@Field("UserID") Integer UserID);

    /**
     * Filter Individual Task request call
     *
     * @param UserID the user ID
     * @param Status the task status
     * @return the call
     */
    @FormUrlEncoded
    @POST("filterindividualtask")
    Call<ResponseBody> filterIndividualTask(@Field("UserID") Integer UserID,
                                            @Field("Status") String Status);

    /**
     * Search Individual Task request call
     *
     * @param UserID the user ID
     * @param Nama_Tugas the task status
     * @return the call
     */
    @FormUrlEncoded
    @POST("searchindividualtask")
    Call<ResponseBody> searchIndividualTask(@Field("UserID") Integer UserID,
                                            @Field("Nama_Tugas") String Nama_Tugas);

    /**
     * Show Group Task request call
     *
     * @param GroupID the user ID
     * @return the call
     */
    @FormUrlEncoded
    @POST("showgrouptask")
    Call<ResponseBody> showGroupTask(@Field("GroupID") Integer GroupID);

    /**
     * Filter Group Task request call
     *
     * @param GroupID the user ID
     * @param Status the task status
     * @return the call
     */
    @FormUrlEncoded
    @POST("filtergrouptask")
    Call<ResponseBody> filterGroupTask(@Field("GroupID") Integer GroupID,
                                            @Field("Status") String Status);

    /**
     * Search Group Task request call
     *
     * @param GroupID the user ID
     * @param Nama_Tugas the task status
     * @return the call
     */
    @FormUrlEncoded
    @POST("searchgrouptask")
    Call<ResponseBody> searchGroupTask(@Field("UserID") Integer GroupID,
                                       @Field("Nama_Tugas") String Nama_Tugas);

    /**
     * Delete User call
     * @param UserID the user id
     * @return the call
     */
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteuser", hasBody = true)
    Call<ResponseBody> deleteUser(@Field("UserID") Integer UserID);

    /**
     * Delete enrollment call
     * @param UserID the user id
     * @param GroupID the group id
     * @return the call
     */
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "unenrollgroup", hasBody = true)
    Call<ResponseBody> unenrollGroup(@Field("UserID") Integer UserID,
                                     @Field("GroupID") Integer GroupID);

    /**
     * Delete individual task call
     * @param UserID the user id
     * @param TugasIndividuID the task id
     * @return the call
     */
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteindividualtask", hasBody = true)
    Call<ResponseBody> deleteIndividualTask(@Field("UserID") Integer UserID,
                                            @Field("TugasIndividuID") Integer TugasIndividuID);

    /**
     * Delete individual task call
     * @param GroupID the group id
     * @param TugasGrupID the task id
     * @return the call
     */
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deletegrouptask", hasBody = true)
    Call<ResponseBody> deleteGroupTask(@Field("GroupID") Integer GroupID,
                                       @Field("TugasGrupID") Integer TugasGrupID);

    /**
     * Update Individual Task Call
     *
     * @param UserID the user id
     * @param TugasIndividuID The task ID
     * @param Nama_Tugas The name
     * @param Deskripsi_tugas The description
     * @param Tanggal_Pengerjaan The date
     * @param status the task status
     * @return the call
     */
    @FormUrlEncoded
    @PUT("updateindividualtask")
    Call<ResponseBody> updateIndividualTask(@Field("UserID") Integer UserID,
                                            @Field("TugasIndividuID") Integer TugasIndividuID,
                                            @Field("Nama_Tugas") String Nama_Tugas,
                                            @Field("Deskripsi_tugas") String Deskripsi_tugas,
                                            @Field("Tanggal_pengerjaan") String Tanggal_Pengerjaan,
                                            @Field("status") String status);

    /**
     * Update Group Task Call
     *
     * @param TugasGrupID The task ID
     * @param Nama_Tugas The name
     * @param Deskripsi_tugas The description
     * @param Tanggal_Pengerjaan The date
     * @param status the task status
     * @return the call
     */
    @FormUrlEncoded
    @PUT("updategrouptask")
    Call<ResponseBody> updateGroupTask(@Field("GroupID") Integer GroupID,
                                        @Field("TugasGrupID") Integer TugasGrupID,
                                        @Field("Nama_Tugas") String Nama_Tugas,
                                        @Field("Deskripsi_tugas") String Deskripsi_tugas,
                                        @Field("Tanggal_pengerjaan") String Tanggal_Pengerjaan,
                                        @Field("status") String status);

}
