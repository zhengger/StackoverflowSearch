package bruno.stackrest.Utilities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-17.
 */
public class ViewUtilities {

    Context context;

    @Inject
    public ViewUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context ;
    }

    public void hideKeyboardFromInputView(Context context, View inputView) {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(inputView.getWindowToken(), 0);
    }
}
