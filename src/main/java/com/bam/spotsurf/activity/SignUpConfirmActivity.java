package com.bam.spotsurf.activity;

/**
 * Created by bmerm on 12/10/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.bam.spotsurf.R;
import com.bam.spotsurf.utils.ViewHelper;


/**
 * Activity to prompt for sign-up confirmation information.
 */
public class SignUpConfirmActivity extends Activity {
    /** Log tag. */
    private static final String LOG_TAG = SignUpConfirmActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_confirm);
    }

    /**
     * Retrieve input and return to caller.
     * @param view the Android View
     */
    public void confirmAccount(final View view) {
        final String username =
                ViewHelper.getStringValue(this, R.id.confirm_account_username);
        final String verificationCode =
                ViewHelper.getStringValue(this, R.id.confirm_account_confirmation_code);

        Log.d(LOG_TAG, "username = " + username);
        Log.d(LOG_TAG, "verificationCode = " + verificationCode);

        final Intent intent = new Intent();
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME, username);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.VERIFICATION_CODE, verificationCode);

        setResult(RESULT_OK, intent);

        finish();
    }
}