package com.fammeo.app.fragment.FammeoFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fammeo.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganisationFragment extends Fragment {
    View mView;
    public OrganisationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_organisation, container, false);
        return mView;
    }
}
