package com.fammeo.app.fragment.FammeoFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fammeo.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {
    View mView;
    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_following, container, false);

        ((LinearLayout) mView.findViewById(R.id.lyt_following)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "View Following Profile", Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }
}
