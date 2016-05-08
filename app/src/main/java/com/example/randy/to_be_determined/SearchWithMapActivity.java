package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * This class displays a map activity that updates dynamically with markers to show which buildings
 * on campus have spots available for the user to reserve.
 *
 * Resources:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 * http://www.mopri.de/2010/timertask-bad-do-it-the-android-way-use-a-handler/
 * https://developers.google.com/maps/documentation/android-api/marker#customize_a_marker
 * http://stackoverflow.com/questions/15925319/how-to-disable-android-map-marker-click-auto-center
 * https://developers.google.com/maps/documentation/android-api/infowindows#custom_info_windows
 */
public class SearchWithMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    /* PRIVATE CONSTANTS */
    private final int DELAY = 2500;
    public final static String EXTRA_MESSAGE = "com.example.randy.to_be_determined.MESSAGE";

    /* PRIVATE VARIABLES */
    private GoogleMap mMap;
    private MapMarker campusBuildings[] = new MapMarker[15];
    private Handler handler = new Handler();
    private Button basicSearchBtn;

    private class MapMarker{
        /* PRIVATE VARIABLES */
        private String name;
        private int numSpotsAvailable = 0;
        private MarkerOptions markerOps = new MarkerOptions();
        private Marker marker;

        public MapMarker(String name, LatLng pos) {
            this.name = name;

            markerOps.position(pos);
            markerOps.title(name);
            markerOps.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerOps.snippet("Spots available: " + numSpotsAvailable);
            markerOps.visible(false);
        }

        public void updateNumSpots(int numSpotsAvailable)
        {
            this.numSpotsAvailable = numSpotsAvailable;
            marker.setSnippet("Number of Spots: " + this.numSpotsAvailable);

            if(this.numSpotsAvailable > 0)
                marker.setVisible(true);
            else
                marker.setVisible(false);
        }
    }

    private class UpdateMapMarkers implements Runnable{
        public UpdateMapMarkers()
        {

        }

        @Override
        public void run() {
            if (mMap != null)
                    new FindNumSpots().execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Intialize the campus buildings hashtable */
        campusBuildings[0] = new MapMarker("Performing Arts and Humanities", new LatLng(39.2549, -76.7147));
        campusBuildings[1] = new MapMarker("Information Technology", new LatLng(39.2537, - 76.7142));
        campusBuildings[2] = new MapMarker("Engineering", new LatLng(39.2544, -76.7139));
        campusBuildings[3] = new MapMarker("Fine Arts", new LatLng(39.2550, -76.7135));
        campusBuildings[4] = new MapMarker("Sherman Hall", new LatLng(39.2536, -76.7134));
        campusBuildings[5] = new MapMarker("University Center", new LatLng(39.2542, -76.7133));
        campusBuildings[6] = new MapMarker("Meyerhoff", new LatLng(39.2549, -76.7127));
        campusBuildings[7] = new MapMarker("Sondheim Hall", new LatLng(39.2533, -76.7128));
        campusBuildings[8] = new MapMarker("Math and Psychology", new LatLng(39.2541, -76.7124));
        campusBuildings[9] = new MapMarker("Biological Science", new LatLng(39.2547, -76.7122));
        campusBuildings[10] = new MapMarker("The Commons", new LatLng(39.2548, -76.7108));;
        campusBuildings[11] = new MapMarker("Physics", new LatLng(39.2544, -76.7095));
        campusBuildings[12] = new MapMarker("Public Policy", new LatLng(39.2552, -76.7091));
        campusBuildings[13] = new MapMarker("AOK Library", new LatLng(39.2565, -76.7114));
        campusBuildings[14] = new MapMarker("Retriever Activities Center", new LatLng(39.2528, -76.7124));

        basicSearchBtn = (Button) findViewById(R.id.basicSearchBtn);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView)findViewById(R.id.mapSearch), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", basicSearchBtn, getAssets());

        basicSearchBtn.setOnClickListener(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        handler.removeCallbacks(null);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        handler.postDelayed(new UpdateMapMarkers(), 0);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.basicSearchBtn:
                Intent intent = new Intent(this, SearchBasicActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMapClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        // Move camera to UMBC
        LatLng umbc = new LatLng(39.2547, -76.7121);
        CameraPosition umbcCamera = new CameraPosition.Builder()
                .target(umbc)
                .bearing(20)
                .zoom(16.3f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(umbcCamera));

        for(int i = 0; i < 15; i++)
           campusBuildings[i].marker = mMap.addMarker(campusBuildings[i].markerOps);
    }

    @Override
    public void onMapClick(LatLng point) {
        // Move camera to UMBC
        LatLng umbc = new LatLng(39.2547, -76.7121);
        CameraPosition umbcCamera = new CameraPosition.Builder()
                .target(umbc)
                .bearing(20)
                .zoom(16.3f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(umbcCamera));
    }

    @Override
    public void onInfoWindowClick(Marker m)
    {
        /*
        Intent selectSpotActivity = new Intent(this, ListOfSpots.class);
        selectSpotActivity.putExtra(EXTRA_MESSAGE, m.getTitle().toString());
        startActivity(selectSpotActivity);*/
    }

    public class FindNumSpots extends AsyncTask<Void, Void, String> {
        String [] spots = new String[15];

        protected String doInBackground(Void... v)
        {
            /* LOCAL VARIABLES */
            URL url;
            String s = "";

            try {
                for(int i = 0; i < 15; i++)
                {
                    url = new URL("http://mpss.csce.uark.edu/~palande1/fetch_post_buildings.php?username=" + ((SpotSwap) getApplication()).getUserName() + "&location=" + URLEncoder.encode(campusBuildings[i].name, "UTF-8"));
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                    spots[i] = responseStreamReader.readLine();
                    Log.i("Response", s);

                    urlConnection.disconnect();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPostExecute(String result) {
            for(int i = 0; i < 15; i++)
                campusBuildings[i].updateNumSpots(Integer.valueOf(spots[i]));

            handler.postDelayed(new UpdateMapMarkers(), DELAY);
        }
    }
}
