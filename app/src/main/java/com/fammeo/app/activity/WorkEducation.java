package com.fammeo.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.fammeo.app.R;
import com.fammeo.app.activity.EditActivity.EditAddress;
import com.fammeo.app.activity.EditActivity.EducationDetails;
import com.fammeo.app.adapter.fammeoAdapter.CityListAdapter;
import com.fammeo.app.adapter.fammeoAdapter.EducationCityListAdapter;
import com.fammeo.app.animation.ViewAnimation;
import com.fammeo.app.app.App;
import com.fammeo.app.common.DataGlobal;
import com.fammeo.app.fragment.FammeoFragment.EducationFragment;
import com.fammeo.app.model.CommonModel;
import com.fammeo.app.util.CustomAuthRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fammeo.app.constants.Constants.METHOD_GET_SEARCHCITY_USER;

public class WorkEducation extends AppCompatActivity {


    LinearLayout lyt_expand_text;
    String flage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_education);

        lyt_expand_text = findViewById(R.id.lyt_expand_text);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            flage = bundle.getString("E");
            Log.e("TEST","Education value :"+flage);
        }

        ((ImageButton) findViewById(R.id.bt_toggle_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionText(v);
            }
        });

        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((ImageButton) findViewById(R.id.btn_orgNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgName = ((EditText) findViewById(R.id.edt_orgName)).getText().toString();
                createOrg(orgName);
                hideKeyboard(WorkEducation.this);
            }
        });
        ((ImageView) findViewById(R.id.img_createOrg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getorgName = ((TextView) findViewById(R.id.txt_viewOrgName)).getText().toString();
                Log.e("TEST","Get Org :"+getorgName);
                Intent intent = new Intent(getApplicationContext(), EducationDetails.class);
                intent.putExtra("ON",getorgName);
                intent.putExtra("E",flage);
                startActivity(intent);
            }
        });



    }

    private void createOrg(String orgName) {
        String[] splited = orgName.split(" ");

        String firstLater = splited[0].substring(0,1).toUpperCase();
       /* String secondLater = splited[1].substring(0,1).toUpperCase();
        if(secondLater.length() == 0){
            secondLater = "";
        }
        String imgName = firstLater+secondLater;*/
        //Log.e("TEST","Get F Latter :"+imgName);
        /*
        Glide.with(getActivity()).load(DataText.GetImagePath(App.getInstance().GetUrl()))
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
                    .apply(RequestOptions.circleCropTransform())
                    .into(((ImageView) view.findViewById(R.id.search_image)));
         */
        ((ImageView) findViewById(R.id.search_image)).setImageResource(R.drawable.bg_search_circle);
        ((ImageView) findViewById(R.id.search_image)).setColorFilter(null);
        ((TextView) findViewById(R.id.search_image_text)).setText(firstLater);

        ((TextView) findViewById(R.id.txt_viewOrgName)).setText(orgName);
        ((TextView) findViewById(R.id.txt_orgContent)).setText("Create Organization");
    }

    public static void hideKeyboard(WorkEducation activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
