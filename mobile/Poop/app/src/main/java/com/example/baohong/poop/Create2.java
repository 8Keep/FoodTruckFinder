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
import android.view.inputmethod.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Create2 extends CreateActivity {
    private String gfname, glname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        DoneCheck = (EditText) findViewById(R.id.lname);
        next = (Button) findViewById(R.id.next);
        next.setEnabled(false);
        next.setVisibility(View.GONE);
        fname.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        fname.addTextChangedListener(emptyCheck);
        lname.addTextChangedListener(emptyCheck);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToNext();
//                Intent intent = new Intent(Create2.this,Create3.class);
//                Bundle bundle = getIntent().getExtras();
//                bundle.putString("fname", gfname);
//                bundle.putString("lname", glname);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });



    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void sendToNext()
    {
        Intent intent = new Intent(Create2.this,Create3.class);
        Bundle bundle = getIntent().getExtras();
        for (String key : bundle.keySet())
        {
            Log.d("Bundle Debug", key + " = \"" + bundle.get(key) + "\"");
        }
        bundle.putString("fname", gfname);
        bundle.putString("lname", glname);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private TextView.OnEditorActionListener flistener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                sendToNext();
//                Intent intent = new Intent(Create2.this,Create3.class);
//                Bundle bundle = getIntent().getExtras();
//                bundle.putString("fname", gfname);
//                bundle.putString("lname", glname);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
            {
                fname.setError("Please enter your first name");
            }
            if(TextUtils.isEmpty(glname))
            {
                lname.setError("Please enter your last name");
            }
            if(!TextUtils.isEmpty(gfname) && !TextUtils.isEmpty(glname))
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
