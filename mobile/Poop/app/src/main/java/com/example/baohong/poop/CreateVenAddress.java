package com.example.baohong.poop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateVenAddress extends CreateActivity {
    private String gStreet,gCity,gState,gZip;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createVenAddress);
        bundle = getIntent().getExtras();
        for (String key : bundle.keySet())
        {
            Log.d("Bundle Debug", key + " = \"" + bundle.get(key) + "\"");
        }
        street = (EditText) findViewById(R.id.username);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zip = (EditText) findViewById(R.id.zip);
        DoneCheck = (EditText) findViewById(R.id.zip);
        next = (Button) findViewById(R.id.next);
        next.setEnabled(false);
        next.setVisibility(View.GONE);
        street.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        street.addTextChangedListener(emptyCheck);
        city.addTextChangedListener(emptyCheck);
        state.addTextChangedListener(emptyCheck);
        zip.addTextChangedListener(emptyCheck);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToNext();
            }
        });
    }
    public void sendToNext()
    {
        Intent intent = new Intent(CreateVenAddress.this,CreateUserPass.class);
        bundle.putString("gStreet", gStreet);
        bundle.putString("gCity", gCity);
        bundle.putString("gState", gState);
        bundle.putString("gZip", gZip);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
            gStreet = street.getText().toString();
            gCity = city.getText().toString();
            gState = state.getText().toString();
            gZip = zip.getText().toString();
            if(TextUtils.isEmpty(gStreet))
                street.setError("Please enter your street");
            if(TextUtils.isEmpty(gCity))
                city.setError("Please enter your city");
            if(TextUtils.isEmpty(gState))
                state.setError("Please enter your state");
            if(TextUtils.isEmpty(gZip))
                zip.setError("Please enter your zip");
            if(!TextUtils.isEmpty(gStreet) && !TextUtils.isEmpty(gCity) && !TextUtils.isEmpty(gState) && !TextUtils.isEmpty(gZip))
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
