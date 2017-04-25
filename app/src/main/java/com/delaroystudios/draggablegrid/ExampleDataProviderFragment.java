package com.delaroystudios.draggablegrid;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by delaroy on 4/23/17.
 */
public class ExampleDataProviderFragment extends Fragment {

    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        mDataProvider = new ExampleDataProvider();
    }

    public AbstractDataProvider getDataProvider(){ return mDataProvider; }
}
