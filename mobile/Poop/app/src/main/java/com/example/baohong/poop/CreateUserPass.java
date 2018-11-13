package com.example.baohong.poop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class CreateUserPass extends CreateActivity {
    private String gUsername,gPassword;

    private TextView passHint, confirmErr;
    private boolean isVendor;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserpass);
        bundle = getIntent().getExtras();
        for (String key : bundle.keySet())
        {
            Log.d("Bundle Debug", key + " = \"" + bundle.get(key) + "\"");
        }
        isVendor = bundle.getBoolean("isVendor");
        username = (EditText) findViewById(R.id.username);
        password = findViewById(R.id.password1);
        confirm = findViewById(R.id.confirm1);
        passHint = findViewById(R.id.passHint);
        confirmErr = findViewById(R.id.confirmErr);
        passHint.setVisibility(View.INVISIBLE);//hind the passhint
        confirmErr.setVisibility(View.INVISIBLE);
        username.addTextChangedListener(emptyCheck);
        password.addTextChangedListener(emptyCheck);
        confirm.addTextChangedListener(emptyCheck);
        username.requestFocus();
        next = findViewById(R.id.submit);
        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject loginObject = new JSONObject();
                JSONObject infoObject = new JSONObject();
//              boolean connect = checkNetworkConnection();
                try {
                    loginObject.put("username", gUsername);
                    loginObject.put("password", gPassword);
                    if(isVendor)
                    {
                        infoObject.put("name", bundle.getString("fname") + " " + bundle.getString("lname"));
                        infoObject.put("address", bundle.getString("gStreet"));
                        infoObject.put("city", bundle.getString("gCity"));
                        infoObject.put("state", bundle.getString("gState"));
                        infoObject.put("zip", bundle.getString("gZip"));
                    }
                    else
                    {
                        infoObject.put("truck_name", bundle.getString("FTname"));
                        infoObject.put("city", bundle.getString("gCity"));
                        infoObject.put("state", bundle.getString("gState"));
                        infoObject.put("zip", bundle.getString("gZip"));
                        infoObject.put("range", "50-100");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(isVendor)
                {
                    //10.0.2.2 only work on emulator. Need to change if work on real phone. Please google this.
                    new HTTPAsyncTask().execute("http://10.0.2.2/api/users/vendors/create.php", loginObject.toString());
                    new HTTPAsyncTask().execute("http://10.0.2.2/api/vendors/edit.php", infoObject.toString());
                }
                else
                {
                    new HTTPAsyncTask().execute("http://10.0.2.2/api/users/foodtrucks/create.php", loginObject.toString());
                    new HTTPAsyncTask().execute("http://10.0.2.2/api/foodtrucks/edit.php", infoObject.toString());
                }

            }
        });

    }
//    public boolean checkNetworkConnection() {
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        boolean isConnected = false;
//        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
//            // show "Connected" & type of network "WIFI or MOBILE"
//            confirmErr.setVisibility(View.VISIBLE);
//            confirmErr.setText("Connected "+networkInfo.getTypeName());
//            // change background color to red
//            confirmErr.setBackgroundColor(0xFF7CCC26);
//
//
//        } else {
//            // show "Not Connected"
//            confirmErr.setVisibility(View.VISIBLE);
//            confirmErr.setText("Not Connected");
//            // change background color to green
//            confirmErr.setBackgroundColor(0xFFFF0000);
//        }
//
//        return isConnected;
//    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    private boolean checkValid(String pass)
    {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern specialChar = Pattern.compile("[\\p{P}\\p{S}]");
        if(pass.length()<8)
            return false;
        if(!uppercase.matcher(pass).find())
            return false;
        if(!lowercase.matcher(pass).find())
            return false;
        if(!digit.matcher(pass).find())
            return false;
        if(!specialChar.matcher(pass).find())
            return false;


        return true;
    }
    private TextWatcher emptyCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gUsername = username.getText().toString();
            gPassword = password.getText().toString();
            if(password.isFocused())
                passHint.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(gUsername))
            {

            }
            if(!checkValid(gPassword))
            {
                passHint.setTextColor(Color.RED);
            }
            else
            {
                passHint.setVisibility(View.INVISIBLE);
            }
            if(!confirm.getText().toString().equals(gPassword))
            {
                confirmErr.setVisibility(View.VISIBLE);
                confirmErr.setTextColor(Color.RED);
            }
            else
            {
                confirmErr.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(gUsername))
            {
                username.setError("Please enter an username");
            }
            if(!TextUtils.isEmpty(gUsername) && checkValid(gPassword) && confirm.getText().toString().equals(gPassword))
            {
                next.setEnabled(true);
                next.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.

            try {
                try {
                    return HttpPost(urls[0], urls[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            boolean isCreated = false;
            ;
            if(result.equals("OK"))
            {
                isCreated = true;
            }
            if(isCreated) {
                Toast.makeText(CreateUserPass.this, "Your account was successfully created", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateUserPass.this, FirstPage.class);
                bundle.putBoolean("isCreated", isCreated);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else
            {
                Toast.makeText(CreateUserPass.this, "Your account was failed to create", Toast.LENGTH_LONG).show();
            }

        }
        private String HttpPost(String myUrl, String JSON) throws IOException, JSONException {
            String result = "";

            URL url = new URL(myUrl);

            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoOutput(true);
            // 2. build JSON object
            // 3. add JSON content to POST request body
            setPostRequestContent(conn, JSON);
            // 4. make POST request to the given URL
            conn.connect();

            // 5. return response message
            return conn.getResponseMessage()+"";

        }
        private void setPostRequestContent(HttpURLConnection conn,
                                          String jsonObject){
            try {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonObject);
                writer.flush();
                writer.close();
                os.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }

}
