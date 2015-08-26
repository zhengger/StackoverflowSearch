package bruno.stackrest.Services;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import bruno.stackrest.POJOs.ResponseStackREST;
import bruno.stackrest.POJOs.Topic;
import bruno.stackrest.R;
import bruno.stackrest.Utilities.C;
import bruno.stackrest.Utilities.DataUtilities;
import bruno.stackrest.Utilities.DatabaseUtilities;
import bruno.stackrest.Utilities.NetworkUtilities;
import bruno.stackrest.Utilities.SystemUtilities;
import roboguice.service.RoboIntentService;

/**
 * Created by Bruno on 2015-08-14.
 */
public class StackoverflowInTitleSearch extends RoboIntentService {

    @Inject private DataUtilities dataUtilities;

    @Inject private NetworkUtilities networkUtilities;

    @Inject private DatabaseUtilities databaseUtilities;

    @Inject private SystemUtilities systemUtilities;

    private ResponseStackREST response;

    private String searchTerm;

    public static Intent makeIntent(Context context, String searchTerm) {
        Intent intent = new Intent(context, StackoverflowInTitleSearch.class);
        intent.putExtra(C.General.SEARCH_IN_TITLE_PARAMETER, searchTerm);

        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        searchTerm = intent.getStringExtra(C.General.SEARCH_IN_TITLE_PARAMETER);


        try {
            response = networkUtilities.getInTitleSearchResultsFromStackoverflow(searchTerm);
            unsuccessfulSearchExitStrategy();
        } catch (Exception exception) {
            broadcastResults(searchTerm, C.SearchService.SearchServiceResult.DOWNLOAD_FAILED);
            killCurrentProcess();
        }

        List<Topic> ListTopic = dataUtilities.convertServerResponseToTopicList(response, searchTerm);

        try{
            databaseUtilities.deleteSearchResultsByInTitle(searchTerm);
            databaseUtilities.saveSearchResults(ListTopic);
        } catch (Exception exception) {
            broadcastResults(searchTerm, C.SearchService.SearchServiceResult.DATABASE_ERROR);
            killCurrentProcess();
        }
        broadcastResults(searchTerm, C.SearchService.SearchServiceResult.DOWNLOAD_SUCCESSFUL);
    }

    private void unsuccessfulSearchExitStrategy() {
        if (response.getItems().length == 0) {
            broadcastResults(searchTerm, C.SearchService.SearchServiceResult.ZERO_SEARCH_RESULTS);
            killCurrentProcess();
        }
    }

    private void killCurrentProcess() {
        systemUtilities.killProcess(getPackageName() + getString(R.string.services_process));
    }


    private void broadcastResults(String searchTerm, Enum result) {
        Intent intent = new Intent();
        intent.setAction(C.General.STACKOVERFLOW_SEARCH);
        intent.setPackage(getBaseContext().getPackageName());
        intent.putExtra(C.General.SEARCH_IN_TITLE_PARAMETER, searchTerm);
        intent.putExtra(C.SearchService.SEARCH_SERVICE_RESULT, result);

        sendBroadcast(intent);
    }

    public StackoverflowInTitleSearch() { super(StackoverflowInTitleSearch.class.getName());  }

}
