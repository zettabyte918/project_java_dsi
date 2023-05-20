package com.projet.utils.OrangeSMS;

import okhttp3.*;

import java.io.IOException;

public class SmsSender {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String BASE_URL = "https://api.orange.com";

    private final OkHttpClient client;
    private final String accessToken;

    public SmsSender(String accessToken) {
        this.client = new OkHttpClient();
        this.accessToken = accessToken;
    }

    public void sendSms(String recipientNumber, String code) throws IOException {
        // Build the request body
        String message = "Bonjour, " + code + " est votre code de confirmation sur reminders app";
        String json = "{\"outboundSMSMessageRequest\":{" +
                "\"address\":\"tel:" + recipientNumber + "\"," +
                "\"senderAddress\":\"tel:+21627515642\"," +
                "\"senderName\":\"houssem\"," +
                "\"outboundSMSTextMessage\":{" +
                "\"message\":\"" + message + "\"" +
                "}" +
                "}}";
        RequestBody body = RequestBody.create(json, JSON);

        // Build the request
        String url = BASE_URL + "/smsmessaging/v1/outbound/tel:+21627515642/requests";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Send the request and handle the response
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
