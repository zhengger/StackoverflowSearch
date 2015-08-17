package bruno.stackrest.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import bruno.stackrest.POJOs.ResponseStackREST;
import bruno.stackrest.Retrofit.StackoverflowRetrofitInterface;
import retrofit.RestAdapter;
import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-14.
 */
public class NetworkUtilities {

    private Context context;

    @Inject
    public NetworkUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static StackoverflowRetrofitInterface getStackoverflowRetrofitInterface() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(C.StackoverflowServer.ENDPOINT)
                .build();

        StackoverflowRetrofitInterface stackoverflowRetrofitInterface = restAdapter.create(StackoverflowRetrofitInterface.class);
        return stackoverflowRetrofitInterface;
    }

    public ResponseStackREST getInTitleSearchResultsFromStackoverflow(String searchTerm) {
        Map queryMap = new HashMap<String,String>();
        queryMap.put(C.StackoverflowServer.QUERY_IN_TITLE, searchTerm);

        StackoverflowRetrofitInterface stackoverflowRetrofitInterface = NetworkUtilities.getStackoverflowRetrofitInterface();

        ResponseStackREST responseStackREST = stackoverflowRetrofitInterface.getFeed(queryMap);
        return responseStackREST;
    }
}
