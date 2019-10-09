package com.miesaf.mobeve.api;

import com.miesaf.mobeve.beans.EventList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of EmployeeList
    */
    @GET("dev/member/event_list.php")
    Call<EventList> getMyJSON();
}