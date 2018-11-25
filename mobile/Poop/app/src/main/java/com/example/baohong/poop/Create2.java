package com.example.baohong.poop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class Create2 extends CreateActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        DoneCheck = (EditText) findViewById(R.id.phone);
        email.addTextChangedListener(emptyCheck);
        phone.addTextChangedListener(emptyCheck);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private TextView.OnEditorActionListener flistener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                startActivity(new Intent(Create2.this,Create3.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
            return false;
        }
    };
    private TextWatcher emptyCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gEmail = email.getText().toString();
            gPhone = phone.getText().toString();
            if(TextUtils.isEmpty(gEmail))
                email.setError("Please enter your email");
            if(TextUtils.isEmpty(gPhone))
                phone.setError("Please enter your phone number");
            if(!TextUtils.isEmpty(gEmail) && !TextUtils.isEmpty(gPhone))
                DoneCheck.setOnEditorActionListener(flistener);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
