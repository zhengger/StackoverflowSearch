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

import bruno.stackrest.AbstractClasses.BaseActivity;
import bruno.stackrest.Activities.SingleTopicView.WebViewActivity;
import bruno.stackrest.Adapters.TopicAdapter;
import bruno.stackrest.POJOs.Topic;
import bruno.stackrest.R;
import bruno.stackrest.Services.StackoverflowInTitleSearch;
import bruno.stackrest.Utilities.C;
import bruno.stackrest.Utilities.DatabaseUtilities;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
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
    ListView listView;

    private List<Topic> ListTopic ;
    private View header;
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle Icicle) {
        super.onCreate(Icicle);

        fetchDataAndSetListAdapter();
        initializeHeader();
    }

    protected void fetchDataAndSetListAdapter() {
        try {
            ListTopic = databaseUtilities.getListTopicByInTitle(searchTerm);
        } catch (Exception exception) {
            displaySnackbarMessage(getString(R.string.error_database));
        }
        adapter = new TopicAdapter(getBaseContext(), R.layout.item_searchresult, ListTopic);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void manageHeadlessFragmentBroadcastReceiverLifeCycle() {
        searchResultsHeadlessFragment = (SearchResultsHeadlessFragment) getSupportFragmentManager().findFragmentByTag(C.Fragments.HEADLESS_FRAGMENT_TAG);

        if(searchResultsHeadlessFragment == null) {
            searchResultsHeadlessFragment = new SearchResultsHeadlessFragment();
            searchResultsHeadlessFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(searchResultsHeadlessFragment, C.Fragments.HEADLESS_FRAGMENT_TAG).commit();
        }
    }

    public static Intent makeIntent (Context context, String search_string) {
        Intent intent = new Intent(context, SearchResultsActivity.class);
        intent.putExtra(C.General.STACKOVERFLOW_SEARCH, search_string);

        return intent;
    }

    @OnItemClick(android.R.id.list) void onItemClick(int position)  {
        if (position == 0) {
            refreshDataFromServer();
        } else {
            startWebView(position);
        }
    }

    private void startWebView (Integer position) {
        Intent intent = WebViewActivity.makeIntent(this, ListTopic.get(position - 1).getTopicLink());

        intent.putExtra(C.General.URL, (ListTopic.get(position - 1).getTopicLink()));
        startActivity(intent);
    }

    private void refreshDataFromServer() {
        Intent serviceIntent = StackoverflowInTitleSearch.makeIntent(this, searchTerm);
        startService(serviceIntent);
        displaySnackbarMessage(getString(R.string.ongoing_search_please_wait));
    }

    private void initializeHeader() {
        if (listView.getHeaderViewsCount() == 0) {
            LayoutInflater mInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            header = mInflater.inflate(R.layout.list_header, null);
            listView.addHeaderView(header);
        }
    }
}

