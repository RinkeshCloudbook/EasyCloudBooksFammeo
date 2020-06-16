package com.fammeo.app.fragment.FammeoFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fammeo.app.R;
import com.fammeo.app.activity.EditActivity.AboutDetails;
import com.fammeo.app.activity.EditActivity.EditAddress;
import com.fammeo.app.activity.EditActivity.EditHobbies;
import com.fammeo.app.activity.EditActivity.EditLanguage;
import com.fammeo.app.activity.EditActivity.EditPhone;
import com.fammeo.app.activity.EditActivity.Skills;
import com.fammeo.app.activity.EditActivity.SocialLink;
import com.fammeo.app.activity.EditEmail;
import com.fammeo.app.adapter.AddressAdapter;
import com.fammeo.app.adapter.LanuageSettingAdapter;
import com.fammeo.app.adapter.fammeoAdapter.EmailDailogeAdapter;
import com.fammeo.app.adapter.fammeoAdapter.EmailListAdapter;
import com.fammeo.app.adapter.fammeoAdapter.HobbyAdapterSetting;
import com.fammeo.app.adapter.fammeoAdapter.PhoneAdapter;
import com.fammeo.app.adapter.fammeoAdapter.SkillAdapterSetting;
import com.fammeo.app.app.App;
import com.fammeo.app.common.DataGlobal;
import com.fammeo.app.common.DataText;
import com.fammeo.app.fragment.VewProfileFragment;
import com.fammeo.app.model.CommonModel;
import com.fammeo.app.model.EmailModel;
import com.fammeo.app.util.CustomAuthRequest;
import com.fammeo.app.view.siv.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.fammeo.app.constants.Constants.METHOD_GET_DELETE_ADDRESS_USER;
import static com.fammeo.app.constants.Constants.METHOD_GET_DELETE_Email_USER;
import static com.fammeo.app.constants.Constants.METHOD_GET_DELETE_PHONE_USER;
import static com.fammeo.app.constants.Constants.METHOD_GET_USERDATA_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private static final String TAG = VewProfileFragment.class.getSimpleName();
    String email_id,getLink,getDec,getTitle,mode;
    LinearLayout pbHeaderProgress;
    public CustomAuthRequest request;
    RecyclerView recycler_view_lang,recycler_view_email,recycler_view_address,recycler_view_phone,recycler_view_hb
            ,recycler_view_sk;
    ArrayList<CommonModel> lanList = new ArrayList<>();
    ArrayList<CommonModel> mAddressList = new ArrayList<>();
    ArrayList<CommonModel> phoneList = new ArrayList<>();
    List<CommonModel> hobbyList = new ArrayList<>();
    List<CommonModel> skillList = new ArrayList<>();
    ArrayList<CommonModel> getFieldList = new ArrayList<>();
    List<CommonModel> profileLangList;
    ArrayList<EmailModel> emailList = new ArrayList<>();
    View mView;
    String pe, peType,userId;
    TextView txt_title,txt_dec,txt_link;
    int phoneLenght;
    JSONArray arr, arrSetting;
    private Button bt_save, bt_save_address;
    private EmailListAdapter emailadapter = null;
    private EmailDailogeAdapter emailAdapterDialogList = null;
    SharedPreferences sp;
    AddressAdapter addsAdapter;
    PhoneAdapter phoneAdapter;
    ImageView bg_image;
    AlertDialog dialog,modedialog;
    ProgressBar pr_imageLoder;
    public final String APP_TAG = "MyCustomAppContect";
    File photoFile;
    public String photoFileName = "photo.jpg";


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_about, container, false);
        pbHeaderProgress = mView.findViewById(R.id.pbHeaderProgress);
        recycler_view_email = mView.findViewById(R.id.recycler_view_email);
        recycler_view_address = mView.findViewById(R.id.recycler_view_address);
        recycler_view_phone = mView.findViewById(R.id.recycler_view_phone);
        recycler_view_lang = mView.findViewById(R.id.recycler_view_lang);
        recycler_view_hb = mView.findViewById(R.id.recycler_view_hb);
        recycler_view_sk = mView.findViewById(R.id.recycler_view_sk);
        txt_title = mView.findViewById(R.id.txt_title);
        txt_dec = mView.findViewById(R.id.txt_dec);
        txt_link = mView.findViewById(R.id.txt_link);

        setRecleyViewManager(recycler_view_email);
        setRecleyViewManager(recycler_view_address);
        setRecleyViewManager(recycler_view_phone);

        setRecleyGrideViewManager(recycler_view_lang);
        setRecleyGrideViewManager(recycler_view_hb);
        setRecleyGrideViewManager(recycler_view_sk);

        sp = getActivity().getSharedPreferences("uId", MODE_PRIVATE);
        userId = sp.getString("u", "");
        String un = sp.getString("un", "");

        if (userId.equals("") || userId.length() > 0) {
            userId = App.getInstance().getUserId();
        }



        ((ImageView) mView.findViewById(R.id.img_addEmail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditEmail.class);
                startActivity(intent);
            }
        });
        ((ImageView) mView.findViewById(R.id.img_btn_socLink)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SocialLink.class);
                intent.putExtra("field", (Serializable) getFieldList);
                startActivity(intent);
            }
        });

        ((ImageView) mView.findViewById(R.id.img_edt_address)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditAddress.class);
                startActivity(intent);
            }
        });

        ((ImageView) mView.findViewById(R.id.img_btn_phone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPhone.class);
                startActivity(intent);
            }
        });

        ((ImageView) mView.findViewById(R.id.imgbt_lang)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showAboutCustomDialog();
                ArrayList<String> LangList = new ArrayList<String>();
                Intent intent = new Intent(getActivity(), EditLanguage.class);
                intent.putExtra("list", (Serializable) profileLangList);
                startActivity(intent);
            }
        });
        ((ImageView) mView.findViewById(R.id.img_edit_hobbies)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditHobbies.class);
                intent.putExtra("Hlist", (Serializable) hobbyList);
                startActivity(intent);
            }
        });
        ((ImageView) mView.findViewById(R.id.img_edit_skills)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Skills.class);
                intent.putExtra("Slist", (Serializable) skillList);
                startActivity(intent);
            }
        });
        ((ImageView) mView.findViewById(R.id.img_btn_about)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showAboutCustomDialog();
                Intent intent = new Intent(getActivity(), AboutDetails.class);
                intent.putExtra("T",getTitle);
                intent.putExtra("D",getDec);
                intent.putExtra("L",getLink);
                startActivity(intent);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAboutData();
            }
        },300);

        return mView;
    }


    private void getAboutData() {
        pbHeaderProgress.setVisibility(View.VISIBLE);
            Log.e("TEST", "Get User Data");
            // list.clear();
            request = new CustomAuthRequest(Request.Method.POST, METHOD_GET_USERDATA_USER, null, 0,
                    new Response.Listener<JSONObject>() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onResponse(JSONObject response) {
                            if (App.getInstance().authorizeSimple(response)) {
                                String strResponse = response.toString();
                                Log.e("TEST", "Get User Data Response :" + response.toString());
                                if (strResponse != null) {
                                    pbHeaderProgress.setVisibility(View.GONE);

                                    try {
                                        JSONObject object = new JSONObject(strResponse);
                                        String msgType = object.getString("MessageType");
                                        if (msgType.equalsIgnoreCase("success")) ;

                                        JSONObject obj = object.getJSONObject("obj");
/*-------------------------------------------Email List-------------------------------------------------------------------*/
                                        JSONArray arrEs = obj.getJSONArray("Es");
                                        if(arrEs.length() == 0){
                                            //((TextView) mView.findViewById(R.id.txt_emailContent)).setVisibility(View.VISIBLE);
                                          //  ((TextView) mView.findViewById(R.id.txt_emailContent)).setText("No data found");
                                        }
                                        emailList.clear();
                                        for (int i = 0; i < arrEs.length(); i++) {
                                            JSONObject objEs = arrEs.getJSONObject(i);
                                            EmailModel em = new EmailModel();
                                            pe = objEs.getString("E");
                                            peType = objEs.getString("T");
                                            String id = objEs.getString("Id");
                                            em.emailAddress = pe;
                                            em.emailType = peType;
                                            em.recordId = id;
                                            emailList.add(em);
                                        }
                                        emailadapter = new EmailListAdapter(AboutFragment.this,getContext(), emailList);
                                        recycler_view_email.setAdapter(emailadapter);
/*-------------------------------------------Email List-------------------------------------------------------------------*/
                                        JSONArray arrfields = obj.getJSONArray("Fs");
                                        Log.e("TEST","Get Soc Lenght :"+arrfields.length());
                                        if(arrfields.length() == 0){
                                            ((TextView) mView.findViewById(R.id.txt_socContent)).setVisibility(View.VISIBLE);
                                            ((TextView) mView.findViewById(R.id.txt_socContent)).setText("No Social links to show");
                                        }
                                        for (int k = 0; k < arrfields.length(); k++) {
                                            JSONObject fieldObj = arrfields.getJSONObject(k);
                                            CommonModel cm = new CommonModel();
                                            cm.soc_N = fieldObj.getString("N");
                                            //cm.soc_V = fieldObj.getString("V");
                                            byte[] data1 = Base64.decode(fieldObj.getString("V"), Base64.DEFAULT);
                                            cm.soc_V = new String(data1, StandardCharsets.UTF_8);
                                            getFieldList.add(cm);

                                            if(cm.soc_N.equalsIgnoreCase("facebook")) {
                                                if(cm.soc_V.equalsIgnoreCase("")){
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_fb)).setVisibility(View.GONE);
                                                }else
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_fb)).setVisibility(View.VISIBLE);
                                            }
                                            if(cm.soc_N.equalsIgnoreCase("twitter")) {
                                                if(cm.soc_V.equalsIgnoreCase("")){
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_twitter)).setVisibility(View.GONE);
                                                }else
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_twitter)).setVisibility(View.VISIBLE);
                                            }
                                            if(cm.soc_N.equalsIgnoreCase("linkedin")) {
                                                if(cm.soc_V.equalsIgnoreCase("")){
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_linkdin)).setVisibility(View.GONE);
                                                }else
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_linkdin)).setVisibility(View.VISIBLE);
                                            }
                                            if(cm.soc_N.equalsIgnoreCase("instagram")) {
                                                if(cm.soc_V.equalsIgnoreCase("")){
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_instagram)).setVisibility(View.GONE);
                                                }else
                                                    ((FloatingActionButton) mView.findViewById(R.id.flt_instagram)).setVisibility(View.VISIBLE);
                                            }
                                        }
/*-------------------------------------------Complete Email List-------------------------------------------------------------------*/
/*-------------------------------------------Address List-------------------------------------------------------------------*/
                                        mAddressList.clear();
                                        JSONArray arrAdds = obj.getJSONArray("Adds");
                                        if(arrAdds.length() == 0){
                                            ((TextView) mView.findViewById(R.id.txt_addContent)).setVisibility(View.VISIBLE);
                                            ((TextView) mView.findViewById(R.id.txt_addContent)).setText("No Address to show");
                                        }
                                        for (int j = 0; j < arrAdds.length(); j++) {
                                            JSONObject addsObj = arrAdds.getJSONObject(j);
                                            String fullAddress = addsObj.getString("C") + addsObj.getString("S") + addsObj.getString("CR");
                                            CommonModel am = new CommonModel();

                                            am.cType = addsObj.getString("T");
                                            am.cAddress = addsObj.getString("L1");
                                            am.cN = addsObj.getString("C");
                                            am.cState = addsObj.getString("S");
                                            am.cCountry = addsObj.getString("CR");
                                            am.addsId = addsObj.getString("Id");
                                            am.fullAddress = fullAddress;

                                            mAddressList.add(am);
                                        }
                                        addsAdapter = new AddressAdapter(AboutFragment.this,getContext(), mAddressList);
                                        recycler_view_address.setAdapter(addsAdapter);

/*-------------------------------------------Complete Address List-------------------------------------------------------------------*/
/*-------------------------------------------Phone List-------------------------------------------------------------------*/
                                        phoneList.clear();
                                        JSONArray phoneAdds = obj.getJSONArray("PHs");
                                        phoneLenght = phoneAdds.length();
                                        if(phoneAdds.length() == 0){
                                            ((TextView) mView.findViewById(R.id.txt_phoneContent)).setVisibility(View.VISIBLE);
                                            ((TextView) mView.findViewById(R.id.txt_phoneContent)).setText("No Phone numbers to show");
                                        }
                                        for (int k = 0; k < phoneAdds.length(); k++) {
                                            JSONObject phoneObj = phoneAdds.getJSONObject(k);
                                            CommonModel am = new CommonModel();
                                            am.phNumber = phoneObj.getString("Ph");
                                            am.phcType = phoneObj.getString("T");
                                            am.phcCode = phoneObj.getString("CC");
                                            am.phId = phoneObj.getString("Id");
                                            phoneList.add(am);
                                        }
                                    phoneAdapter = new PhoneAdapter(AboutFragment.this,getContext(), phoneList);
                                    recycler_view_phone.setAdapter(phoneAdapter);
/*-------------------------------------------Complete phone List-------------------------------------------------------------------*/
/*-------------------------------------------Language List-------------------------------------------------------------------*/
                                        lanList.clear();
                                        JSONArray arr = obj.getJSONArray("Ls");
                                        profileLangList = new ArrayList<>();
                                        // LinearLayout lLayout = (LinearLayout) findViewById(R.id.rel_lang);
                                        // lLayout.removeAllViews();
                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject arrObj = arr.getJSONObject(i);
                                            CommonModel cm = new CommonModel();
                                            cm.lId = arrObj.getString("Id");
                                            cm.lName = arrObj.getString("N");
                                            profileLangList.add(cm);
                                            String getname = cm.lName;
                                        }
                                    LanuageSettingAdapter listAdapter = new LanuageSettingAdapter(AboutFragment.this,getContext(), profileLangList);
                                    recycler_view_lang.setAdapter(listAdapter);
                                        //rowTextView.setText(getname);

/*-------------------------------------------Complete Language List-------------------------------------------------------------------*/
/*------------------------------------------- Hobbies List-------------------------------------------------------------------*/

                                        JSONArray arrHobbies = obj.getJSONArray("Hs");
                                        if(arrHobbies.length() == 0){
                                            ((TextView) mView.findViewById(R.id.txt_hbContent)).setVisibility(View.VISIBLE);
                                            ((TextView) mView.findViewById(R.id.txt_hbContent)).setText("No Hobby to show");
                                        }
                                        for (int k = 0; k < arrHobbies.length(); k++) {

                                            JSONObject arrObj = arrHobbies.getJSONObject(k);
                                            CommonModel cm = new CommonModel();
                                            cm.lId = arrObj.getString("Id");
                                            cm.lName = arrObj.getString("N");
                                            hobbyList.add(cm);
                                        }

                                    HobbyAdapterSetting adpeter = new HobbyAdapterSetting(AboutFragment.this, getContext(), hobbyList);
                                    recycler_view_hb.setAdapter(adpeter);

/*-------------------------------------------Complete Hobbies List-------------------------------------------------------------------*/
/*-------------------------------------------Skills List-------------------------------------------------------------------*/

                                        arrSetting = obj.getJSONArray("Ss");
                                        JSONArray arrSkill = obj.getJSONArray("Sks");
                                        if(arrSkill.length() == 0){
                                            ((TextView) mView.findViewById(R.id.txt_skillContent)).setVisibility(View.VISIBLE);
                                            ((TextView) mView.findViewById(R.id.txt_skillContent)).setText("No Skill to show");
                                        }
                                        for (int k = 0; k < arrSkill.length(); k++) {
                                            JSONObject arrObj = arrSkill.getJSONObject(k);
                                            CommonModel cm = new CommonModel();
                                            cm.lId = arrObj.getString("Id");
                                            cm.lName = arrObj.getString("N");
                                            skillList.add(cm);
                                        }

                                    SkillAdapterSetting skadpeter = new SkillAdapterSetting(AboutFragment.this, getContext(), skillList);
                                    recycler_view_sk.setAdapter(skadpeter);

/*-------------------------------------------Complete Skills List-------------------------------------------------------------------*/
/*------------------------------------------- About List-------------------------------------------------------------------*/

                                        JSONArray arrAbout = obj.getJSONArray("Bls");
                                        JSONObject objLin = arrAbout.getJSONObject(0);
                                        String imgLink = objLin.getString("OV");

                                        JSONObject objDec = arrAbout.getJSONObject(1);
                                        getLink = objDec.getString("OV");
                                        txt_link.setText(getLink);
                                        //txt_link.setText(getDec);

                                        JSONObject objAbout = arrAbout.getJSONObject(2);
                                        getDec = objAbout.getString("OV");
                                        txt_dec.setText(getDec);
                                        //txt_dec.setText(getTitle);

                                        JSONObject objTitle = arrAbout.getJSONObject(3);
                                        getTitle = objTitle.getString("OV");
                                        txt_title.setText(getTitle);

/*-------------------------------------------Complete about List-------------------------------------------------------------------*/

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("TEST","Get User Exception :"+e);
                                    }
                                }
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pbHeaderProgress.setVisibility(View.VISIBLE);
                    //SnakebarCustom.danger(mContext, v, "Unable to fetch Companies: " + error.getMessage(), 5000);
                }
            }) {

                @Override
                protected JSONObject getParams() {
                    try {
                        JSONObject params = new JSONObject();
                        JSONArray jsonArray = new JSONArray();

                        //JSONObject jsonObject=new JSONObject();
                        //jsonObject.put("id",null);
                        //jsonObject.put("N",allLangList.get(i));
                        jsonArray.put("email");
                        jsonArray.put("field");
                        jsonArray.put("phone");
                        jsonArray.put("address");
                        jsonArray.put("date");
                        jsonArray.put("setting");
                        jsonArray.put("language");
                        jsonArray.put("hobby");
                        jsonArray.put("skill");
                        jsonArray.put("blob");

                        params.put("scopes", jsonArray);
                        params.put("UserId", userId);

                        Log.e("TEST","Get User Param :"+params);
                        return params;
                    } catch (JSONException ex) {
                        Log.e("TEST","Get User Exception :"+ex);
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



    public void deleteEmail(final String emailId){
        Log.e("TEST","Get Delete Id From Email :"+emailId);
        // list.clear();
        request = new CustomAuthRequest(Request.Method.POST, METHOD_GET_DELETE_Email_USER, null, 0,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (App.getInstance().authorizeSimple(response)) {
                            String strResponse = response.toString();
                            Log.e("TEST", "Delete Response :" + response.toString());
                            if (strResponse != null) {
                                lanList.clear();
                                try {
                                    JSONObject object = new JSONObject(strResponse);
                                    String msgType = object.getString("MessageType");
                                    if (msgType.equalsIgnoreCase("success")) {
                                        toastIconSuccess("addsdlete");
                                        getAboutData();
                                    }
//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
//
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
                    params.put("Id", emailId);
                    params.put("UserId", userId);
                    Log.e("TEST","Delete Address Param :"+params);
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

    private void toastIconSuccess(String msgText) {
        Toast toast = new Toast(getActivity());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_success_text, null);
        if (msgText.equalsIgnoreCase("Language")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("Language(s) Saved Successfully!");
        } else if (msgText.equalsIgnoreCase("email")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("Email(s) Saved Successfully!");
        } else if (msgText.equalsIgnoreCase("address")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("Address(s) Saved Successfully!");
        } else if (msgText.equalsIgnoreCase("phone")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("Phone(s) Saved Successfully!");
        }else if (msgText.equalsIgnoreCase("about")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("About(s) Saved Successfully!");
        }else if (msgText.equalsIgnoreCase("addsdlete")) {
            ((TextView) custom_view.findViewById(R.id.message)).setText("Delete Successfully!");
        }

        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();
    }

    public void deleteAddress(final String addsId){
        Log.e("TEST","Get Delete Id From Address :"+addsId);
        // list.clear();
        request = new CustomAuthRequest(Request.Method.POST, METHOD_GET_DELETE_ADDRESS_USER, null, 0,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (App.getInstance().authorizeSimple(response)) {
                            String strResponse = response.toString();
                            Log.e("TEST", "Delete Response :" + response.toString());
                            if (strResponse != null) {
                                lanList.clear();
                                try {
                                    JSONObject object = new JSONObject(strResponse);
                                    String msgType = object.getString("MessageType");
                                    if (msgType.equalsIgnoreCase("success")) {
                                        toastIconSuccess("addsdlete");
                                        getAboutData();
                                    }
//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {
//
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
                    params.put("Id", addsId);
                    params.put("UserId", userId);
                    Log.e("TEST","Delete Address Param :"+params);
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

    public void deletePhone(final String addsId){
        Log.e("TEST","Get Phone Id From :"+addsId);
        // list.clear();
        request = new CustomAuthRequest(Request.Method.POST, METHOD_GET_DELETE_PHONE_USER, null, 0,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (App.getInstance().authorizeSimple(response)) {
                            String strResponse = response.toString();
                            Log.e("TEST", "Delete Phone Response :" + response.toString());
                            if (strResponse != null) {
                                lanList.clear();
                                try {
                                    JSONObject object = new JSONObject(strResponse);
                                    String msgType = object.getString("MessageType");
                                    if (msgType.equalsIgnoreCase("success")) {
                                        toastIconSuccess("addsdlete");
                                        getAboutData();
                                    }
//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
//
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
                    params.put("Id", addsId);
                    params.put("UserId", userId);
                    Log.e("TEST","Delete Phone Param :"+params);
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

    private void settingDilougeBox() {
        //arrSetting
        Log.e("TEST","Setting Request :"+arrSetting.length());

        final Dialog settindialog = new Dialog(getActivity());
        settindialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        settindialog.setContentView(R.layout.custom_setting_alert_dailogbox);
        settindialog.setCancelable(true);

        String getShowFrnd = ((TextView) settindialog.findViewById(R.id.txt_user_ShowFriends1)).getText().toString();
        String getshowFollowers = ((TextView) settindialog.findViewById(R.id.txt_user_ShowFollowers1)).getText().toString();
        String getAllowRequest = ((TextView) settindialog.findViewById(R.id.txt_user_AllowFriendRequests1)).getText().toString();
        String getNewMessage = ((TextView) settindialog.findViewById(R.id.txt_user_Notify_NewMessage1)).getText().toString();
        String getNewConnection = ((TextView) settindialog.findViewById(R.id.txt_user_Notify_NewConnection1)).getText().toString();
        String getEmailOff = ((TextView) settindialog.findViewById(R.id.txt_user_EmailOff1)).getText().toString();
        Log.e("TEST","Get Value :"+getShowFrnd);

        for (int i = 0; i < arrSetting.length(); i++) {
            try {
                JSONObject objSetting = arrSetting.getJSONObject(i);
                String settingN = objSetting.getString("N");

                if(settingN.equalsIgnoreCase(getShowFrnd)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_ShowFriends)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_ShowFriends)).setChecked(false);
                    }
                }
                if(settingN.equalsIgnoreCase(getshowFollowers)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_ShowFollowers)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_ShowFollowers)).setChecked(false);
                    }
                }
                if(settingN.equalsIgnoreCase(getAllowRequest)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_AllowFriendRequests)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_AllowFriendRequests)).setChecked(false);
                    }
                }
                if(settingN.equalsIgnoreCase(getNewMessage)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_Notify_NewMessage)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_Notify_NewMessage)).setChecked(false);
                    }
                }
                if(settingN.equalsIgnoreCase(getNewConnection)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_Notify_NewConnection)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_Notify_NewConnection)).setChecked(false);
                    }
                }
                if(settingN.equalsIgnoreCase(getEmailOff)){
                    if(objSetting.getString("IA").equalsIgnoreCase("true")){
                        ((Switch) settindialog.findViewById(R.id.switch_user_EmailOff)).setChecked(true);
                    }else {
                        ((Switch) settindialog.findViewById(R.id.switch_user_EmailOff)).setChecked(false);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        //modedialog.setView(lp);
        settindialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        settindialog.show();
        settindialog.getWindow().setAttributes(lp);
        dialog.dismiss();
    }



    private void setRecleyViewManager(RecyclerView recycler_view){
        RecyclerView.LayoutManager addsrecy = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(addsrecy);
    }

    private void setRecleyGrideViewManager(RecyclerView recycler_view) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recycler_view.setLayoutManager(gridLayoutManager);
    }

}
