package pl.zaliczenie.linijka2d.ui.Maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.zaliczenie.linijka2d.R;
import pl.zaliczenie.linijka2d.ui.tutorial.MenuTutorialFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pl.zaliczenie.linijka2d.R;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        ZMIENIONE:
        tutaj zamiast tego co było wcześniej
        getSupportFragmentManager()
        dałem
        getChildFragmentManager()
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng szczecin = new LatLng(53.425126, 14.557921);
        mMap.addMarker(new MarkerOptions().position(szczecin).title("Wymiarowanie - 3D"));


        LatLng warszawa = new LatLng(52.253869, 20.898279);
        mMap.addMarker(new MarkerOptions().position(warszawa).title("Wymiarowanie - plany budynków"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(warszawa));

        LatLng krakow = new LatLng(50.074486, 19.913732);
        mMap.addMarker(new MarkerOptions().position(krakow).title("Wymiarowanie - części samochodowe"));


        LatLng wroclaw = new LatLng(51.102819, 17.026566);
        mMap.addMarker(new MarkerOptions().position(wroclaw).title("Wymiarowanie - cześci hydrauliczne"));


        LatLng gdansk = new LatLng(54.393937, 18.599345);
        mMap.addMarker(new MarkerOptions().position(gdansk).title("Wymiarowanie - meble"));


        LatLng lublin = new LatLng(51.234858, 22.570292);
        mMap.addMarker(new MarkerOptions().position(lublin).title("Wymiarowanie - przyrzady kuchenne"));
    }
}