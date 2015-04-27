package mundo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import moviles.uniandes.com.rockmapv2.AgregarParqueActivity;
import moviles.uniandes.com.rockmapv2.MapActivity;

/**
 * Created by pablomesa on 14/04/15.
 */
public class GPSHelper {

    /**
     * Location Manager de la app
     */
    private LocationManager locationManager;

    /**
     * Ubicacion del usuario
     */
    private Location location;

    /**
     * Esta hablitado el GPS
     */
    private boolean isGPSEnabled;

    /**
     * Esta habilitada la red
     */
    private boolean isNetworkEnabled;

    /**
     * Es posible determinar ubicacion
     */
    private boolean canGetLocation;

    /**
     * latitud del usuario
     */
    private double latitude;

    /**
     * longitud del usuario
     */
    private double longitude;

    /**
     * Contexto de la aplicacion
     */
    private Context context;

    /**
     * Actividad de mapas
     */
    private MapActivity mapActivity;

    /**
     * Instancia del helper de GPS
     */
    private static GPSHelper instancia;

    /**
     * Devuelve la instancia del GPS helper
     * @param ctx contexto de la aplicacion
     * @param map Actividad de mapas
     * @return instancia del GPSHelper
     */
    public static GPSHelper darInstancia(Context ctx,MapActivity map)
    {
        if( instancia == null )
            instancia = new GPSHelper(ctx,map);
        return instancia;
    }

    /**
     * Constructor del GPS helper
     * @param ctx contexto de la aplicacion
     * @param map actividad Actividad de mapas
     */
    public GPSHelper(Context ctx, MapActivity map)
    {
        context = ctx;
        mapActivity = map;
        darUbicacion();
    }

    /**
     * Retorna el LocationManager
     * @return devuelve el manager de ubicacion
     */
    public LocationManager getLocationManager() {
        return locationManager;
    }

    /**
     * Retorna la ubicacion actual
     * @return ubicacion actual del dispositivo
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Determina si esta habilitado el GPS
     * @return true si esta habilitado false en caso contrario
     */
    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    /**
     * Determina si esta habilitada la red
     * @return true si esta habilitada false en caso contrario
     */
    public boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    /**
     * Retorna la latitud actual
     * @return retorna la ultima latitud conocida
     */
    public double getLatitude() {
        if(location != null)
        {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * Retorna la longitud actual
     * @return retorna la ultima longitud conocida
     */
    public double getLongitude()
    {
        if(location != null )
            longitude = location.getLongitude();
        return longitude;
    }

    /**
     * Determina si se pudo encontrar una ubicacion
     * @return true si fue posible false en caso contrario
     */
    public boolean canGetLocation() {
        return canGetLocation;
    }

    /**
     * Retorna la ubicacion actual
     * @return objeto con la ubicacion del usuario
     */
    public Location darUbicacion()
    {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.i("GPS", "No hay servicio GPS");
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000*60,
                            10, mapActivity);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if
                            (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1000*60,
                                10, mapActivity);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Muestra alerta por GPS no activado
     */
    public void mostrarAlertaGPS()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS no activado");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("¿Desea activar el GPS?");
        alertDialog.setPositiveButton("Settings",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog dialog= alertDialog.create();
        dialog.show();
    }


}
