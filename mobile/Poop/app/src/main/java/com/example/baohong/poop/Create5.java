package com.example.baohong.poop;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class Create5 extends CreateActivity {
    private String gUsername,gPassword;
    Bundle bundle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create5);
        bundle = getIntent().getExtras();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        username.addTextChangedListener(emptyCheck);
        password.addTextChangedListener(emptyCheck);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private boolean checkValid(String pass)
    {
        if(pass.length() < 8)
            return false;
        return true;
    }
    private TextWatcher emptyCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gUsername = username.getText().toString();
            gPassword = username.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
