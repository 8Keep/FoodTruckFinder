package com.example.baohong.poop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {
    private Button FoodTruck, Vendor;
    private TextView Create;
    private boolean isVendor;
    SharedPreferences sp;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodTruck = (Button) findViewById(R.id.login);
        Vendor = (Button) findViewById(R.id.vendor);
        bundle = new Bundle();
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(FirstPage.this, MainContentPg.class);
            startActivity(intent);
            finish();
        }
        Create = (TextView) findViewById(R.id.creAcc);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, CreateVenOrFT.class);
                startActivity(intent);
            }
        });
        FoodTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, Login.class);
                isVendor = false;
                bundle.putBoolean("isVendor", isVendor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, Login.class);
                isVendor = true;
                bundle.putBoolean("isVendor", isVendor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
