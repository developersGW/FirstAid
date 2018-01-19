// authors: SecanoLibre
package developersgw.firstaid;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MapActivity3 extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap map;
    Menu mainmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiZGV2ZWxvcGVyc2d3IiwiYSI6ImNqNGlqanRvczBhZXAyd2t3ZnVvbmtiMGcifQ.L9DjFHTma0Lhcv4E3ikQmA");
        setContentView(R.layout.activity_map3);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Karte");
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(final MapboxMap mapboxMap) {
            map = mapboxMap;
            }
    });
        final FloatingActionButton zoominButton = findViewById(R.id.zoominButton);
        zoominButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                zoomIn();
            }
        });
        final FloatingActionButton zoomoutButton = findViewById(R.id.zoomoutButton);
        zoomoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                zoomOut();
            }
        });
    }

    public void zoomIn(){
        CameraPosition position = new CameraPosition.Builder()
                .zoom(map.getCameraPosition().zoom+1)
                .build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 1000);
    }

    public void zoomOut(){
        CameraPosition position2 = new CameraPosition.Builder()
                .zoom(map.getCameraPosition().zoom-1)
                .build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position2), 1000);
    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
