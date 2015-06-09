package com.comrax.mouseappandroid.activities_N_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CustomAdapter;
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

        mListView = (ListView) view.findViewById(R.id.listview);
        btn = (Button)view.findViewById(R.id.button);

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

    }

    private void populateListView() {

//        String[] elements = new String[500];
//        for (int i = 0; i < elements.length; i++) {
//            elements[i] = "row " + i;
//        }
//
//        mListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, elements));

        adapter = new CustomAdapter(getActivity(), customListViewValuesArr, getResources());
        mListView.setAdapter(adapter);

    }

}
