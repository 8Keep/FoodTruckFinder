package com.example.baohong.poop;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickClick;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
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
    private boolean isVendor;
    private String username;
    private String avaURL;
    SharedPreferences sp;
    TextView name, usernameTV, address, city, state, zip, description, email, phone, entityName, entity;
    EditText nameED, usernameED, addressED, cityED, stateED, zipED, descriptionED, emailED, phoneED, entityNameED;
    Button edit, editED;
    //https://www.peopleorderourpatties.com/backend
    //http://192.168.0.17
//    private final String DEF_IMG = "https://www.peopleorderourpatties.com/backend/images/default.png";
//    private final String SHOW_IMG = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/showUserImg.php";
//    private String uploadURL = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/addimg.php";
    private final String DEF_IMG = "https://www.peopleorderourpatties.com/backend/images/default.png";
    private final String SHOW_IMG = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/showUserImg.php";
    private String uploadURL = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/addimg.php";
    private String editProfileURL = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/editProfile.php";
    private String showProfileURL = "https://www.peopleorderourpatties.com/backend/api/foodtrucks/showProfile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        avatar = findViewById(R.id.avatar);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        username = sp.getString("username","");
        Log.d("username", username);
        importIMG = findViewById(R.id.importIMG);
        isVendor = sp.getBoolean("isVendor", false);

        entity = findViewById(R.id.entity);

        name = findViewById(R.id.name);
        usernameTV = findViewById(R.id.username);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zip = findViewById(R.id.zip);
        description = findViewById(R.id.description);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        entityName = findViewById(R.id.entityName);

        nameED = findViewById(R.id.nameED);
        usernameED = findViewById(R.id.usernameED);
        addressED = findViewById(R.id.addressED);
        cityED = findViewById(R.id.cityED);
        stateED = findViewById(R.id.stateED);
        zipED = findViewById(R.id.zipED);
        descriptionED = findViewById(R.id.descriptionED);
        emailED = findViewById(R.id.emailED);
        phoneED = findViewById(R.id.phoneED);
        entityNameED = findViewById(R.id.entityNameED);

        edit = findViewById(R.id.edit);
        editED = findViewById(R.id.editDone);

        showProfile();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.GONE);
                usernameTV.setVisibility(View.GONE);
                address.setVisibility(View.INVISIBLE);
                city.setVisibility(View.INVISIBLE);
                state.setVisibility(View.INVISIBLE);
                zip.setVisibility(View.INVISIBLE);
                description.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                entityName.setVisibility(View.GONE);

                nameED.setVisibility(View.VISIBLE);
                usernameED.setVisibility(View.VISIBLE);
                addressED.setVisibility(View.VISIBLE);
                cityED.setVisibility(View.VISIBLE);
                stateED.setVisibility(View.VISIBLE);
                zipED.setVisibility(View.VISIBLE);
                descriptionED.setVisibility(View.VISIBLE);
                editED.setVisibility(View.VISIBLE);
                emailED.setVisibility(View.VISIBLE);
                phoneED.setVisibility(View.VISIBLE);
                entityNameED.setVisibility(View.VISIBLE);




            }
        });
        editED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameED.setVisibility(View.GONE);
                usernameED.setVisibility(View.GONE);
                addressED.setVisibility(View.INVISIBLE);
                cityED.setVisibility(View.INVISIBLE);
                stateED.setVisibility(View.INVISIBLE);
                zipED.setVisibility(View.INVISIBLE);
                descriptionED.setVisibility(View.GONE);
                editED.setVisibility(View.GONE);
                emailED.setVisibility(View.GONE);
                phoneED.setVisibility(View.GONE);
                entityNameED.setVisibility(View.GONE);

                name.setVisibility(View.VISIBLE);
                usernameTV.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                city.setVisibility(View.VISIBLE);
                state.setVisibility(View.VISIBLE);
                zip.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                entityName.setVisibility(View.VISIBLE);
                editProfile();
                sp.edit().putString("username", usernameED.getText().toString()).apply();
                finish();
                startActivity(getIntent());
            }
        });
        if(isVendor)
            entity.setText("Vendor");
        else
            entity.setText("Food Truck");

        loadProfileIMG();


        importIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog();
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        showProfile();
//    }
    private void showProfile(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showProfileURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("records");
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                name.setText(o.getString("first") +" "+ o.getString("last"));
                                entityName.setText(o.getString("TruckName"));
                                usernameTV.setText(o.getString("username"));
                                email.setText(o.getString("email"));
                                phone.setText(o.getString("phone"));
                                address.setText(o.getString("address"));
                                city.setText(o.getString("city"));
                                state.setText(o.getString("state"));
                                zip.setText(o.getString("zip"));
                                String des = o.getString("description");
                                if(des != "null" && !des.isEmpty())
                                    description.setText(des);
                                else
                                    description.setText("No description yet.");

                                nameED.setText(o.getString("first") +" "+ o.getString("last"));
                                entityNameED.setText(o.getString("TruckName"));
                                usernameED.setText(o.getString("username"));
                                emailED.setText(o.getString("email"));
                                phoneED.setText(o.getString("phone"));
                                addressED.setText(o.getString("address"));
                                cityED.setText(o.getString("city"));
                                stateED.setText(o.getString("state"));
                                zipED.setText(o.getString("zip"));
                                descriptionED.setText(o.getString("description"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    private void editProfile()
    {
        JSONObject jsonObject = prepareEditJSON();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, editProfileURL, jsonObject,
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
    private JSONObject prepareEditJSON()
    {
        String[] name = nameED.getText().toString().split(" ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first", name[0]);
            jsonObject.put("last", name[1]);
            jsonObject.put("usernameBefore", username);
            jsonObject.put("truck_name", entityNameED.getText().toString());
            jsonObject.put("username", usernameED.getText().toString());
            jsonObject.put("email", emailED.getText().toString());
            jsonObject.put("phone", phoneED.getText().toString());
            jsonObject.put("address", addressED.getText().toString());
            jsonObject.put("city", cityED.getText().toString());
            jsonObject.put("state", stateED.getText().toString());
            jsonObject.put("zip", zipED.getText().toString());
            jsonObject.put("description", descriptionED.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    private void loadProfileIMG(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SHOW_IMG, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("records");
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                avaURL = o.getString("imgURL");
                                Log.d("Error1", avaURL);

                                if(avaURL.isEmpty())
                                {
                                    Picasso.get().load(DEF_IMG).resize(avatar.getWidth(), 480).centerCrop().into(avatar);
                                }
                                else
                                {
                                    Picasso.get().load(avaURL).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE).resize(avatar.getWidth(), 480).centerCrop().into(avatar);
                                }

                            }

                        } catch (JSONException e) {
                            Log.d("Error1", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error2", error.getMessage());
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
    private void setupDialog()
    {

        PickImageDialog.build(new PickSetup()).show(this);

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if(pickResult.getError() == null)
        {

            bitmap = pickResult.getBitmap();
            uploadImage();
            loadProfileIMG();
            finish();
            startActivity(getIntent());

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
