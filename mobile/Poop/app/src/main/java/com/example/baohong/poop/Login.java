package com.example.baohong.poop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    private String gUsername, gPassword;
    private TextInputEditText username, password;
    private Button login;
    private TextView forgot, register;
    SharedPreferences sp;
    private boolean isVendor;
    //192.168.0.17 for real
    //10.0.2.2 for emu
    private String localhostip = "192.168.0.17";
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bundle = getIntent().getExtras();
        isVendor = bundle.getBoolean("isVendor");
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        forgot = findViewById(R.id.forgotPass);
        register = findViewById(R.id.creAcc);
        login = findViewById(R.id.login);
        username.addTextChangedListener(textwatcher);
        password.addTextChangedListener(textwatcher);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        //implement forgot pass later
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CreateVenOrFT.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject logging = new JSONObject();
                try {
                    logging.put("username", gUsername);
                    logging.put("password", gPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(isVendor)
                    new HTTPAsyncTask().execute("http://"+localhostip+"/api/users/vendors/authenticate.php", logging.toString());
                else
                    new HTTPAsyncTask().execute("http://"+localhostip+"/api/users/foodtrucks/authenticate.php", logging.toString());




            }
        });


    }
    private TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gUsername = username.getText().toString();
            gPassword = password.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        String res;
        JSONObject resb;
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
        protected void onPostExecute(String result){
            if(!result.equals("OK"))
                Toast.makeText(Login.this, "Please check your connection", Toast.LENGTH_LONG).show();
            else
            {
                String resa = null;
                try {
                    resa = resb.getString("message");
                    Toast.makeText(Login.this, resa, Toast.LENGTH_LONG).show();
                    Log.d("Echo", resa);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(resa.equals("Successful Login!"))
                {


                    AlertDialog.Builder remember = new AlertDialog.Builder(Login.this);
                    remember.setMessage("Do you want to be remembered and stayed logged in?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sp.edit().putBoolean("logged", true).apply();
                                    sp.edit().putString("username", gUsername).apply();
                                    Intent intent = new Intent(Login.this, MainContentPg.class);
                                    finishAffinity();
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sp.edit().putBoolean("logged", false).apply();
                                    sp.edit().putString("username", "Logged out!");
                                    Intent intent = new Intent(Login.this, MainContentPg.class);
                                    finishAffinity();
                                    startActivity(intent);


                                }
                            });
                    AlertDialog rem = remember.create();
                    rem.setTitle("Remember me?");
                    rem.show();


                }
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
            conn.setDoInput(true);
            // 2. build JSON object
            // 3. add JSON content to POST request body
            setPostRequestContent(conn, JSON);
            getPHPecho(conn);
            // 4. make POST request to the given URL
            conn.connect();

            // 5. return response message
            return conn.getResponseMessage()+"";

        }
        private void getPHPecho(HttpURLConnection conn)
        {
            try {

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while (line != null) {
                    line = reader.readLine();
                    sb.append(line + "\n");
                }
                is.close();
                res = sb.toString();
                resb = new JSONObject(res);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
