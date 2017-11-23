package developersgw.firstaid;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getSupportActionBar().setTitle(title);
                if (webview.canGoBack()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                super.onReceivedTitle(view, title);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (! Uri.parse(url).getScheme().equals("file")) {
                    dialog = ProgressDialog.show(MainActivity.this, "Loading…", url, true);
                }
                webview.loadUrl(url);
                return true;
            }
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        webview.addJavascriptInterface(new WebAppInterface(this), "firstaid");
        webview.loadUrl("file:///android_asset/home.html");
    }

    public class WebAppInterface {
        Context context;

        WebAppInterface(Context newcontext) {
            context = newcontext;
        }

        @JavascriptInterface
        public void dialog(String message, String title) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        }
    }

    private void goBack() {
        ((WebView) findViewById(R.id.webview)).goBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goBack();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (((WebView) findViewById(R.id.webview)).canGoBack()) {
            goBack();
        }
        else {
            finish();
        }
    }

}
