package com.example.baohong.poop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class Create1 extends CreateActivity {
     private Context c1;
     private Class c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        DoneCheck = (EditText) findViewById(R.id.lname);
        fname.addTextChangedListener(emptyCheck);
        lname.addTextChangedListener(emptyCheck);



    }

    private TextView.OnEditorActionListener flistener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                startActivity(new Intent(Create1.this,Create2.class));
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
            gfname = fname.getText().toString();
            glname = lname.getText().toString();
            if(TextUtils.isEmpty(gfname))
                fname.setError("Please enter your first name");
            if(TextUtils.isEmpty(glname))
                lname.setError("Please enter your last name");
            if(!TextUtils.isEmpty(gfname) && !TextUtils.isEmpty(glname))
                DoneCheck.setOnEditorActionListener(flistener);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
