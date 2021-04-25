package com.benchmarkChat.chatapp.Fragments;

import com.benchmarkChat.chatapp.Notifications.MyResponse;
import com.benchmarkChat.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAU2S2WYs:APA91bE11EaIqCOVsl0091B4Nu-zEWVhdH6zzDdRIYt-4wk5BH-JpLh3LaU00UJa_Tmnm78spRo3HTwdBC6bWd0kmvO8ZqAtzMYvle73-ixeVHFMltXxXom5LjCfKemoWhp8dalfI-kL"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
