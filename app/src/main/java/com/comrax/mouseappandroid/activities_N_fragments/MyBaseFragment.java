package com.comrax.mouseappandroid.activities_N_fragments;

import android.support.v4.app.Fragment;

/**
 * Created by bez on 02/07/2015.
 */
public class MyBaseFragment extends Fragment {

    protected MyFragmentDelegate delegate;

    public void setDelegate(MyFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public interface MyFragmentDelegate {
        void onResumeAction();
    }



    @Override
    public void onResume() {
        super.onResume();
        if(this.delegate != null) {
            this.delegate.onResumeAction();
        }
    }
}
