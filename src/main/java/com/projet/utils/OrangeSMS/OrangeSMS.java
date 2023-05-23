package com.projet.utils.OrangeSMS;

import com.google.gson.Gson;
import com.projet.AppState;
import com.projet.utils.OrangeSMS.responses.ResponsesClasses.AccessTokenResponse;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrangeSMS {
        private static final String AUTH_HEADER = "Basic ZTdrV0RKSHA5U05RZ1F0RmdIU21OMnB0Q05BMFZaSEc6ZEVoQVN3QjZTSGR3OUFNcA==";

        public static void main(String[] args) {

        }

        public static void send2FaSMS(String tel, String message) throws IOException {
                AccessTokenResponse access_token = OrangeSMS.getAuth();

                // send sms
                SmsSender smsSender = new SmsSender(access_token.getAccessToken());
                smsSender.sendSms("+216" + tel, message);
        }

        public static void sendReminderSMS(String message) throws IOException {
                AccessTokenResponse access_token = OrangeSMS.getAuth();
                String user_tel = AppState.getInstance().getUser().getTel();
                // send sms
                SmsSender smsSender = new SmsSender(access_token.getAccessToken());
                smsSender.sendSms("+216" + user_tel, message);
        }

        public static AccessTokenResponse getAuth() throws IOException {
                OkHttpClient client = new OkHttpClient();

                String url = "https://api.orange.com/oauth/v3/token";

                // Build the request body
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                String bodyContent = "grant_type=client_credentials";
                RequestBody body = RequestBody.create(bodyContent, mediaType);

                // Build the request
                Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .addHeader("Authorization",
                                                AUTH_HEADER)
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .build();

                // Send the request and parse the response
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                Gson gson = new Gson();
                return gson.fromJson(responseBody,
                                AccessTokenResponse.class);

        }
}
