package com.lvbo.template.module.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.lvbo.template.R;
import com.lvbo.template.base.BaseFragment;
import com.lvbo.template.base.BasePresenter;

import butterknife.ButterKnife;


/**
 * Created by lvbo on 16/9/2.
 */
public class MapLocatorFragment extends BaseFragment implements OnMapReadyCallback {


    private GoogleMap googleMap;

    private com.google.android.gms.maps.MapFragment mMapFragment;


    public static MapLocatorFragment getInstance() {
        MapLocatorFragment splashFragment = new MapLocatorFragment();
        return splashFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.map_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView = (RelativeLayout) inflater.inflate(R.layout.base_fmt, container, false);
        subContainer = (LinearLayout) contentView.findViewById(R.id.sub_container);

        View bodyView = getBodyView(inflater);
        subContainer.addView(bodyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, contentView);
        init();
        setUp(contentView);

        return contentView;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setUp(View contentView) {


        mMapFragment = (com.google.android.gms.maps.MapFragment) getBaseActivity().getFragmentManager()
                .findFragmentById(R.id.detail_map);
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        //addMarker
        /*if (googleMap != null && null != mStoreInfo) {
            googleMap.clear();
            LatLng mLatLng = new LatLng(Double.parseDouble(mStoreInfo.getLatitude()), Double.parseDouble(mStoreInfo.getLongitude()));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mLatLng);
            int locatorIconRes = Constant.STORE_TYPE_COLUMBIA.equals(mStoreInfo.getType()) ? R.drawable.store_locator_map_view_columbia : R.drawable.store_locator_map_view_go_wild;
            markerOptions.icon(BitmapDescriptorFactory.fromResource(locatorIconRes));

            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
        }*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        com.google.android.gms.maps.MapFragment map = (com.google.android.gms.maps.MapFragment) getBaseActivity().getFragmentManager().findFragmentById(R.id.detail_map);
        if (map != null) {
            getBaseActivity().getFragmentManager().beginTransaction().remove(map).commit();
        }
    }


}
