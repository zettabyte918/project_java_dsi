package com.projet.utils.OrangeSMS.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsesClasses {
    public class AccessTokenResponse {
        @SerializedName("token_type")
        @Expose
        private String tokenType;

        @SerializedName("access_token")
        @Expose
        private String accessToken;

        @SerializedName("expires_in")
        @Expose
        private int expiresIn;

        public String getTokenType() {
            return tokenType;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }
    }
}
