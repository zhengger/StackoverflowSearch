package bruno.stackrest.Activities.SearchResults;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import bruno.stackrest.Fragments.BaseFragment;
import bruno.stackrest.Utilities.C;

/**
 * Created by Bruno on 2015-08-14.
 */
public class SearchResultsHeadlessFragment extends BaseFragment {

    @Override
    public void broadcastReceiverOnReceive (Context context, Intent intent) {
        String searchTerm = intent.getStringExtra(C.General.SEARCH_IN_TITLE_PARAMETER);

        SearchResultsActivity parentActivity = ((SearchResultsActivity)getActivity()) ;
        Enum searchServiceResult = (Enum) intent.getSerializableExtra(C.SearchService.SEARCH_SERVICE_RESULT);

        if (searchServiceResult.equals(C.SearchService.SearchServiceResult.DOWNLOAD_SUCCESSFUL)) {
            parentActivity.fetchDataAndSetListAdapter();
            parentActivity.displaySnackbarMessage("Your data has been refreshed!");
            System.out.println("The data download was succesful");
        } else if (searchServiceResult.equals(C.SearchService.SearchServiceResult.DOWNLOAD_FAILED)) {
            parentActivity.displaySnackbarMessage("Download failed!");
        } else if (searchServiceResult.equals(C.SearchService.SearchServiceResult.DATABASE_ERROR)) {
            parentActivity.displaySnackbarMessage("Database threw an error, not cool!");
        } else if (searchServiceResult.equals(C.SearchService.SearchServiceResult.ZERO_SEARCH_RESULTS)){
            parentActivity.displaySnackbarMessage("No search result was obtained!");
        }
    }

    @Override
    protected IntentFilter makeIntentFilter (String filterAction) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(C.General.STACKOVERFLOW_SEARCH);

        return filter;
    }
}
