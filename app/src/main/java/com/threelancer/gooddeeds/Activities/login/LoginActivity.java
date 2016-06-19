package com.threelancer.gooddeeds.Activities.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.threelancer.gooddeeds.Activities.TabbedActivity;
import com.threelancer.gooddeeds.util.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.threelancer.gooddeeds.util.Constants;
import com.threelancer.gooddeeds.R;


public class LoginActivity extends AppCompatActivity {

    Firebase mFirebase;
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextEmailInput, mEditTextPasswordInput;
    String mEncodedEmail;
    TextView logoText;
    LinearLayout mainLinearLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logoText = (TextView) findViewById(R.id.logo_text);
        mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

        AnimatorSet set  = new AnimatorSet();

        set.playSequentially(ObjectAnimator.ofFloat(logoText, "Y", 200).setDuration(2000),
                ObjectAnimator.ofFloat(logoText, "alpha", 0).setDuration(1500),
                ObjectAnimator.ofFloat(mainLinearLayout, "alpha", 1).setDuration(500));

        set.start();

        mFirebase = new Firebase(Constants.FIREBASE_URL);
        initializeScreen();

        mEditTextPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    signInPassword();
                }
                return true;
            }
        });
    }

    public void initializeScreen() {
        mEditTextEmailInput = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPasswordInput = (EditText) findViewById(R.id.edit_text_password);


        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    public void onSignInPressed(View view) {
        signInPassword();
    }

    public void signInPassword() {
        String email = mEditTextEmailInput.getText().toString();
        String password = mEditTextPasswordInput.getText().toString();

        if (email.equals("")) {
            mEditTextEmailInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }

        if (password.equals("")) {
            mEditTextPasswordInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }
        mAuthProgressDialog.show();
        mFirebase.authWithPassword(email, password, new MyAuthResultHandler(Constants.PASSWORD_PROVIDER));
    }

    public void onSignUpPressed(View view) {
        startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        finish();
    }

    /**
     * Handle user authentication that was initiated with mFirebaseRef.authWithPassword
     * or mFirebaseRef.authWithOAuthToken
     */
    private class MyAuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public MyAuthResultHandler(String provider) {
            this.provider = provider;
        }

        /**
         * On successful authentication call setAuthenticatedUser if it was not already
         * called in
         */
        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.dismiss();
            Log.i("myTest", provider + " " + getString(R.string.log_message_auth_successful));

            if (authData != null) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spe = sp.edit();
                /**
                 * If user has logged in with Password provider
                 */
                if (authData.getProvider().equals(Constants.PASSWORD_PROVIDER)) {
                    setAuthenticatedUserPasswordProvider(authData);
                }

                /* Save encodedEmail for later use and start MainActivity */
                spe.putString(Constants.KEY_EMAIL, mEncodedEmail).apply();

                /* Go to main activity */
                Intent intent = new Intent(LoginActivity.this, TabbedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.dismiss();

            switch (firebaseError.getCode()) {
                case FirebaseError.INVALID_EMAIL:
                case FirebaseError.USER_DOES_NOT_EXIST:
                    mEditTextEmailInput.setError(getString(R.string.error_message_email_issue));
                    break;
                case FirebaseError.INVALID_PASSWORD:
                    mEditTextPasswordInput.setError(firebaseError.getMessage());
                    break;
                case FirebaseError.NETWORK_ERROR:
                    showErrorToast(getString(R.string.error_message_failed_sign_in_no_network));
                    break;
                default:
                    showErrorToast(firebaseError.toString());
            }
        }
    }

    private void showErrorToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void setAuthenticatedUserPasswordProvider(AuthData authData) {
        final String unprocessedEmail = authData.getProviderData().get(Constants.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();
        /**
         * Encode user email replacing "." with ","
         * to be able to use it as a Firebase db key
         */
        mEncodedEmail = Utilities.encodeEmail(unprocessedEmail);
    }
}
