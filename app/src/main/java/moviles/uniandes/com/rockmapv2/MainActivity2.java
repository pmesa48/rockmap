package moviles.uniandes.com.rockmapv2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import java.io.File;


public class MainActivity2 extends FragmentActivity
{
    ActionBar.Tab tabBuscar, tabRutas, tabAgregar, tabGPS;

    final Fragment fragmentBuscar = new FragmentBuscar();
    final Fragment fragmentRutas = new FragmentRutas();
    final Fragment fragmentAgregar = new FragmentAgregar();
    final Fragment fragmentGPS = new FragmentGPS();

    private FragmentTabHost mTabHost;



    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();

       /* actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,ActionBar.DISPLAY_SHOW_CUSTOM);*/

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

/*        tabBuscar = actionBar.newTab().setIcon(getResources().getDrawable(R.drawable.buscar));
        tabRutas = actionBar.newTab().setIcon(getResources().getDrawable(R.drawable.formatlistbulleted));
        tabAgregar = actionBar.newTab().setIcon(getResources().getDrawable(R.drawable.camera));
        tabGPS = actionBar.newTab().setIcon(getResources().getDrawable(R.drawable.earth));

        tabBuscar.setTabListener(new TabListener(fragmentBuscar));
        tabRutas.setTabListener(new TabListener(fragmentRutas));
        tabAgregar.setTabListener(new TabListener(fragmentAgregar));
        tabGPS.setTabListener(new TabListener(fragmentGPS));

        actionBar.addTab(tabBuscar);
        actionBar.addTab(tabRutas);
        actionBar.addTab(tabAgregar);
        actionBar.addTab(tabGPS);*/
        TabWidget wd = (TabWidget)findViewById(android.R.id.tabs);
        wd.setBackgroundColor(Color.BLACK);



        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("", getResources().getDrawable(R.drawable.buscar)),
                FragmentBuscar.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("", getResources().getDrawable(R.drawable.formatlistbulleted)),
                FragmentRutas.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("", getResources().getDrawable(R.drawable.camera)),
                FragmentAgregar.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("", getResources().getDrawable(R.drawable.earth)),
                FragmentGPS.class, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("CAMERAACTIVITY","PADRE1");


        super.onActivityResult(requestCode, resultCode, data);



    }


}
