package bruno.stackrest.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;

import bruno.stackrest.R;
import butterknife.ButterKnife;
import roboguice.activity.RoboFragmentActivity;

/**
 * Created by Bruno on 2015-08-14.
 */
public abstract class BaseActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        SetupActionBar();
        manageHeadlessFragmentBroadcastReceiverLifeCycle();
    }

    public void displaySnackbarMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void SetupActionBar() {
    }

    protected abstract void manageHeadlessFragmentBroadcastReceiverLifeCycle()  ;
}
