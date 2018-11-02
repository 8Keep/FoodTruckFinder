package com.example.baohong.poop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class Create3 extends CreateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);
        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zip = (EditText) findViewById(R.id.zip);
        DoneCheck = (EditText) findViewById(R.id.zip);
        street.addTextChangedListener(emptyCheck);
        city.addTextChangedListener(emptyCheck);
        state.addTextChangedListener(emptyCheck);
        zip.addTextChangedListener(emptyCheck);
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
                startActivity(new Intent(Create3.this,Create4.class));
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
                DoneCheck.setOnEditorActionListener(flistener);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
