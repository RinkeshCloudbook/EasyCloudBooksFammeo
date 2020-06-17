package com.fammeo.app.adapter.fammeoAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fammeo.app.R;
import com.fammeo.app.activity.WorkEducation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperienceFragment extends Fragment {

    View mView;
    public ExperienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_experience, container, false);

        ((ImageView) mView.findViewById(R.id.img_edt_exp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WorkEducation.class);
                intent.putExtra("E","Exp");
                startActivity(intent);
            }
        });


        return mView;
    }
}
