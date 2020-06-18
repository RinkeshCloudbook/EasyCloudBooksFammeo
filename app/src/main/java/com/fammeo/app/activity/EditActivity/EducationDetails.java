package com.fammeo.app.activity.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.fammeo.app.activity.WorkEducation;
import com.fammeo.app.adapter.fammeoAdapter.EducationCityListAdapter;
import com.fammeo.app.app.App;
import com.fammeo.app.common.DataGlobal;
import com.fammeo.app.model.CommonModel;
import com.fammeo.app.util.CustomAuthRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fammeo.app.constants.Constants.METHOD_GET_SEARCHCITY_USER;

public class EducationDetails extends AppCompatActivity {
    private static final String TAG = EducationDetails.class.getSimpleName();
    EditText edt_city,edt_dec,Expedt_city,Expedt_dec,Orgedt_city,Orgedt_dec;
    String cName,state,country, getOrgName,flage;
    public CustomAuthRequest request;
    List<CommonModel> mCityList = new ArrayList<>();

    AutoCompleteTextView autoComplete,ExpautoComplete,OrgautoComplete;
    String[] structure = {"University ","College","School","Educational Insitute","Other"};
    String[] exp_structure = {"Company ","Public Limited","Privat Limited","Partnership","HUF","Proprietoship"
            ,"Non-Profite","Association/Club","NGO","Trus","Association","Goverment Agency","Intergovermental"
            ,"Other","Individual","Banking","Electricity","Insurance","Forieng","One Person Company","Society","Local Authority"
            ,"Artificial Juridical Person","Body of Individual"};

    RecyclerView recycler_add_type,Exprecycler_add_type,Orgrecycler_add_type;
    AppCompatButton bt_save_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_details);

        autoComplete = findViewById(R.id.autoComplete);
        ExpautoComplete = findViewById(R.id.ExpautoComplete);
        OrgautoComplete = findViewById(R.id.OrgautoComplete);

        edt_city = findViewById(R.id.edt_city);
        Orgedt_city = findViewById(R.id.Orgedt_city);
        Expedt_city = findViewById(R.id.Expedt_city);

        edt_dec = findViewById(R.id.edt_dec);
        Expedt_dec = findViewById(R.id.Expedt_dec);
        Orgedt_dec = findViewById(R.id.Orgedt_dec);

        recycler_add_type = findViewById(R.id.recycler_add_type);
        Exprecycler_add_type = findViewById(R.id.Exprecycler_add_type);
        Orgrecycler_add_type = findViewById(R.id.Orgrecycler_add_type);

        bt_save_about = findViewById(R.id.bt_save_about);

        RecyclerView.LayoutManager addsrecy = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_add_type.setLayoutManager(addsrecy);
        RecyclerView.LayoutManager recy = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        Exprecycler_add_type.setLayoutManager(recy);
        RecyclerView.LayoutManager rv = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        Orgrecycler_add_type.setLayoutManager(rv);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
             getOrgName = bundle.getString("ON");
             flage = bundle.getString("E");

            ((TextView) findViewById(R.id.txt_getName)).setText("Education :"+getOrgName);
            if(flage.equalsIgnoreCase("edu")){
                bt_save_about.setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.lin_education)).setVisibility(View.VISIBLE);
            }else if(flage.equalsIgnoreCase("exp")) {
                ((TextView) findViewById(R.id.dailog_header)).setText("Experience Details");
                ((AppCompatButton) findViewById(R.id.bt_Expsave_about)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt_ExpgetName)).setText(getOrgName);
                ((LinearLayout) findViewById(R.id.lin_Experience)).setVisibility(View.VISIBLE);
                expGetData();
            }else if(flage.equalsIgnoreCase("org")){
                ((TextView) findViewById(R.id.dailog_header)).setText("Organization Details");
                ((AppCompatButton) findViewById(R.id.bt_Orgsave_about)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.lin_Orgerience)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txt_OrggetName)).setText(getOrgName);
                organiseGetData();
            }
        }
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edt_city.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {

                if (s.length() >= 3) {
                    recycler_add_type.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seachCity(s.toString(),"edu");
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,structure);
        autoComplete.setAdapter(adapter);
        autoComplete.setThreshold(1);

        ((ImageButton) findViewById(R.id.bt_clear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_city.setText("");
            }
        });

        bt_save_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Test","Get Details Flage :"+flage);
                Intent intent = new Intent(getApplicationContext(), ConnectOrg.class);
                intent.putExtra("ON",getOrgName);
                intent.putExtra("E",flage);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.btn_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoComplete.setText("");
                edt_city.setText("");
                edt_dec.setText("");

            }
        });
        ((Button) findViewById(R.id.Expbtn_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpautoComplete.setText("");
                Expedt_city.setText("");
                Expedt_dec.setText("");

            }
        });
        ((Button) findViewById(R.id.Orgbtn_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrgautoComplete.setText("");
                Orgedt_city.setText("");
                Orgedt_dec.setText("");

            }
        });
    }

    private void expGetData() {
        Expedt_city.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {

                if (s.length() >= 3) {
                    Exprecycler_add_type.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seachCity(s.toString(),"exp");
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,exp_structure);
        ExpautoComplete.setAdapter(adapter);
        ExpautoComplete.setThreshold(1);

        ((AppCompatButton) findViewById(R.id.bt_Expsave_about)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConnectOrg.class);
                intent.putExtra("ON",getOrgName);
                intent.putExtra("E",flage);
                startActivity(intent);
            }
        });
    }

    private void organiseGetData() {
        Orgedt_city.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {

                if (s.length() >= 3) {
                    Exprecycler_add_type.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seachCity(s.toString(),"org");
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();
                }
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,exp_structure);
        OrgautoComplete.setAdapter(adapter);
        OrgautoComplete.setThreshold(1);

        ((AppCompatButton) findViewById(R.id.bt_Orgsave_about)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void seachCity(final String cName, final String getFlage) {
        // pbHeaderProgress.setVisibility(View.VISIBLE);
        Log.e("TEST", "Search City :" + cName);
        // list.clear();
        request = new CustomAuthRequest(Request.Method.POST, METHOD_GET_SEARCHCITY_USER, null, 0,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (App.getInstance().authorizeSimple(response)) {
                            String strResponse = response.toString();
                            Log.e("TEST", "Search City Response :" + response.toString());
                            if (strResponse != null) {
                                mCityList.clear();
                                try {
                                    JSONObject object = new JSONObject(strResponse);
                                    String msgType = object.getString("MessageType");
                                    if (msgType.equalsIgnoreCase("success")) {
                                        // bt_save_address.setVisibility(View.VISIBLE);
                                    }
//                                        JSONObject obj = object.getJSONObject("obj");
                                    JSONArray addOBJ = object.getJSONArray("obj");
                                    for (int i = 0; i < addOBJ.length(); i++) {
                                        CommonModel cm = new CommonModel();
                                        JSONObject ob = addOBJ.getJSONObject(i);
                                        cm.cN = ob.getString("N");
                                        cm.cState = ob.getString("S");
                                        cm.cCountry = ob.getString("CR");
                                        cm.cSC = ob.getString("SC");
                                        cm.cCRC = ob.getString("CRC");

                                        //String name = cm.cN + ", " + cm.cState + ", " + cm.cCountry;
                                        mCityList.add(cm);
                                    }
                                    EducationCityListAdapter adapter = new EducationCityListAdapter(EducationDetails.this, mCityList,getFlage);
                                    if(getFlage.equalsIgnoreCase("edu")){
                                        recycler_add_type.setAdapter(adapter);
                                    }else if(getFlage.equalsIgnoreCase("exp")){
                                        Exprecycler_add_type.setAdapter(adapter);
                                    }else if(getFlage.equalsIgnoreCase("org")){
                                        Orgrecycler_add_type.setAdapter(adapter);
                                    }

                                    adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //  pbHeaderProgress.setVisibility(View.VISIBLE);
                            //SnakebarCustom.danger(mContext, View , "Unable to fetch contact: " + "No data found", 5000);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pbHeaderProgress.setVisibility(View.VISIBLE);
                //SnakebarCustom.danger(mContext, v, "Unable to fetch Companies: " + error.getMessage(), 5000);
            }
        }) {

            @Override
            protected JSONObject getParams() {
                try {
                    JSONObject params = new JSONObject();
                    params.put("city", cName);
                    params.put("country", "IN");

                    return params;
                } catch (JSONException ex) {
                    DataGlobal.SaveLog(TAG, ex);
                    return null;
                }
            }

            @Override
            protected void onCreateFinished(CustomAuthRequest request) {
                int socketTimeout = 300000;//0 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.customRequest.setRetryPolicy(policy);
                App.getInstance().addToRequestQueue(request);
            }
        };
    }

    public void cityName(String cN, String cState, String cCountry, String fullCname,String setFlage){
        Log.e("TEST","Education cState :"+cState);
        Log.e("TEST","Get Flage :"+setFlage);

        cName = cN;
        state = cState;
        country = cCountry;
        if(setFlage.equalsIgnoreCase("edu")){
            edt_city.setText(fullCname);
        }else  if(setFlage.equalsIgnoreCase("exp")){
            Expedt_city.setText(fullCname);
        }else  if(setFlage.equalsIgnoreCase("org")){
            Orgedt_city.setText(fullCname);
        }
        Orgedt_city.setText(fullCname);
        recycler_add_type.setVisibility(View.GONE);
        Exprecycler_add_type.setVisibility(View.GONE);
        Orgrecycler_add_type.setVisibility(View.GONE);
    }
}
