package com.fammeo.app.adapter.fammeoAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fammeo.app.R;
import com.fammeo.app.activity.EditActivity.EditAddress;
import com.fammeo.app.activity.EditActivity.EducationDetails;
import com.fammeo.app.activity.WorkEducation;
import com.fammeo.app.fragment.FammeoFragment.EducationFragment;
import com.fammeo.app.model.CommonModel;

import java.util.List;

public class EducationCityListAdapter extends RecyclerView.Adapter<EducationCityListAdapter.ViewHolder> {
    EducationDetails context;
    List<CommonModel> mCityList;
    String flage;
    public EducationCityListAdapter(EducationDetails workEducation, List<CommonModel> mCityList, String getFlage) {
        this.context = workEducation;
        this.mCityList = mCityList;
        this.flage = getFlage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row_list,parent,false);
        return new EducationCityListAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationCityListAdapter.ViewHolder holder, final int i) {
        holder.txt_city.setText(mCityList.get(i).cN+", "+ mCityList.get(i).cState+", "+ mCityList.get(i).cCountry);

        holder.fra_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mCityList.get(i).cN+", "+ mCityList.get(i).cState+", "+ mCityList.get(i).cCountry;
                Log.e("TEST","City Name :"+city);
                // dataInterface.CityData(mCityList.get(i).cN,mCityList.get(i).cState,mCityList.get(i).cCountry);
                context.cityName(mCityList.get(i).cN,mCityList.get(i).cState,mCityList.get(i).cCountry,city,flage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_city;
        FrameLayout fra_city;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_city = itemView.findViewById(R.id.txt_city);
            fra_city = itemView.findViewById(R.id.fra_city);
        }
    }
}
