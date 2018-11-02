package com.example.baohong.poop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Create4 extends CreateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create4);
    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
