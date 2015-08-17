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

        String currentProcess = getPackageName() + getString(R.string.services_process); //TODO: get the service name in a dynamic fashion

        try {
            response = networkUtilities.getInTitleSearchResultsFromStackoverflow(searchTerm);
            stopSelfAndBroadcastOutcomeIfSearchYieldedZeroResult(currentProcess);
        } catch (Exception exception) {
            sendBroadcastIntentReturningResultsToActivity(getBaseContext(), searchTerm, C.SearchService.SearchServiceResult.DOWNLOAD_FAILED);
            systemUtilities.killProcess(currentProcess);
        }

        List<Topic> ListTopic = dataUtilities.convertServerResponseToTopicList(response, searchTerm);

        try{
            databaseUtilities.deleteSearchResultsByInTitle(searchTerm);
            databaseUtilities.saveSearchResults(ListTopic);
        } catch (Exception exception) {
            sendBroadcastIntentReturningResultsToActivity(getBaseContext(), searchTerm, C.SearchService.SearchServiceResult.DATABASE_ERROR);
            systemUtilities.killProcess(currentProcess);
        }

        sendBroadcastIntentReturningResultsToActivity(getBaseContext(), searchTerm, C.SearchService.SearchServiceResult.DOWNLOAD_SUCCESSFUL);
    }

    private void stopSelfAndBroadcastOutcomeIfSearchYieldedZeroResult(String currentProcess) {
        if (response.getItems().length == 0) {
            sendBroadcastIntentReturningResultsToActivity(getBaseContext(), searchTerm, C.SearchService.SearchServiceResult.ZERO_SEARCH_RESULTS);
            systemUtilities.killProcess(currentProcess);
        }
    }

    private void sendBroadcastIntentReturningResultsToActivity(Context context, String searchTerm, Enum result) {
        Intent intent = new Intent();
        intent.setAction(C.General.STACKOVERFLOW_SEARCH);
        intent.setPackage(context.getPackageName());
        intent.putExtra(C.General.SEARCH_IN_TITLE_PARAMETER, searchTerm);
        intent.putExtra(C.SearchService.SEARCH_SERVICE_RESULT, result);

        sendBroadcast(intent);
    }

    public StackoverflowInTitleSearch() { super(StackoverflowInTitleSearch.class.getName());  }

}
