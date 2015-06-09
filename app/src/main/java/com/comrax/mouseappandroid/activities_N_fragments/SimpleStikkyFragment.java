package com.comrax.mouseappandroid.activities_N_fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.comrax.mouseappandroid.App;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CustomAdapter;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.model.ListModel;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class SimpleStikkyFragment extends Fragment {

    private ListView mListView;
    private Button btn;

    CustomAdapter adapter;
    public ArrayList<ListModel> customListViewValuesArr = new ArrayList<>();



    public SimpleStikkyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simplelistview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) getActivity().findViewById(R.id.listview);
        btn = (Button) view.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "great", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeader(100)
                .build();


        populateListView();

        adapter = new CustomAdapter(getActivity(), customListViewValuesArr);
        mListView.setAdapter(adapter);


    }

    private void populateListView() {

        String cityId = App.getInstance().getCityId();
        Cursor cursor = new DBTools(getActivity()).getCurrentCityPlacesTable(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, cityId);

            for (int i = 0; i < cursor.getCount(); cursor.moveToNext(), i++) {
                ListModel lm = new ListModel();

                lm.setTitleA(cursor.getString(cursor.getColumnIndex(DBConstants.name)));
                lm.setTitleB(cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
                lm.setTitleC(cursor.getString(cursor.getColumnIndex(DBConstants.type)));

//                lm.setDistance(cursor.getString(cursor.getColumnIndex(DBConstants.type)));
//                lm.setTitleC(cursor.getString(cursor.getColumnIndex(DBConstants.type)));
//                lm.setTitleC(cursor.getString(cursor.getColumnIndex(DBConstants.type)));


                customListViewValuesArr.add(lm);

            }


    }

}
