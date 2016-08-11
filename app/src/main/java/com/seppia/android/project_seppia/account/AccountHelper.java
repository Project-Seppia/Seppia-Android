package com.seppia.android.project_seppia.account;


import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class AccountHelper {

    private static AccountHelper accountHelper;
    private static CognitoUserPool userPool;

    // currently following parameters are for UserPool CognitoDemo, will be replaced
    private static final String userPoolId = "us-east-1_dDcvwYYrs";
    private static final String clientId = "2dgjhr02sj7s6gqefk3rqv3el2";
    private static final String clientSecret = "ht494lsph6hnmubn17g3gtjirmt92q5c2p6pqr5fluisbfb6sr5";
    private static final Regions cognitoRegion = Regions.US_EAST_1;

    public static void init(Context context){

        if(accountHelper != null && userPool != null){
            return;
        }
        if(accountHelper == null){
            accountHelper = new AccountHelper();
        }

        if (userPool == null) {
            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret);
        }
    }

    public static CognitoUserPool getUserPool(){
        return userPool;
    }

    public static boolean isEmailValid(String email){
        return email.contains("@");
    }
    public static boolean isPasswordValid(String password){
        return password.length()>5;
    }
}

