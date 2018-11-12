package com.example.baohong.poop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class CreateContactInfo extends CreateActivity {
    private String gEmail,gPhone;
    private boolean isVendor;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcontactinfo);
        bundle = getIntent().getExtras();
        isVendor = bundle.getBoolean("isVendor");
//        for (String key : bundle.keySet())
//        {
//            Log.d("Bundle Debug", key + " = \"" + bundle.get(key) + "\"");
//        }
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        DoneCheck = (EditText) findViewById(R.id.phone);
        email.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        next = findViewById(R.id.next);
        next.setEnabled(false);
        next.setVisibility(View.GONE);
        email.addTextChangedListener(emptyCheck);
        phone.addTextChangedListener(emptyCheck);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToNext();
            }
        });
    }
    public void sendToNext()
    {
        Intent intent1 = new Intent(CreateContactInfo.this,CreateVenAddress.class);
        Intent intent2 = new Intent(CreateContactInfo.this,CreateFTLocation.class);
        bundle.putString("gEmail", gEmail);
        bundle.putString("gPhone", gPhone);
        if(isVendor) {
            intent1.putExtras(bundle);
            startActivity(intent1);
        }
        else {
            intent2.putExtras(bundle);
            startActivity(intent2);
        }
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                sendToNext();
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
            if(!isEmailValid(gEmail))
            {
                email.setError("Email is not valid");
            }
            if(TextUtils.isEmpty(gPhone))
                phone.setError("Please enter your phone number");
            if(!TextUtils.isEmpty(gEmail) && !TextUtils.isEmpty(gPhone) && isEmailValid(gEmail))
            {
                next.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                DoneCheck.setOnEditorActionListener(flistener);
            }
            else
            {
                next.setEnabled(false);
                next.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
