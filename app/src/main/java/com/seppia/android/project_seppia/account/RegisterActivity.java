package com.seppia.android.project_seppia.account;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import com.seppia.android.project_seppia.MainActivity;
import com.seppia.android.project_seppia.R;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = "SignUp";

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText phone;

    private TextView email_errorMessage;

    private Button signUp;
    private Button openConfirmActivity;
    private ProgressDialog waitDialog;

    private String usernameInput;
    private String userPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AccountHelper.init(getApplicationContext());
        init();
    }

    private void init() {
        username = (EditText) findViewById(R.id.editText_RegUsername);
        password = (EditText) findViewById(R.id.editText_RegPassword);
        email = (EditText) findViewById(R.id.editText_RegEmail);
        phone = (EditText) findViewById(R.id.editText_RegPhone);

        signUp = (Button) findViewById(R.id.button_RegSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "sign up button clicked");
                CognitoUserAttributes userAttributes = new CognitoUserAttributes();

                usernameInput = username.getText().toString();
                if (usernameInput == null || usernameInput.isEmpty()) {
                    TextView view = (TextView) findViewById(R.id.textView_regUsernameErrorMessage);
                    view.setText(username.getHint() + " cannot be empty");
                    return;
                }
                if(!AccountHelper.isUsernameValid(usernameInput)){
                    TextView view = (TextView) findViewById(R.id.textView_regUsernameErrorMessage);
                    view.setText(username.getHint() + " is not valid");
                }

                userPasswordInput = password.getText().toString();
                if (userPasswordInput == null || userPasswordInput.isEmpty()) {
                    TextView view = (TextView) findViewById(R.id.textView_regPasswordErrorMessage);
                    view.setText(password.getHint() + " cannot be empty");
                    return;
                }
                if( !AccountHelper.isPasswordValid(userPasswordInput) ){
                    TextView view = (TextView) findViewById(R.id.textView_regPasswordErrorMessage);
                    view.setText(password.getHint() + " has to be at least 6 characters");
                    return;
                }

                String userInput = email.getText().toString();
                email_errorMessage = (TextView) findViewById(R.id.textView_regEmailInputErrorMessage);
                if (userInput != null && userInput.length() > 0) {
                    if (AccountHelper.isEmailValid(userInput)) {
                        userAttributes.addAttribute("email", userInput);
                    } else {
                        email_errorMessage.setText(email.getHint() + " is not valid");
                        return;
                    }
                } else {
                    email_errorMessage.setText(email.getHint() + " is required");
                    return;
                }

                userInput = phone.getText().toString();
                if(userInput != null){
                    if(userInput.length() > 0){
                        userAttributes.addAttribute("custom:PhoneNumber", userInput);
                    }
                }

                showWaitDialog("Signing up...");

                AccountHelper.getUserPool().signUpInBackground(usernameInput, userPasswordInput, userAttributes, null, signUpHandler);
            }
        });

        openConfirmActivity = (Button) findViewById(R.id.button_openConfirmActivity);
        openConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountComfirmActivity.class));
            }
        });
    }

    SignUpHandler signUpHandler = new SignUpHandler() {
        Toast toast;

        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            closeWaitDialog();
            if(signUpConfirmationState){
                toast = Toast.makeText(getApplicationContext(),"Sign up Success",Toast.LENGTH_SHORT);
                toast.show();
            } else {
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Please comfirm")
                        .setMessage("A varification code has been sent to your email address")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), AccountComfirmActivity.class));
                            }
                        })
                        .show();
            }

        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            toast = Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_LONG);
            toast.show();
            Log.e(TAG, exception.toString());
            // Sign-up failed, check exception for the cause
        }
    };

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }
}
