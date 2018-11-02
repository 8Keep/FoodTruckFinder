package com.example.baohong.poop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

abstract public class CreateActivity extends AppCompatActivity {
    protected EditText fname, lname, DoneCheck, phone, email, street, city, state, zip;
    protected String gfname, glname, gEmail, gPhone, gStreet, gCity, gState, gZip;

}

