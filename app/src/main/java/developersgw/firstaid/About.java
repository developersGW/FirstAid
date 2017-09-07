package developersgw.firstaid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        TextView linkToProjectSite = (TextView) findViewById(R.id.link_to_project_site);
        linkToProjectSite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWebPage("https://github.com/developersGW/FirstAid");
            }
        });

        TextView linkToGPL = (TextView) findViewById(R.id.link_to_gpl);
        linkToGPL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWebPage("https://www.gnu.org/licenses/");
            }
        });

    TextView materialDesignIcons = (TextView) findViewById(R.id.material_design_icons);
        materialDesignIcons.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            openWebPage("https://material.io/icons/");}
    });

    TextView markdownView = (TextView) findViewById(R.id.markdownview);
        markdownView.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
        openWebPage("https://github.com/mukeshsolanki/MarkdownView-Android");
        }
        });
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
