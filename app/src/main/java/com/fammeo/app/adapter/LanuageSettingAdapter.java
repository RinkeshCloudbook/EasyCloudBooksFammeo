package com.fammeo.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fammeo.app.R;
import com.fammeo.app.activity.SettingEdit;
import com.fammeo.app.model.CommonModel;

import java.util.List;

public class LanuageSettingAdapter extends RecyclerView.Adapter<LanuageSettingAdapter.ViewHolder> {
    SettingEdit context;
    List<CommonModel> profileLangList;
    public LanuageSettingAdapter(SettingEdit settingEdit, List<CommonModel> profileLangList) {
        this.context = settingEdit;
        this.profileLangList = profileLangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.laguage_fron_item_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_langname.setText(profileLangList.get(position).lName);
    }

    @Override
    public int getItemCount() {
        return profileLangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_langname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_langname = itemView.findViewById(R.id.txt_langname);
        }
    }
}