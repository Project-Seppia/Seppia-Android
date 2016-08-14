package com.seppia.android.project_seppia.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;
import com.seppia.android.project_seppia.MainActivity;
import com.seppia.android.project_seppia.R;

public class AccountComfirmActivity extends AppCompatActivity {

    Button button_confirm;
    EditText confirmCode;
    EditText verifyUser;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_comfirm);

        confirmCode = (EditText) findViewById(R.id.editText_comfirmCode);

        verifyUser = (EditText) findViewById(R.id.editText_verifyUser);
        String currentUserID = AccountHelper.getUserPool().getCurrentUser().getUserId();
        verifyUser.setText(currentUserID);

        button_confirm = (Button) findViewById(R.id.button_confirmAccount);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = confirmCode.getText().toString();
                AccountHelper.getUserPool().getUser(verifyUser.getText().toString()).confirmSignUpInBackground(code,true,confirmCodeHandler);
            }
        });
    }

    GenericHandler confirmCodeHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            showSuccessAlertDialog();
        }

        @Override
        public void onFailure(Exception exception) {
            showFailureAlertDialog();
        }
    };

    VerificationHandler requestNewCodeHandler = new VerificationHandler() {
        @Override
        public void onSuccess(CognitoUserCodeDeliveryDetails verificationCodeDeliveryMedium) {
            showSentCodeAlertDialog();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_LONG);
        }
    };

    private void showSuccessAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("account verification success")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showFailureAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("verification is not successful, request new code?")
                .setPositiveButton("later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountHelper.getUserPool().getUser().resendConfirmationCodeInBackground(requestNewCodeHandler);
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showSentCodeAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("code has been sent")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
