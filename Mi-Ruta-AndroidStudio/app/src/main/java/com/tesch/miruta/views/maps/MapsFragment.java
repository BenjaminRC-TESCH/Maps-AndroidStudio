package com.tesch.miruta.views.maps;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.tesch.miruta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private GoogleMap mMap;
    private static final String ARG_PARAM_OPCION_BOTON = "opcionBoton";
    private String opcionBoton;
    private static final int POLYLINE_STROKE_WIDTH_PX = 20;

    public static MapsFragment newIntance(String opcionBoton){
        MapsFragment mapsFragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_OPCION_BOTON, opcionBoton);
        mapsFragment.setArguments(args);
        return mapsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            opcionBoton = getArguments().getString(ARG_PARAM_OPCION_BOTON);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings;
        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    ((Activity) requireContext()) , new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
            return;
        }

        mMap.setMyLocationEnabled(true);


        LatLng origen = new LatLng(19.259939328808784, -98.89771222062437);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen,15));


        direction(mMap);
    }

    public void direction(GoogleMap googleMap) {
        if (Objects.equals(opcionBoton, "ruta1")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.25993684237464, -98.89766400425555)).title("Base"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.279247717495615, -98.83314282116105)).title("Destino"));
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .jointType(JointType.ROUND)
                    .add(
                            new LatLng(19.25993684237464, -98.89766400425555),//Origen ruta 36
                            new LatLng(19.25914176397708, -98.8980247613738),
                            new LatLng(19.258754712847015, -98.89713589049333),
                            new LatLng(19.257930043167562, -98.89520097927787),
                            new LatLng(19.257323598906193, -98.89375929193318),
                            new LatLng(19.256482050029636, -98.89175352860634),
                            new LatLng(19.25769637931885, -98.8913492258069),
                            new LatLng(19.258376253538174, -98.89113330797855),
                            new LatLng(19.25950313460304, -98.89072417191389),
                            new LatLng(19.260153848654156, -98.89030380033861),
                            new LatLng(19.261360608799393, -98.88947479465739),
                            new LatLng(19.261375801212388, -98.8887653503619),
                            new LatLng(19.26151506492064, -98.88790167906501),
                            new LatLng(19.262045509438444, -98.88167300898347),
                            new LatLng(19.262015124739243, -98.8811768003154),
                            new LatLng(19.261830284362183, -98.88054916339941),
                            new LatLng(19.26184294466912, -98.88001272160412),
                            new LatLng(19.26195435532502, -98.87956747490675),
                            new LatLng(19.262331631754318, -98.87868234592446),
                            new LatLng(19.262496215164784, -98.8772259064259),
                            new LatLng(19.263328918349785, -98.86792239646523),
                            new LatLng(19.26389637810982, -98.86132174979782),
                            new LatLng(19.264010697842235, -98.85977331626884),
                            new LatLng(19.263982684050273, -98.85892417720086),
                            new LatLng(19.263334629902747, -98.85659605677029),
                            new LatLng(19.266021100419326, -98.85574579652811),
                            new LatLng(19.26610465628797, -98.85560900386419),
                            new LatLng(19.265889436539897, -98.85523617679976),
                            new LatLng(19.265562808380736, -98.85482848104056),
                            new LatLng(19.26542608012157, -98.85438591653963),
                            new LatLng(19.265580532407082, -98.85379851277453),
                            new LatLng(19.265800816564774, -98.85356516058961),
                            new LatLng(19.26660599062708, -98.85299116786935),
                            new LatLng(19.267823872169647, -98.85218918737274),
                            new LatLng(19.268180879398805, -98.85144353327975),
                            new LatLng(19.2689531263983, -98.84946674521876),
                            new LatLng(19.269400015223933, -98.8458028476991),
                            new LatLng(19.269602570658048, -98.84244472199732),
                            new LatLng(19.269759550950504, -98.8408944051978),
                            new LatLng(19.269921594964995, -98.83916169817009),//entrada de villas
                            new LatLng(19.273030783419504, -98.83827656919992),
                            new LatLng(19.27520692707385, -98.83777767832645),
                            new LatLng(19.2752752871433, -98.83797616179336),
                            new LatLng(19.27528035085061, -98.83815050538465),
                            new LatLng(19.275272755289595, -98.83877546010413),
                            new LatLng(19.277569130570054, -98.83803785262234),
                            new LatLng(19.278232465621212, -98.83783400473762),
                            new LatLng(19.279422411068463, -98.8374236267534),
                            new LatLng(19.27928316258718, -98.83612543757849),
                            new LatLng(19.279245185708277, -98.835543398235),
                            new LatLng(19.279270503630297, -98.83498281654134),
                            new LatLng(19.279247717501722, -98.83435786183055),
                            new LatLng(19.279247717495615, -98.83314282116105)//Destino la loma
                    ));
            polyline1.setEndCap(new RoundCap());
            polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
            polyline1.setColor(R.color.lavender);
            polyline1.setJointType(JointType.ROUND);
        } else if (Objects.equals(opcionBoton, "ruta2")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.260619677147904, -98.89895293206177)).title("Base"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.271699479353416, -98.82948389896593)).title("Destino"));
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .jointType(JointType.ROUND)
                    .add(
                            new LatLng(19.260619677147904, -98.89895293206177),//Origen ruta 32
                            new LatLng(19.260989360413845, -98.89975759478148),
                            new LatLng(19.261294475105878, -98.90047374457917),
                            new LatLng(19.260394785906517, -98.9009323022793),
                            new LatLng(19.25914176397708, -98.8980247613738),
                            new LatLng(19.258754712847015, -98.89713589049333),
                            new LatLng(19.257930043167562, -98.89520097927787),
                            new LatLng(19.257323598906193, -98.89375929193318),
                            new LatLng(19.256482050029636, -98.89175352860634),
                            new LatLng(19.25769637931885, -98.8913492258069),
                            new LatLng(19.258376253538174, -98.89113330797855),
                            new LatLng(19.25950313460304, -98.89072417191389),
                            new LatLng(19.260153848654156, -98.89030380033861),
                            new LatLng(19.261360608799393, -98.88947479465739),
                            new LatLng(19.261375801212388, -98.8887653503619),
                            new LatLng(19.26151506492064, -98.88790167906501),
                            new LatLng(19.262045509438444, -98.88167300898347),
                            new LatLng(19.262015124739243, -98.8811768003154),
                            new LatLng(19.261830284362183, -98.88054916339941),
                            new LatLng(19.26184294466912, -98.88001272160412),
                            new LatLng(19.26195435532502, -98.87956747490675),
                            new LatLng(19.262331631754318, -98.87868234592446),
                            new LatLng(19.262496215164784, -98.8772259064259),
                            new LatLng(19.263328918349785, -98.86792239646523),
                            new LatLng(19.26389637810982, -98.86132174979782),
                            new LatLng(19.264010697842235, -98.85977331626884),
                            new LatLng(19.263982684050273, -98.85892417720086),
                            new LatLng(19.263334629902747, -98.85659605677029),
                            new LatLng(19.266021100419326, -98.85574579652811),
                            new LatLng(19.26610465628797, -98.85560900386419),
                            new LatLng(19.265889436539897, -98.85523617679976),
                            new LatLng(19.265562808380736, -98.85482848104056),
                            new LatLng(19.26542608012157, -98.85438591653963),
                            new LatLng(19.265580532407082, -98.85379851277453),
                            new LatLng(19.265800816564774, -98.85356516058961),
                            new LatLng(19.26660599062708, -98.85299116786935),
                            new LatLng(19.267823872169647, -98.85218918737274),
                            new LatLng(19.268180879398805, -98.85144353327975),
                            new LatLng(19.2689531263983, -98.84946674521876),
                            new LatLng(19.269400015223933, -98.8458028476991),
                            new LatLng(19.269602570658048, -98.84244472199732),
                            new LatLng(19.269759550950504, -98.8408944051978),
                            new LatLng(19.269921594964995, -98.83916169817009),//entrada de villas
                            new LatLng(19.273030783419504, -98.83827656919992),
                            new LatLng(19.27520692707385, -98.83777767832645),
                            new LatLng(19.275237121527496, -98.8377677273713),
                            new LatLng(19.274768727813154, -98.83531350609351),//
                            new LatLng(19.274166143652458, -98.83212972401151),
                            new LatLng(19.274054741298134, -98.83169520613785),
                            new LatLng(19.27401676320549, -98.83162815091046),
                            new LatLng(19.27444718107774, -98.83072156427775),
                            new LatLng(19.274976973295967, -98.82978010891684),//entrada el llano
                            new LatLng(19.274819365039736, -98.82940728187265),
                            new LatLng(19.274743409200113, -98.82891375539889),
                            new LatLng(19.27469277195356, -98.82866162776135),
                            new LatLng(19.274667453324703, -98.82847387312461),
                            new LatLng(19.271699479353416, -98.82948389896593)//Desnito ruta 32
                    ));
            polyline1.setEndCap(new RoundCap());
            polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
            polyline1.setColor(R.color.lavender);
            polyline1.setJointType(JointType.ROUND);
        } else if (Objects.equals(opcionBoton, "ruta3")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.260619677147904, -98.89895293206177)).title("Base"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.27198264524659, -98.80949932975261)).title("Destino"));
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .jointType(JointType.ROUND)
                    .add(
                            new LatLng(19.260619677147904, -98.89895293206177),//Origen ruta 32
                            new LatLng(19.260989360413845, -98.89975759478148),
                            new LatLng(19.261294475105878, -98.90047374457917),
                            new LatLng(19.260394785906517, -98.9009323022793),
                            new LatLng(19.25914176397708, -98.8980247613738),
                            new LatLng(19.258754712847015, -98.89713589049333),
                            new LatLng(19.257930043167562, -98.89520097927787),
                            new LatLng(19.257323598906193, -98.89375929193318),
                            new LatLng(19.256482050029636, -98.89175352860634),
                            new LatLng(19.25769637931885, -98.8913492258069),
                            new LatLng(19.258376253538174, -98.89113330797855),
                            new LatLng(19.25950313460304, -98.89072417191389),
                            new LatLng(19.260153848654156, -98.89030380033861),
                            new LatLng(19.261360608799393, -98.88947479465739),
                            new LatLng(19.261375801212388, -98.8887653503619),
                            new LatLng(19.26151506492064, -98.88790167906501),
                            new LatLng(19.262045509438444, -98.88167300898347),
                            new LatLng(19.262015124739243, -98.8811768003154),
                            new LatLng(19.261830284362183, -98.88054916339941),
                            new LatLng(19.26184294466912, -98.88001272160412),
                            new LatLng(19.26195435532502, -98.87956747490675),
                            new LatLng(19.262331631754318, -98.87868234592446),
                            new LatLng(19.262496215164784, -98.8772259064259),
                            new LatLng(19.263328918349785, -98.86792239646523),
                            new LatLng(19.26389637810982, -98.86132174979782),
                            new LatLng(19.264010697842235, -98.85977331626884),
                            new LatLng(19.263982684050273, -98.85892417720086),
                            new LatLng(19.263334629902747, -98.85659605677029),
                            new LatLng(19.266021100419326, -98.85574579652811),
                            new LatLng(19.26610465628797, -98.85560900386419),
                            new LatLng(19.265889436539897, -98.85523617679976),
                            new LatLng(19.265562808380736, -98.85482848104056),
                            new LatLng(19.26542608012157, -98.85438591653963),
                            new LatLng(19.265580532407082, -98.85379851277453),
                            new LatLng(19.265800816564774, -98.85356516058961),
                            new LatLng(19.26660599062708, -98.85299116786935),
                            new LatLng(19.267823872169647, -98.85218918737274),
                            new LatLng(19.268180879398805, -98.85144353327975),
                            new LatLng(19.2689531263983, -98.84946674521876),
                            new LatLng(19.269400015223933, -98.8458028476991),
                            new LatLng(19.269602570658048, -98.84244472199732),
                            new LatLng(19.269759550950504, -98.8408944051978),
                            new LatLng(19.269921594964995, -98.83916169817009),//entrada de villas
                            new LatLng(19.273030783419504, -98.83827656919992),
                            new LatLng(19.27520692707385, -98.83777767832645),
                            new LatLng(19.275237121527496, -98.8377677273713),
                            new LatLng(19.274768727813154, -98.83531350609351),//
                            new LatLng(19.274166143652458, -98.83212972401151),
                            new LatLng(19.274054741298134, -98.83169520613785),
                            new LatLng(19.27401676320549, -98.83162815091046),
                            new LatLng(19.27444718107774, -98.83072156427775),
                            new LatLng(19.274976973295967, -98.82978010891684),//entrada el llano
                            new LatLng(19.274819365039736, -98.82940728187265),
                            new LatLng(19.274743409200113, -98.82891375539889),
                            new LatLng(19.27469277195356, -98.82866162776135),
                            new LatLng(19.274667453324703, -98.82847387312461),
                            new LatLng(19.271699479353416, -98.82948389896593),//base ruta 32
                            new LatLng(19.269916792260936, -98.83009062700256),
                            new LatLng(19.270051757255118, -98.82926890957754),
                            new LatLng(19.27014290686816, -98.82903287517705),//
                            new LatLng(19.270306710019163, -98.82873879364185),
                            new LatLng(19.270696626846288, -98.82667349267798),
                            new LatLng(19.270676371450644, -98.82608340671815), //
                            new LatLng(19.270728275896087, -98.82531897711378),
                            new LatLng(19.27075359513117, -98.82477448870277),
                            new LatLng(19.270776382441877, -98.82384644438487),
                            new LatLng(19.270928297765327, -98.82330732037776),
                            new LatLng(19.27101691497335, -98.82278160741126),
                            new LatLng(19.271090340622667, -98.82172749925968),
                            new LatLng(19.271234659908345, -98.81938056635114),
                            new LatLng(19.271049829924394, -98.81914989640504),
                            new LatLng(19.271009319215715, -98.81908284117762),
                            new LatLng(19.27099159577752, -98.81894604851371),
                            new LatLng(19.27099159577752, -98.81884680677715),
                            new LatLng(19.27102197881325, -98.81867782760405),
                            new LatLng(19.27109540446207, -98.81853298832267),
                            new LatLng(19.271295425885345, -98.81829695392614),
                            new LatLng(19.27132580886692, -98.81803409744589),
                            new LatLng(19.271465064120427, -98.81662057330219),
                            new LatLng(19.2714802555961, -98.81453917909461),
                            new LatLng(19.2717101310121, -98.81099262020298),
                            new LatLng(19.27198264524659, -98.80949932975261)//Destino piedras
                    ));
            polyline1.setEndCap(new RoundCap());
            polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
            polyline1.setColor(R.color.lavender);
            polyline1.setJointType(JointType.ROUND);
        } else if (Objects.equals(opcionBoton, "ruta4")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.260619677147904, -98.89895293206177)).title("Base"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.271992340512615, -98.80947791453718)).title("Destino"));
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .jointType(JointType.ROUND)
                    .add(
                            new LatLng(19.260619677147904, -98.89895293206177),//Origen ruta 32
                            new LatLng(19.260989360413845, -98.89975759478148),
                            new LatLng(19.261294475105878, -98.90047374457917),
                            new LatLng(19.260394785906517, -98.9009323022793),
                            new LatLng(19.25914176397708, -98.8980247613738),
                            new LatLng(19.258754712847015, -98.89713589049333),
                            new LatLng(19.257930043167562, -98.89520097927787),
                            new LatLng(19.257323598906193, -98.89375929193318),
                            new LatLng(19.256482050029636, -98.89175352860634),
                            new LatLng(19.25769637931885, -98.8913492258069),
                            new LatLng(19.258376253538174, -98.89113330797855),
                            new LatLng(19.25950313460304, -98.89072417191389),
                            new LatLng(19.260153848654156, -98.89030380033861),
                            new LatLng(19.261360608799393, -98.88947479465739),
                            new LatLng(19.261375801212388, -98.8887653503619),
                            new LatLng(19.26151506492064, -98.88790167906501),
                            new LatLng(19.262045509438444, -98.88167300898347),
                            new LatLng(19.262015124739243, -98.8811768003154),
                            new LatLng(19.261830284362183, -98.88054916339941),
                            new LatLng(19.26184294466912, -98.88001272160412),
                            new LatLng(19.26195435532502, -98.87956747490675),
                            new LatLng(19.262331631754318, -98.87868234592446),
                            new LatLng(19.262496215164784, -98.8772259064259),
                            new LatLng(19.263328918349785, -98.86792239646523),
                            new LatLng(19.26389637810982, -98.86132174979782),
                            new LatLng(19.264010697842235, -98.85977331626884),
                            new LatLng(19.263982684050273, -98.85892417720086),
                            new LatLng(19.263334629902747, -98.85659605677029),
                            new LatLng(19.266021100419326, -98.85574579652811),
                            new LatLng(19.26610465628797, -98.85560900386419),
                            new LatLng(19.265889436539897, -98.85523617679976),
                            new LatLng(19.265562808380736, -98.85482848104056),
                            new LatLng(19.26542608012157, -98.85438591653963),
                            new LatLng(19.265580532407082, -98.85379851277453),
                            new LatLng(19.265800816564774, -98.85356516058961),
                            new LatLng(19.26660599062708, -98.85299116786935),
                            new LatLng(19.267823872169647, -98.85218918737274),
                            new LatLng(19.268180879398805, -98.85144353327975),
                            new LatLng(19.2689531263983, -98.84946674521876),
                            new LatLng(19.269400015223933, -98.8458028476991),
                            new LatLng(19.269602570658048, -98.84244472199732),
                            new LatLng(19.269759550950504, -98.8408944051978),
                            new LatLng(19.269921594964995, -98.83916169817009),//entrada de villas
                            new LatLng(19.273030783419504, -98.83827656919992),
                            new LatLng(19.27520692707385, -98.83777767832645),
                            new LatLng(19.275237121527496, -98.8377677273713),
                            new LatLng(19.274768727813154, -98.83531350609351),//
                            new LatLng(19.274166143652458, -98.83212972401151),
                            new LatLng(19.274054741298134, -98.83169520613785),
                            new LatLng(19.27401676320549, -98.83162815091046),
                            new LatLng(19.27444718107774, -98.83072156427775),
                            new LatLng(19.274976973295967, -98.82978010891684),//entrada el llano
                            new LatLng(19.274819365039736, -98.82940728187265),
                            new LatLng(19.274743409200113, -98.82891375539889),
                            new LatLng(19.27469277195356, -98.82866162776135),
                            new LatLng(19.274667453324703, -98.82847387312461),
                            new LatLng(19.271699479353416, -98.82948389896593),//base ruta 32
                            new LatLng(19.269916792260936, -98.83009062700256),
                            new LatLng(19.270051757255118, -98.82926890957754),
                            new LatLng(19.27014290686816, -98.82903287517705),//
                            new LatLng(19.270306710019163, -98.82873879364185),
                            new LatLng(19.270506507951453, -98.82751566654954),
                            new LatLng(19.27068880662202, -98.82670832162417),
                            new LatLng(19.270676146999563, -98.82637840992446),
                            new LatLng(19.270701466245317, -98.82566226010387),
                            new LatLng(19.270744508957346, -98.82482541090606),
                            new LatLng(19.27075970049905, -98.82402879481437),
                            new LatLng(19.27082553049748, -98.82361573463491),
                            new LatLng(19.270931871207438, -98.82324827200131),
                            new LatLng(19.271007828814675, -98.8229022670339),
                            new LatLng(19.27100529689706, -98.82289422041467),
                            new LatLng(19.27127874399847, -98.82283521182259),
                            new LatLng(19.273493670618215, -98.82215190035005),
                            new LatLng(19.273156930068467, -98.82141161065917),
                            new LatLng(19.272989825479304, -98.82119971614453),
                            new LatLng(19.27253914860927, -98.82039773565275),
                            new LatLng(19.272199874194605, -98.81973523002344),
                            new LatLng(19.27205049195105, -98.81921219926635),
                            new LatLng(19.27203656648832, -98.81909418207937),
                            new LatLng(19.272296086415558, -98.81904858452472),
                            new LatLng(19.272349256301766, -98.81842362981385),
                            new LatLng(19.272577127043004, -98.8176511536065),
                            new LatLng(19.272767019090576, -98.81712544063491),
                            new LatLng(19.272911336900616, -98.81679016451504),
                            new LatLng(19.272939187693872, -98.81631273132152),
                            new LatLng(19.273005016815116, -98.81593990425709),
                            new LatLng(19.27365292047475, -98.81163176368611),
                            new LatLng(19.27365326139274, -98.81163441065081),
                            new LatLng(19.27165812878871, -98.81177924994202),
                            new LatLng(19.271759405140664, -98.81056689143038),
                            new LatLng(19.271992340512615, -98.80947791453718)
                    ));
            polyline1.setEndCap(new RoundCap());
            polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
            polyline1.setColor(R.color.lavender);
            polyline1.setJointType(JointType.ROUND);
        } else if (Objects.equals(opcionBoton, "ruta5")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.260619677147904, -98.89895293206177)).title("Base"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(19.27723003689549, -98.82540007030435)).title("Destino"));
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .jointType(JointType.ROUND)
                    .add(
                            new LatLng(19.260619677147904, -98.89895293206177),//Origen ruta 32
                            new LatLng(19.260989360413845, -98.89975759478148),
                            new LatLng(19.261294475105878, -98.90047374457917),
                            new LatLng(19.260394785906517, -98.9009323022793),
                            new LatLng(19.25914176397708, -98.8980247613738),
                            new LatLng(19.258754712847015, -98.89713589049333),
                            new LatLng(19.257930043167562, -98.89520097927787),
                            new LatLng(19.257323598906193, -98.89375929193318),
                            new LatLng(19.256482050029636, -98.89175352860634),
                            new LatLng(19.25769637931885, -98.8913492258069),
                            new LatLng(19.258376253538174, -98.89113330797855),
                            new LatLng(19.25950313460304, -98.89072417191389),
                            new LatLng(19.260153848654156, -98.89030380033861),
                            new LatLng(19.261360608799393, -98.88947479465739),
                            new LatLng(19.261375801212388, -98.8887653503619),
                            new LatLng(19.26151506492064, -98.88790167906501),
                            new LatLng(19.262045509438444, -98.88167300898347),
                            new LatLng(19.262015124739243, -98.8811768003154),
                            new LatLng(19.261830284362183, -98.88054916339941),
                            new LatLng(19.26184294466912, -98.88001272160412),
                            new LatLng(19.26195435532502, -98.87956747490675),
                            new LatLng(19.262331631754318, -98.87868234592446),
                            new LatLng(19.262496215164784, -98.8772259064259),
                            new LatLng(19.263328918349785, -98.86792239646523),
                            new LatLng(19.26389637810982, -98.86132174979782),
                            new LatLng(19.264010697842235, -98.85977331626884),
                            new LatLng(19.263982684050273, -98.85892417720086),
                            new LatLng(19.263334629902747, -98.85659605677029),
                            new LatLng(19.266021100419326, -98.85574579652811),
                            new LatLng(19.26610465628797, -98.85560900386419),
                            new LatLng(19.265889436539897, -98.85523617679976),
                            new LatLng(19.265562808380736, -98.85482848104056),
                            new LatLng(19.26542608012157, -98.85438591653963),
                            new LatLng(19.265580532407082, -98.85379851277453),
                            new LatLng(19.265800816564774, -98.85356516058961),
                            new LatLng(19.26660599062708, -98.85299116786935),
                            new LatLng(19.267823872169647, -98.85218918737274),
                            new LatLng(19.268180879398805, -98.85144353327975),
                            new LatLng(19.2689531263983, -98.84946674521876),
                            new LatLng(19.269400015223933, -98.8458028476991),
                            new LatLng(19.269602570658048, -98.84244472199732),
                            new LatLng(19.269759550950504, -98.8408944051978),
                            new LatLng(19.269921594964995, -98.83916169817009),//entrada de villas
                            new LatLng(19.273030783419504, -98.83827656919992),
                            new LatLng(19.27520692707385, -98.83777767832645),
                            new LatLng(19.275237121527496, -98.8377677273713),
                            new LatLng(19.274768727813154, -98.83531350609351),//
                            new LatLng(19.274166143652458, -98.83212972401151),
                            new LatLng(19.274054741298134, -98.83169520613785),
                            new LatLng(19.27401676320549, -98.83162815091046),
                            new LatLng(19.27444718107774, -98.83072156427775),
                            new LatLng(19.274976973295967, -98.82978010891684),//entrada el llano
                            new LatLng(19.27723003689549, -98.82540007030435)
                    ));
            polyline1.setEndCap(new RoundCap());
            polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
            polyline1.setColor(R.color.lavender);
            polyline1.setJointType(JointType.ROUND);
        }  else {
            Toast.makeText(getContext(),"Algo salio mal...",Toast.LENGTH_LONG).show();
        }
    }


}
