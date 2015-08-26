package bruno.stackrest.AbstractClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import bruno.stackrest.Utilities.C;
import roboguice.fragment.RoboFragment;

/**
 * Created by Bruno on 2015-08-17.
 */
public abstract class BaseFragment extends RoboFragment {

    BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume(){
        super.onResume();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                broadcastReceiverOnReceive(context, intent);
            }
        };

        getActivity().registerReceiver(broadcastReceiver, makeIntentFilter(C.General.STACKOVERFLOW_SEARCH) );
    }

    protected IntentFilter makeIntentFilter (String filterAction) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(filterAction);

        return filter;
    }

    @Override
    public void onPause(){
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    public abstract void broadcastReceiverOnReceive(Context context, Intent intent) ;
}
