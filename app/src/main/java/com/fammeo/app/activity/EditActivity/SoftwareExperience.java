package com.fammeo.app.activity.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fammeo.app.R;

public class SoftwareExperience extends AppCompatActivity {

    EditText edt_software;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_experience);

        edt_software = findViewById(R.id.edt_software);
        edt_software.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= 3){
                    ((ImageButton) findViewById(R.id.btn_addsoftware)).setVisibility(View.VISIBLE);
                    ((AppCompatButton) findViewById(R.id.bt_save_software)).setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },300);
                    lastChange = System.currentTimeMillis();
                }else if(s.length() < 3){
                    ((ImageButton) findViewById(R.id.btn_addsoftware)).setVisibility(View.GONE);
                    ((AppCompatButton) findViewById(R.id.bt_save_software)).setVisibility(View.GONE);
                }
            }
        });

        ((ImageButton) findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });((ImageButton) findViewById(R.id.btn_addsoftware)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText =edt_software.getText().toString();
                Toast.makeText(getApplication(),getText,Toast.LENGTH_LONG).show();
                //CommonModel cm = new CommonModel();
                // cm.lName = getText;

                //skillList.add(cm);
                // lisyAdapter.notifyDataSetChanged();
            }
        });
    }
}
