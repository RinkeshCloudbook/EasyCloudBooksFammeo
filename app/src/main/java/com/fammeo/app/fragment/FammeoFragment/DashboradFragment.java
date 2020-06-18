package com.fammeo.app.fragment.FammeoFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fammeo.app.R;
import com.fammeo.app.activity.WorkEducation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboradFragment extends Fragment {

    View mView;
    public DashboradFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashborad, container, false);

        ((FloatingActionButton) mView.findViewById(R.id.fab_createOrg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WorkEducation.class);
                Log.e("TEST","Org");
                intent.putExtra("E","org");
                startActivity(intent);
            }
        });

        return mView;
    }
}
