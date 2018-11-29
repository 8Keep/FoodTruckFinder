package com.example.baohong.poop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickClick;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;

public class Profile_page extends AppCompatActivity implements IPickResult {
    private ImageView avatar;
    private Button importIMG;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        avatar = findViewById(R.id.avatar);
        importIMG = findViewById(R.id.importIMG);
        Picasso.get().load(R.drawable.cam).into(avatar);

        importIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog();
            }
        });

    }
    private void setupDialog()
    {

        PickImageDialog.build(new PickSetup()).show(this);

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if(pickResult.getError() == null)
        {

            Uri path = pickResult.getUri();
            bitmap = pickResult.getBitmap();
            Picasso.get().load(path).resize(480,320).centerCrop().into(avatar);
        }
        else
        {
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
