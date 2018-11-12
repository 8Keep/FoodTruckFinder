package com.example.baohong.poop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class CreateVenOrFT extends CreateActivity {
    private ToggleButton vendor, ft;
    private boolean isVendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createvenorft);
        vendor = (ToggleButton) findViewById(R.id.vendor);
        ft = (ToggleButton) findViewById(R.id.ft);
        next = findViewById(R.id.next);
        if(!vendor.isChecked() && !ft.isChecked())
        {
            next.setEnabled(false);
            next.setVisibility(View.GONE);
        }
        vendor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ft.setChecked(false);
                    next.setEnabled(true);
                    isVendor = true;
                    next.setVisibility(View.VISIBLE);

                }
                else
                {
                    if(!ft.isChecked())
                    {
                        next.setEnabled(false);
                        next.setVisibility(View.GONE);
                        isVendor = false;
                    }
                }


            }
        });
        ft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    vendor.setChecked(false);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                    isVendor = false;
                }
                else
                {
                    if(!vendor.isChecked())
                    {
                        next.setEnabled(false);
                        next.setVisibility(View.GONE);
                        isVendor = true;
                    }

                }


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateVenOrFT.this,CreateName.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isVendor", isVendor);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

}
