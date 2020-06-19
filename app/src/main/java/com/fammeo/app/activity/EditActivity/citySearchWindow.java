package com.fammeo.app.activity.EditActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.fammeo.app.R;
import com.fammeo.app.adapter.fammeoAdapter.CityListAdapter;
import com.fammeo.app.animation.ViewAnimation;
import com.fammeo.app.app.App;
import com.fammeo.app.common.DataGlobal;
import com.fammeo.app.model.CommonModel;
import com.fammeo.app.util.CustomAuthRequest;
import com.fammeo.app.util.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fammeo.app.constants.Constants.METHOD_GET_SEARCHCITY_USER;

public class citySearchWindow extends AppCompatActivity {
    private static final String TAG = citySearchWindow.class.getSimpleName();
    public CustomAuthRequest request;
    private Toolbar toolbar;
    EditText et_search;
    private ProgressBar progress_bar;
    private LinearLayout lyt_no_result;
    private RecyclerView recyclerSuggestion;
    List<CommonModel> mCityList = new ArrayList<>();
    String aType, aAdd, id;

    private LinearLayout lyt_suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search_window);

        et_search = findViewById(R.id.et_search);
        progress_bar = findViewById(R.id.progress_bar);
        recyclerSuggestion = findViewById(R.id.recyclerSuggestion);
        lyt_no_result = findViewById(R.id.lyt_no_result);
        lyt_suggestion = findViewById(R.id.lyt_suggestion);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            aType = bundle.getString("T");
            aAdd = bundle.getString("A");
            id = bundle.getString("ID");
        }


        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(this));
        recyclerSuggestion.setHasFixedSize(true);


        initToolbar();

        ((ImageButton) findViewById(R.id.bt_clear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
            Log.e("Test","GET Text :"+s.toString());
                if (s.length() >= 3) {
                    recyclerSuggestion.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seachCity(s.toString());
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    //searchAction();
                    return true;
                }
                return false;
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void seachCity(final String cName) {
        // pbHeaderProgress.setVisibility(View.VISIBLE);
        progress_bar.setVisibility(View.VISIBLE);
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
                                //ViewAnimation.collapse(lyt_suggestion);
                                lyt_suggestion.setVisibility(View.VISIBLE);
                                lyt_no_result.setVisibility(View.GONE);
                                progress_bar.setVisibility(View.GONE);
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
                                    CityListAdapter adapter = new CityListAdapter(citySearchWindow.this, mCityList,aType,aAdd,id);
                                    recyclerSuggestion.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("Test","Get Exception :"+e);
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

    public void cityName(String cN, String cState, String cCountry, String fullCname){
        et_search.setText(fullCname);
        //cName = cN;
        //state = cState;
       // country = cCountry;
        recyclerSuggestion.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
