package com.seppia.android.project_seppia.account;


import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class AccountHelper {
    public static final String TAG = "AccountHelper";

    private static AccountHelper accountHelper;
    private static CognitoUserPool userPool;
    private static CognitoUser cognitoUser;

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
    public static CognitoUser getCognitoUser(){
        try {
            return userPool.getCurrentUser();
        }catch (Exception exception){
            Log.i(TAG,"userPool not exists");
            return null;
        }
    }

    public static boolean isEmailValid(String email){
        // TODO: more details..
        return email.contains("@");
    }
    public static boolean isPasswordValid(String password){
        // TODO: more details..
        return password.length()>5;
    }
    public static boolean isUsernameValid(String username){
        // TODO: more details..
        return username.length()>=4;
    }
}

