package bruno.stackrest.Activities.SingleTopicView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import bruno.stackrest.AbstractClasses.BaseActivity;
import bruno.stackrest.R;
import bruno.stackrest.Utilities.C;
import butterknife.Bind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;

@ContentView(R.layout.webview)
public class WebViewActivity extends BaseActivity {

    @InjectExtra(C.General.URL) String url ;

    @Bind(R.id.webview) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.loadUrl(url);
    }

    public static Intent makeIntent (Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.General.URL, url);

        return intent;
    }

    @Override
    protected void manageHeadlessFragmentBroadcastReceiverLifeCycle() {}
}
