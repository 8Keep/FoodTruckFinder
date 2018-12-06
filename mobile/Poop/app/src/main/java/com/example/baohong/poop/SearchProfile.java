package com.example.baohong.poop;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchProfile extends AppCompatActivity {
    private TextView name, usernameTV, address, city, state, zip, description, email, phone, entityName;
    private SharedPreferences sp;
    private Boolean isVendor;
    private String username;
    Bundle bundle;
    private String avaURL;
    private ImageView avatar;
    private final String DEF_IMG = "https://www.peopleorderourpatties.com/backend/images/default.png";
    private final String SHOW_IMG = "https://www.peopleorderourpatties.com/backend/api2/foodtrucks/showUserImg.php";
    private final String SHOW_IMGET = "https://www.peopleorderourpatties.com/backend/api2/vendors/showUserImg.php";
    private String showProfileURL = "https://www.peopleorderourpatties.com/backend/api2/foodtrucks/showProfile.php";
    private String showProfileURLET = "https://www.peopleorderourpatties.com/backend/api2/vendors/showProfile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        isVendor = sp.getBoolean("isVendor", false);

        bundle = getIntent().getExtras();
        username = bundle.getString("SearchedUser");

        avatar = findViewById(R.id.avatar);

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
        loadProfileIMG();
        showProfile();
    }

    private void showProfile(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sendingURL;
        if(!isVendor)
        {
            sendingURL = showProfileURLET;
        }
        else
            sendingURL = showProfileURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendingURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("records");
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                name.setText(o.getString("first") +" "+ o.getString("last"));
                                if(!isVendor)
                                    entityName.setText(o.getString("ET_Name"));
                                else
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
                                {
                                    description.setText(des);
                                }
                                else
                                {
                                    description.setText("No description yet.");
                                }



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
    private void loadProfileIMG(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sendingURL;
        if(!isVendor)
            sendingURL = SHOW_IMGET;
        else
            sendingURL = SHOW_IMG;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendingURL, jsonObject,
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

                                if(avaURL.isEmpty() && avaURL != "null")
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
                Toast.makeText(SearchProfile.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
}
