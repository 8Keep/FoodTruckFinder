package com.example.baohong.poop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile_page extends AppCompatActivity implements IPickResult {
    private ImageView avatar;
    private Button importIMG;
    private Bitmap bitmap;
    private String username;
    SharedPreferences sp;
    private String uploadURL = "http://192.168.0.17/api/foodtrucks/addimg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        avatar = findViewById(R.id.avatar);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        username = sp.getString("username","");
        Log.d("username", username);
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
            Picasso.get().load(path).resize(avatar.getWidth(), 480).centerCrop().into(avatar);
            uploadImage();
        }
        else
        {
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void uploadImage()
    {
        JSONObject data = prepareJSON(username, imageToString(bitmap));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uploadURL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("message");
                            Toast.makeText(Profile_page.this, res, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
                Toast.makeText(Profile_page.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject prepareJSON(String username, String image)
    {
        JSONObject object = new JSONObject();
        Log.d("image", image);
        try {
            object.put("username", username);
            object.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByres = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByres,Base64.DEFAULT);
    }

}