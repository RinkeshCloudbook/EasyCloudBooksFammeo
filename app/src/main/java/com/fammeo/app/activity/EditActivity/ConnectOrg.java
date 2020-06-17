package com.fammeo.app.activity.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fammeo.app.R;
import com.fammeo.app.adapter.fammeoAdapter.ExperienceFragment;
import com.fammeo.app.animation.ViewAnimation;
import com.fammeo.app.fragment.VewProfileFragment;

import java.util.Calendar;

public class ConnectOrg extends AppCompatActivity {

    LinearLayout lyt_expand_text,lyt_Expexpand_text;
    AppCompatButton bt_save_about;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_org);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String getName = bundle.getString("ON");
            String flage = bundle.getString("E");
            ((TextView) findViewById(R.id.txt_getName)).setText(getName);
            if(flage.equalsIgnoreCase("edu")){
                ((LinearLayout) findViewById(R.id.lin_educationData)).setVisibility(View.VISIBLE);
                ((AppCompatButton) findViewById(R.id.bt_save_about)).setVisibility(View.VISIBLE);
            }else {
                ((TextView) findViewById(R.id.dailog_header)).setText("Experience Details");
                ((TextView) findViewById(R.id.txt_ExpgetName)).setText(getName);
                ((AppCompatButton) findViewById(R.id.bt_expsave_about)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.lin_ExperienceData)).setVisibility(View.VISIBLE);
                expConnection();
            }
        }

        lyt_expand_text = findViewById(R.id.lyt_expand_text);
        lyt_Expexpand_text = findViewById(R.id.lyt_Expexpand_text);
        bt_save_about = findViewById(R.id.bt_save_about);
        ((ImageButton) findViewById(R.id.bt_toggle_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionText(v);
            }
        });

        ((ImageButton) findViewById(R.id.bt_Exptoggle_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionText(v);
            }
        });

        ((ImageButton) findViewById(R.id.bt_Exptoggle_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exptoggleSectionText(v);
            }
        });

        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((EditText) findViewById(R.id.edt_startDate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetDateOnChange(s,"");
            }
        });

        ((EditText) findViewById(R.id.edt_endDate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetDateOnChange(s,"ed");
            }
        });

        bt_save_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondMainActivity.class);
                startActivity(intent);
            }
        });
        ((Button) findViewById(R.id.btn_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

    }

    private void expConnection() {
        ((EditText) findViewById(R.id.edt_ExpstartDate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetExpDateOnChange(s,"");
            }
        });

        ((EditText) findViewById(R.id.edt_ExpendDate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetExpDateOnChange(s,"exp");
            }
        });

        ((AppCompatButton) findViewById(R.id.bt_expsave_about)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearData() {
        ((EditText) findViewById(R.id.edt_startDate)).setText("");
        ((EditText) findViewById(R.id.edt_endDate)).setText("");
        ((EditText) findViewById(R.id.edt_field)).setText("");
        ((EditText) findViewById(R.id.edt_degree)).setText("");
    }

    private void GetDateOnChange(Editable s,String ed) {
        Log.e("TEST","Get Exp Date :"+s.toString());
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll("[^\\d.]", "");
            String cleanC = current.replaceAll("[^\\d.]", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8){
                clean = clean + ddmmyyyy.substring(clean.length());
            }else{

                int day  = Integer.parseInt(clean.substring(0,2));
                int mon  = Integer.parseInt(clean.substring(2,4));
                int year = Integer.parseInt(clean.substring(4,8));

                if(mon > 12) mon = 12;
                cal.set(Calendar.MONTH, mon-1);
                year = (year<1900)?1900:(year>2100)?2100:year;
                cal.set(Calendar.YEAR, year);

                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                clean = String.format("%02d%02d%02d",day, mon, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            if(ed.equalsIgnoreCase("ed")){
                ((EditText) findViewById(R.id.edt_endDate)).setText(current);
                ((EditText) findViewById(R.id.edt_endDate)).setSelection(sel < current.length() ? sel : current.length());
            }else {
                ((EditText) findViewById(R.id.edt_startDate)).setText(current);
                ((EditText) findViewById(R.id.edt_startDate)).setSelection(sel < current.length() ? sel : current.length());
            }

        }
    }

    private void GetExpDateOnChange(Editable s,String ed) {
        Log.e("TEST","Get Exp Date :"+s.toString());
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll("[^\\d.]", "");
            String cleanC = current.replaceAll("[^\\d.]", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8){
                clean = clean + ddmmyyyy.substring(clean.length());
            }else{

                int day  = Integer.parseInt(clean.substring(0,2));
                int mon  = Integer.parseInt(clean.substring(2,4));
                int year = Integer.parseInt(clean.substring(4,8));

                if(mon > 12) mon = 12;
                cal.set(Calendar.MONTH, mon-1);
                year = (year<1900)?1900:(year>2100)?2100:year;
                cal.set(Calendar.YEAR, year);

                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                clean = String.format("%02d%02d%02d",day, mon, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            if(ed.equalsIgnoreCase("exp")){
                ((EditText) findViewById(R.id.edt_ExpendDate)).setText(current);
                ((EditText) findViewById(R.id.edt_ExpendDate)).setSelection(sel < current.length() ? sel : current.length());
            }else {
                ((EditText) findViewById(R.id.edt_ExpstartDate)).setText(current);
                ((EditText) findViewById(R.id.edt_ExpstartDate)).setSelection(sel < current.length() ? sel : current.length());
            }

        }
    }

    private void toggleSectionText(View v) {
        boolean show = toggleArrow(v);
        if (show) {
            ViewAnimation.expand(lyt_expand_text, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    //Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_text);
        }
    }

    private void exptoggleSectionText(View v) {
        boolean show = exptoggleArrow(v);
        if (show) {
            ViewAnimation.expand(lyt_Expexpand_text, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    //Tools.nestedScrollTo(nested_scroll_view, lyt_expand_text);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_Expexpand_text);
        }
    }

    private boolean exptoggleArrow(View v) {
        if (v.getRotation() == 0) {
            v.animate().setDuration(200).rotation(180);
            return true;
        } else {
            v.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private boolean toggleArrow(View v) {
        if (v.getRotation() == 0) {
            v.animate().setDuration(200).rotation(180);
            return true;
        } else {
            v.animate().setDuration(200).rotation(0);
            return false;
        }
    }
}
