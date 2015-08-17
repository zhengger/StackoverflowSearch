package bruno.stackrest.Activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import bruno.stackrest.Activities.BaseActivity;
import bruno.stackrest.Activities.SearchResults.SearchResultsActivity;
import bruno.stackrest.R;
import bruno.stackrest.Services.StackoverflowInTitleSearch;
import bruno.stackrest.Utilities.InputUtilities;
import bruno.stackrest.Utilities.NetworkUtilities;
import bruno.stackrest.Utilities.ViewUtilities;
import butterknife.Bind;
import butterknife.OnClick;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Bind(R.id.et_SearchTopic) EditText searchTermInput;

    @Inject private NetworkUtilities networkUtilities;

    @Inject private InputUtilities inputUtilities;

    @Inject private ViewUtilities viewUtilities;

    MainActivityHeadlessFragment mainActivityHeadlessFragment;

    @OnClick(R.id.button_search)
    public void validateThenStartSearch(View view) {

        String inTitleParameter =  searchTermInput.getText().toString();

        if (!networkUtilities.isNetworkAvailable()) {
            Toast.makeText(getBaseContext(), "You need an Internet connection to search Stackoverflow.", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (!inputUtilities.isInputStringValid(inTitleParameter)) {
            Toast.makeText(getBaseContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            return ;
        }

        viewUtilities.hideKeyboardFromInputView(this, searchTermInput);

        displaySnackbarMessage(getString(R.string.ongoing_search_please_wait));

        Intent serviceIntent = StackoverflowInTitleSearch.makeIntent(this, inTitleParameter);
        startService(serviceIntent);
    }

    @Override
    protected void manageHeadlessFragmentBroadcastReceiverLifeCycle() {
        mainActivityHeadlessFragment = (MainActivityHeadlessFragment) getSupportFragmentManager().findFragmentByTag("tag");

        if(mainActivityHeadlessFragment == null) {
            System.out.println("created fragment again");
            mainActivityHeadlessFragment = new MainActivityHeadlessFragment();
            mainActivityHeadlessFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(mainActivityHeadlessFragment, "tag").commit();
        }
    }

    protected void startSearchResultsActivity(String searchTerm) {
        startActivity(SearchResultsActivity.makeIntent(this, searchTerm));
    }
}