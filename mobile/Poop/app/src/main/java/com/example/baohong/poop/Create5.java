package com.example.baohong.poop;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create5 extends CreateActivity {
    private String gUsername,gPassword;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create5);
        bundle = getIntent().getExtras();
        username = (EditText) findViewById(R.id.username);
        password = findViewById(R.id.password1);
        confirm = findViewById(R.id.confirm1);
        username.addTextChangedListener(emptyCheck);
        password.addTextChangedListener(emptyCheck);
        confirm.addTextChangedListener(emptyCheck);
        username.requestFocus();

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private boolean checkValid(String pass)
    {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern specialChar = Pattern.compile("[\\p{P}\\p{S}]");
        if(pass.length()<8)
            return false;
        if(!uppercase.matcher(pass).find())
            return false;
        if(!lowercase.matcher(pass).find())
            return false;
        if(!digit.matcher(pass).find())
            return false;
        if(!specialChar.matcher(pass).find())
            return false;

//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(pass);

        return true;
    }
    private TextWatcher emptyCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gUsername = username.getText().toString();
            gPassword = password.getText().toString();
            if(TextUtils.isEmpty(gUsername))
            {

            }
            if(!checkValid(gPassword))
            {
                password.setError("Not Valid");
            }
            else
            {
                password.setError("Valid");
            }
            if(!confirm.getText().toString().equals(gPassword))
            {
                confirm.setError("Does not match");
            }
            else
            {
                confirm.setError("OK this is epic");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
