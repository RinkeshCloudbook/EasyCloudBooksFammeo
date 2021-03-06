package com.fammeo.app.adapter.fammeoAdapter;

import android.content.Intent;
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
import com.fammeo.app.activity.EditActivity.citySearchWindow;
import com.fammeo.app.common.CommomInterface;
import com.fammeo.app.common.PassDataInterface;
import com.fammeo.app.model.CommonModel;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    citySearchWindow contex;
    List<CommonModel> mCityList;
    CommomInterface dataInterface;
    String city,address,type,id;
    public CityListAdapter(citySearchWindow editAddress, List<CommonModel> mCityList,String address,String type,String id) {
        this.contex = editAddress;
        this.mCityList = mCityList;
        this.address = address;
        this.type = type;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row_list,parent,false);
        return new CityListAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {


        holder.txt_city.setText(mCityList.get(i).cN+", "+ mCityList.get(i).cState+", "+ mCityList.get(i).cCountry);

        holder.fra_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = mCityList.get(i).cN+", "+ mCityList.get(i).cState+", "+ mCityList.get(i).cCountry;
                Log.e("TEST","City Name :"+city);
                // dataInterface.CityData(mCityList.get(i).cN,mCityList.get(i).cState,mCityList.get(i).cCountry);
                Intent intent = new Intent(contex,EditAddress.class);
                intent.putExtra("C",mCityList.get(i).cN);
                intent.putExtra("S",mCityList.get(i).cState);
                intent.putExtra("Con",mCityList.get(i).cCountry);
                intent.putExtra("T",address);
                intent.putExtra("A",type);
                intent.putExtra("ID",id);
                contex.startActivity(intent);
                 //contex.cityName(mCityList.get(i).cN,mCityList.get(i).cState,mCityList.get(i).cCountry,city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_city;
        FrameLayout fra_city;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_city = itemView.findViewById(R.id.txt_city);
            fra_city = itemView.findViewById(R.id.fra_city);
        }
    }
}
