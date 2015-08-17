package bruno.stackrest.Utilities;

import android.content.Context;
import android.text.TextUtils;

import javax.inject.Inject;

import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-14.
 */
public class InputUtilities {

    @Inject
    public InputUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
    }

    public boolean isInputStringValid(String inputString) {
        if (TextUtils.isEmpty(inputString) ) {
            return false;
        }
        return true;
    }
}
