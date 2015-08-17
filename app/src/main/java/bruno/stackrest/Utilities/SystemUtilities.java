package bruno.stackrest.Utilities;

import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-16.
 */
public class SystemUtilities {

    Context context;

    @Inject
    public SystemUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context ;
    }

    public void killProcess(String process) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        Iterator<ActivityManager.RunningAppProcessInfo> iter = runningAppProcesses.iterator();

        while(iter.hasNext()){
            ActivityManager.RunningAppProcessInfo next = iter.next();

            if(next.processName.equals(process)){
                android.os.Process.killProcess(next.pid);
                break;
            }
        }
    }
}
