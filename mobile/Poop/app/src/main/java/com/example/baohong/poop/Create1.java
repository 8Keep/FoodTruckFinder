package com.example.baohong.poop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class Create1 extends CreateActivity {
    private ToggleButton vendor, ft;
    private boolean isVendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);
        vendor = (ToggleButton) findViewById(R.id.vendor);
        ft = (ToggleButton) findViewById(R.id.ft);
        next = (Button) findViewById(R.id.next);
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
                Log.i("Create1", "1 "+ isVendor);

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
                Log.i("Create1", "2 "+ isVendor);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Create1.this,Create2.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isVendor", isVendor);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

}
