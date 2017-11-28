package developersgw.firstaid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    Menu mainmenu;

    private void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mainmenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goHome(MenuItem item) {
        ((WebView) findViewById(R.id.webview)).loadUrl("file:///android_asset/home.html");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getBaseContext(), "Welcome to " + getString(R.string.app_name) + "!", Toast.LENGTH_SHORT).show();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        final WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getSupportActionBar().setTitle(title);
                if (webview.canGoBack() && !view.getUrl().equals("file:///android_asset/home.html")) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mainmenu.findItem(R.id.home).setVisible(true);
                }
                else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    mainmenu.findItem(R.id.home).setVisible(false);
                }
                super.onReceivedTitle(view, title);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                hideDialog();
                final Uri uri = Uri.parse(url);
                if (!uri.getScheme().equals("file")) {
                    if (uri.getHost() != null) {
                        if (uri.getHost() != null && uri.getHost().equals("firstaid.tim-ney.de")) {
                            dialog = new ProgressDialog(MainActivity.this);
                            dialog.setCancelable(false);
                            dialog.setTitle("Inhalte werden nachgeladen…");
                            dialog.setMessage(url);
                            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ausblenden", (DialogInterface.OnClickListener) null);
                            dialog.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                    .setCancelable(false)
                                    .setTitle("Haftungsausschluss")
                                    .setMessage("Die Mitwirkenden am " + getString(R.string.app_name) + "-Projekt sind nicht für Webpräsenzen Dritter verantwortlich und übernehmen keinerlei Haftung für deren Inhalte bzw. wie aktuell diese sind.")
                                    .setPositiveButton("Fortfahren", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                                            startActivity(browserIntent);
                                        }
                                    })
                                    .setNegativeButton("Abbrechen", null);
                            builder.create().show();
                            return true;
                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setCancelable(false)
                                .setMessage(url)
                                .setPositiveButton("OK", null);
                        builder.create().show();
                        return true;
                    }
                }
                return false;
            }
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                hideDialog();
            }
        });
        webview.addJavascriptInterface(new WebAppInterface(this), "firstaid");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.previouslyStarted), false);
        if (previouslyStarted) {
            webview.loadUrl("file:///android_asset/home.html");
        }
        else {
            webview.loadUrl("file:///android_asset/license.html");
        }
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

        @JavascriptInterface
        public void agree() {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
            edit.putBoolean(getString(R.string.previouslyStarted), true);
            edit.commit();
        }

        @JavascriptInterface
        public void decline() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Abgelehnt")
                    .setMessage("Um diese App nutzen zu können, musst du den Lizenzbedingungen zustimmen.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
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
        WebView webview = (WebView) findViewById(R.id.webview);
        if (webview.canGoBack() && !webview.getUrl().equals("file:///android_asset/home.html")) {
            goBack();
        }
        else {
            finish();
        }
    }

}
