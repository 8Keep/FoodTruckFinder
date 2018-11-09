package com.example.baohong.poop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button FoodTruck, Vendor;
    private TextView Create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodTruck = (Button) findViewById(R.id.ft);
        Vendor = (Button) findViewById(R.id.vendor);
        Create = (TextView) findViewById(R.id.creAcc);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Create1.class);
                startActivity(intent);
            }
        });
    }
}
