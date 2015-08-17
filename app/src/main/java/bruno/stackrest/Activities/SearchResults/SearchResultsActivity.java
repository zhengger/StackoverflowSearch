package bruno.stackrest.Activities.SearchResults;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import bruno.stackrest.Activities.BaseActivity;
import bruno.stackrest.Activities.WebViewActivity;
import bruno.stackrest.Adapters.TopicAdapter;
import bruno.stackrest.POJOs.Topic;
import bruno.stackrest.R;
import bruno.stackrest.Services.StackoverflowInTitleSearch;
import bruno.stackrest.Utilities.C;
import bruno.stackrest.Utilities.DatabaseUtilities;
import butterknife.Bind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;

@ContentView(R.layout.search_results)
public class SearchResultsActivity extends BaseActivity {

    SearchResultsHeadlessFragment searchResultsHeadlessFragment;

    @InjectExtra(C.General.STACKOVERFLOW_SEARCH)
    String searchTerm ;

    @Inject
    private DatabaseUtilities databaseUtilities;

    @Bind(android.R.id.list)
    ListView mListView;

    private List<Topic> ListTopic ;
    private View header;
    private TopicAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle Icicle) {
        super.onCreate(Icicle);

        fetchDataAndSetListAdapter();
        initializeHeader();
        initializeClickListener();
    }

    protected void fetchDataAndSetListAdapter() {
        try {
            ListTopic = databaseUtilities.getListTopicByInTitle(searchTerm);
        } catch (Exception exception) {
            displaySnackbarMessage("A database error occured!  ");
        }
        mAdapter = new TopicAdapter(getBaseContext(), R.layout.item_searchresult, ListTopic);  //TODO: It seems like this is inefficient here.
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void manageHeadlessFragmentBroadcastReceiverLifeCycle() {
        searchResultsHeadlessFragment = (SearchResultsHeadlessFragment) getSupportFragmentManager().findFragmentByTag("tag");

        if(searchResultsHeadlessFragment == null) {
            System.out.println("created fragment again");
            searchResultsHeadlessFragment = new SearchResultsHeadlessFragment();
            searchResultsHeadlessFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(searchResultsHeadlessFragment, "tag").commit();
        }
    }

    public static Intent makeIntent (Context context, String search_string) {
        Intent intent = new Intent(context, SearchResultsActivity.class);
        intent.putExtra(C.General.STACKOVERFLOW_SEARCH, search_string);

        return intent;
    }

    private void initializeClickListener() { //TODO: is there an easier click listener annotation?
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    refreshDataFromServer();
                } else {
                    startWebView(position);
                }
            }

        });
    }

    private void startWebView (Integer position) {
        Intent Intent = WebViewActivity.makeIntent(this, ListTopic.get(position - 1).getTopicLink());

        Intent.putExtra(C.General.URL, (ListTopic.get(position - 1).getTopicLink()));
        startActivity(Intent);
    }

    private void refreshDataFromServer() {
        Intent serviceIntent = StackoverflowInTitleSearch.makeIntent(this, searchTerm);
        startService(serviceIntent);
        displaySnackbarMessage(getString(R.string.ongoing_search_please_wait));
    }

    private void initializeHeader() {
        if (mListView.getHeaderViewsCount() == 0) {
            LayoutInflater mInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            header = mInflater.inflate(R.layout.list_header, null);
            mListView.addHeaderView(header);   }
    }

}

