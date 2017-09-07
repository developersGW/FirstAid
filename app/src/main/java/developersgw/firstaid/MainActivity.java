package developersgw.firstaid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.MarkdownView;

import static developersgw.firstaid.R.menu.menu_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Context context = getApplicationContext();
        CharSequence text = "Herzlich Willkommen bei FirstAid von developersGW!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        showChangelog();
        showLiabilityDisclaimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openMapActivity(MenuItem item) {
        //Toast.makeText(getApplicationContext(), "Hier entsteht die MapActivity.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openAboutActivity(MenuItem item){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

public void openChangelog(MenuItem item){
    showChangelog();
}

    public void showChangelog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        MarkdownView markdownView = new MarkdownView(this);
        markdownView.loadMarkdownFromAssets("changelog.md");
        alert.setView(markdownView);
        alert.setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public void showLiabilityDisclaimer() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        MarkdownView markdownView = new MarkdownView(this);
        markdownView.loadMarkdownFromAssets("disclaimer.md");
        alert.setView(markdownView);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
