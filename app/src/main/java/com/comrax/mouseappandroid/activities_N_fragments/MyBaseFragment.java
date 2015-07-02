package com.comrax.mouseappandroid.activities_N_fragments;

import android.support.v4.app.Fragment;

/**
 * Created by bez on 02/07/2015.
 */
public class MyBaseFragment extends Fragment {

    protected SimpleFragmentDelegate delegate;

    public void setDelegate(SimpleFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public interface SimpleFragmentDelegate {
        void onResumeAction();
    }



    @Override
    public void onResume() {
        super.onResume();
        this.delegate.onResumeAction();
    }
}
