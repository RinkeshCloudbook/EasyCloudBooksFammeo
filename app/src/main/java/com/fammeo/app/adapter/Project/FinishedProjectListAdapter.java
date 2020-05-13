package com.fammeo.app.adapter.Project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fammeo.app.R;
import com.fammeo.app.activity.MainActivity;
import com.fammeo.app.animation.ViewAnimation;
import com.fammeo.app.app.Config;
import com.fammeo.app.common.DataDateTime;
import com.fammeo.app.common.DataText;
import com.fammeo.app.common.SnakebarCustom;
import com.fammeo.app.common.SweetAlertCustom;
import com.fammeo.app.font.FButton;
import com.fammeo.app.fragment.FinishedProjectFragment;
import com.fammeo.app.model.ProjectJ;
import com.fammeo.app.model.ProjectNoteJ;
import com.fammeo.app.model.RoleJ;
import com.fammeo.app.util.Helper;
import com.fammeo.app.util.Tools;
import com.fammeo.app.util.UIUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FinishedProjectListAdapter extends RecyclerView.Adapter<FinishedProjectListAdapter.ViewHolder> {
    public static String TAG = FinishedProjectListAdapter.class.getSimpleName();
    private List<ProjectJ> mDataSet = new ArrayList<>();
    private Context mContext;
    private FinishedProjectListAdapter.MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;
    private static int currentSelectedIndex = -1;
    private FinishedProjectFragment _thisFragment;
    private MainActivity _thisActivity;
    private RoleJ roleJ;
    public void SetRole(RoleJ roleJ)
    {
        this.roleJ = roleJ;
    }

    public FinishedProjectListAdapter(Context mContext, List<ProjectJ> messages,
                                      FinishedProjectListAdapter.MessageAdapterListener listener,
                                      FinishedProjectFragment _thisFragment, AppCompatActivity activity, RoleJ roleJ) {
        this.mDataSet = messages;
        this._thisFragment = _thisFragment;
        this.roleJ = roleJ;
        this._thisActivity = (MainActivity) _thisFragment.getActivity();
        this.mContext = mContext;
        initpDialog();
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
        setHasStableIds(true);
    }
    private SweetAlertCustom swc;

    protected void initpDialog() {
        if (swc == null)
            swc = new SweetAlertCustom(mContext);
        swc.CreatingLoadingDialog("Loading");
    }

    protected void showpDialog() {
        loading = true;
        swc.ShowLoading();
    }

    protected void hidepDialog() {
        loading = false;
        swc.HideLoading();
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return this.getItem(position).ProjectId;
    }



    public interface OnItemClickListener {
        void onClick(ProjectJ mMessage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtCompanyName, txtServiceName, pmr_name,project_note,project_status,icon_text,project_uid;
        public ImageView  imgProfile,imgCardImage,pmr_image,card_bar;
        public LinearLayout messageContainer,lyt_expand_text;
        public RelativeLayout projectdetail_container_note;//, projectdetail_container_uid;
        public FrameLayout mContainer;
        public View projectdetail_container_note_devider,projectdetail_container_uid_devider;
        public ImageButton bt_toggle_text;
        public  FButton call_button;
        public ViewHolder(View view) {
            super(view);
            mContainer = (FrameLayout) view.findViewById(R.id.container);
            txtCompanyName = (TextView) view.findViewById(R.id.card_title);
            txtServiceName = (TextView) view.findViewById(R.id.card_sub_title);
            imgCardImage = (ImageView) view.findViewById(R.id.card_image);
            icon_text = (TextView) view.findViewById(R.id.icon_text);
            pmr_image = (ImageView) view.findViewById(R.id.pmr_image);
            card_bar = (ImageView) view.findViewById(R.id.card_bar);
            call_button= (FButton) view.findViewById(R.id.call_button);
            bt_toggle_text = (ImageButton) view.findViewById(R.id.bt_toggle_text);
            lyt_expand_text = (LinearLayout) view.findViewById(R.id.lyt_expand);

            pmr_name = (TextView) view.findViewById(R.id.pmr_name);
            project_status = (TextView) view.findViewById(R.id.project_status);
            project_note= (TextView) view.findViewById(R.id.project_note);
            projectdetail_container_note= (RelativeLayout) view.findViewById(R.id.projectdetail_container_note);
            projectdetail_container_note_devider= (View) view.findViewById(R.id.projectdetail_container_note_devider);
            //projectdetail_container_uid= (RelativeLayout) view.findViewById(R.id.projectdetail_container_uid);
            projectdetail_container_uid_devider= (View) view.findViewById(R.id.projectdetail_container_uid_devider);
            project_uid= (TextView) view.findViewById(R.id.project_uid);


        }

    }

    public void Add(ProjectJ mNewData, boolean NotifyChange) {
        mDataSet.add(mNewData);
        if (NotifyChange)
            notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_row_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ProjectJ message = mDataSet.get(position);
        // displaying text view data
        //Log.w(TAG, "onBindViewHolder: "+message.ProjectId );
        holder.txtCompanyName.setText(message.CR.Name);
        holder.txtServiceName.setText(message.Service.ServiceName + "  ("+message.Year+")");
        if (message.CR.I != null && !TextUtils.isEmpty(message.CR.I)) {
            Glide.with(mContext).load(DataText.GetImagePath(message.CR.I))
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
                    .apply( new RequestOptions().transform(new RoundedCorners(20)).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.imgCardImage);
            holder.imgCardImage.setColorFilter(null);
            holder.icon_text.setVisibility(View.GONE);
        } else {
            holder.imgCardImage.setImageResource(R.drawable.bg_square);
            holder.imgCardImage.setColorFilter(null);
            holder.icon_text.setText(Helper.getShortName(message.CR.Name));
            holder.icon_text.setVisibility(View.VISIBLE);
        }
        String ProjectStatus = "Lead";
        switch (message.Status)
        {
            case "Lead" : ProjectStatus = message.Status; holder.card_bar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.ecb_project_lead, null));break;
            case "In Progress" :  ProjectStatus = message.Status;holder.card_bar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.ecb_project_inprogress, null));break;
            case "On Hold" :ProjectStatus = message.Status; holder.card_bar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.ecb_project_onhold, null));break;
            case "Not Started" :ProjectStatus = message.Status;  holder.card_bar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.ecb_project_notstarted, null));break;
            case "Completed" :
            case "Review" :
            case "Finished" : ProjectStatus = "Completed"; holder.card_bar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.ecb_project_completed, null));break;
        }

        holder.project_status.setText(ProjectStatus);
        holder.pmr_name.setText( message.PMR.user.FN + " " + message.PMR.user.LN);
        if (message.PMR.user.I != null && !TextUtils.isEmpty(message.PMR.user.I)) {
            Glide.with(mContext).load(DataText.GetImagePath(message.PMR.user.I))
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
                    .apply( new RequestOptions().circleCropTransform()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.pmr_image);
            holder.pmr_image.setColorFilter(null);
        } else {
            holder.pmr_image.setImageResource(R.drawable.bg_circle);
            holder.pmr_image.setColorFilter(null);
        }
        ProjectNoteJ Note = null;
        if( message.Notes != null && message.Notes.size() > 0)
        {
            Note = message.Notes.get(0);
        }
        if (Note != null && !TextUtils.isEmpty(Note.N)) {
            DateTime CD = DataDateTime.getLocalJodaTime(Note.CD);
            String agoString = (String)android.text.format.DateUtils.getRelativeTimeSpanString((CD).getMillis());
            String temp = "<b>"+Note.N + "</b> Stated " +agoString;


            DateTime ND = DataDateTime.getLocalJodaTime(Note.ND);
            if(ND != null) {
                temp = temp + ", Next update on <b>" + ND.toString(DateTimeFormat.mediumDate())+"</b>";
            }
          //  temp = temp + " <small>(Note)</small>";
            holder.project_note.setText(Html.fromHtml(temp));
            //holder.project_note.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            holder.projectdetail_container_note.setVisibility(View.GONE);
            holder.projectdetail_container_note_devider.setVisibility(View.GONE);
        }

        holder.project_uid.setText(message.UId);
        holder.projectdetail_container_uid_devider.setVisibility(View.VISIBLE);
        //holder.projectdetail_container_uid.setVisibility(View.VISIBLE);
        holder.bt_toggle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG, "onClick: ");
                message.setExpanded(toggleLayoutExpand(!message.isExpanded,view,holder.lyt_expand_text));
            }
        });
        if(message.isExpanded && !isExpanded(holder.bt_toggle_text))
        {
            toggleLayoutExpand(true,holder.bt_toggle_text,holder.lyt_expand_text);
        }
        else if(!message.isExpanded && isExpanded(holder.bt_toggle_text))
        {
            toggleLayoutExpand(false,holder.bt_toggle_text,holder.lyt_expand_text);
        }


        if (message.PMR.user.PPh != null && !TextUtils.isEmpty(message.PMR.user.PPh)) {
            holder.call_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CallPhoneTime = DataDateTime.Now();
                    CallPhoneMessage = message;
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(_thisFragment.getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                Config.MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        CallPhoneNumber();
                    }
                }
            });
            holder.call_button.setVisibility(View.VISIBLE);
        }
        else{
            holder.call_button.setVisibility(View.GONE);
        }



    }


    public boolean isExpanded(View view) {
        if (view.getRotation() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }



    boolean loading = false;

    public void DeleteContact(final int position, long ProjectId) {
        /*  showpDialog();
      new CustomAuthRequest(Request.Method.POST, METHOD_CONTACT_CONTACT_DELETE, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                               // mEmailDataAdapter.removeItem(position);
                                removeData(position);
                                loading = false;
                                hidepDialog();
                                String Message = response.getString("Message");
                                if (Message == null)
                                    Message = "Contact Removed Successfully.";
                                swc.SuccessMessage("");
                            } else {
                                loading = false;
                                hidepDialog();
                                String Message = response.getString("Message");
                                if (Message == null)
                                    Message = "Error Removing Contact.";
                                swc.ErrorMessage(Message);
                            }
                        } catch (JSONException e) {
                            loading = false;
                            hidepDialog();
                            swc.ErrorMessage("Something went wrong!");
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;

                hidepDialog();
                swc.ErrorMessage("Something went wrong!");
            }
        })
        {
            @Override
            protected JSONObject getParams() {
                try {
                    JSONObject params = new JSONObject();
                    params.put("ContactUserId", ContactUserId);
                    return params;
                } catch (JSONException ex) {
                    DataGlobal.SaveLog(TAG, ex);
                    return null;
                }
            }
        };*/
    }


    public DateTime CallPhoneTime;
    public ProjectJ CallPhoneMessage;

    public void DialNumber(String PhoneNumber) {
        DateTime NowTime = DataDateTime.Now().minusMinutes(10);
        if (NowTime.isBefore(CallPhoneTime) && CallPhoneMessage != null) {
            ProjectJ message = CallPhoneMessage;
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                SnakebarCustom.success(mContext, _thisFragment.getView(), "Calling" + message.Title + "(" + PhoneNumber + ")", 1000);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + PhoneNumber));
                mContext.startActivity(intent);
                CallPhoneMessage = null;
            }
        }
    }

    public void CallPhoneNumber() {
        DateTime NowTime = DataDateTime.Now().minusMinutes(10);
        if (NowTime.isBefore(CallPhoneTime) && CallPhoneMessage != null) {
            ProjectJ message = CallPhoneMessage;

            String Phone = message.PMR  .user.PPh;String PhoneCode=message.PMR  .user.PPhC;
            if (Phone != null && !TextUtils.isEmpty(Phone)) {
                DialNumber(Phone);
            } else {
                SnakebarCustom.danger(mContext, _thisFragment.getView(), "No Valid Phone Number found to Call", 1000);
            }
        }
    }


    private void applyClickEvents(final ViewHolder holder, final int position) {

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position,holder);
            }
        });

    }

    private void applyProfilePicture(ViewHolder holder, ProjectJ message) {
        holder.imgProfile.setImageResource(R.drawable.bg_circle);
        holder.imgProfile.setColorFilter(message.getColor());
    }


    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    public ProjectJ getItem(int position) {
        return mDataSet.get(position);
    }

    public void set(int position, ProjectJ nMessage) {
        mDataSet.set(position, nMessage);
    }




    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        mDataSet.remove(position);
        listener.onContactDeleted(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {

        void onMessageRowClicked(int position, ViewHolder holder);

        void onContactDeleted(int position);
    }

    public void ClearData() {
        mDataSet.clear();
    }

    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;
    private boolean isFirstOnly = true;

    public void setFirstOnly(boolean firstOnly) {
        isFirstOnly = firstOnly;
    }

    private void animateItem(View view) {
        view.setTranslationY(UIUtils.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();

            /*ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            view.startAnimation(anim);*/
    }

}
