package com.comrax.mouseappandroid.activities_N_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;

public class PlaceFragment extends Fragment {



    public PlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String data = App.getInstance().get_cityFolderName() + "/"+ App.getInstance().get_cityId() + "_"+ App.getInstance().get_boneId()+ "_ArticalsList.json";
//        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(data);

        Bundle bundle = this.getArguments();
        String fullDescription = bundle.getString("fullDescription", null);

        Toast.makeText(getActivity().getApplicationContext(), fullDescription, Toast.LENGTH_LONG).show();

    }







    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateDetails();

    }




    private void populateDetails() {

    }

}
