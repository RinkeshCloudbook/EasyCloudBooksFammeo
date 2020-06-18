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

import com.fammeo.app.R;
import com.fammeo.app.model.CommonModel;

public class IndustryExperience extends AppCompatActivity {

    EditText edt_industry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_experience);

        edt_industry = findViewById(R.id.edt_industry);

        edt_industry.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    ((ImageButton) findViewById(R.id.btn_addIndustry)).setVisibility(View.VISIBLE);
                    ((AppCompatButton) findViewById(R.id.bt_save_ind)).setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // seachSkills(s.toString());
                           // recycler_view_skills.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();
                } else if (s.length() < 3) {
                   // recycler_view_skills.setVisibility(View.GONE);
                    ((ImageButton) findViewById(R.id.btn_addIndustry)).setVisibility(View.GONE);
                    ((AppCompatButton) findViewById(R.id.bt_save_ind)).setVisibility(View.GONE);
                }
            }
        });

        ((ImageButton) findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        ((ImageButton) findViewById(R.id.btn_addIndustry)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText =edt_industry.getText().toString();
                //CommonModel cm = new CommonModel();
               // cm.lName = getText;

                //skillList.add(cm);
               // lisyAdapter.notifyDataSetChanged();
            }
        });
    }
}
