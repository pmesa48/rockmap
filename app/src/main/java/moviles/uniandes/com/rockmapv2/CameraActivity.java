package moviles.uniandes.com.rockmapv2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;


public class CameraActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private File imagen;

    private String nombre;

    private String parque;

    private String dificultad;

    private String altura;

    private boolean creado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        try
        {
            savedInstanceState.getBoolean("created");
            if( imagen.exists()) {
                Log.i("FragmentAgregar", imagen.getAbsolutePath());

                Log.i("CAMERAACTIVITY", "IMAGEN EXISTE");


                Log.i("CAMERAACTIVITY", "PARAMS EXISTEN");

                Intent agregarPuntos = new Intent(this, AgregarRutaActivity.class);
                agregarPuntos.putExtra("nombre", nombre);
                agregarPuntos.putExtra("dificultad", dificultad);
                agregarPuntos.putExtra("pais", "Colombia");
                agregarPuntos.putExtra("parque", parque);
                agregarPuntos.putExtra("imagen", imagen.getAbsolutePath());
                try {
                    agregarPuntos.putExtra("altura", Integer.parseInt("altura"));
                    startActivity(agregarPuntos);
                } catch (Exception err) {
                }
            }
        }
        catch (Exception e)
        {
            Intent fragmentAgregar = getIntent();

            nombre = fragmentAgregar.getStringExtra("nombre");
            parque = fragmentAgregar.getStringExtra("parque");
            dificultad = fragmentAgregar.getStringExtra("dificultad");
            altura = fragmentAgregar.getStringExtra("altura");
            Log.i("CameraNull", nombre);
            if (nombre != null && !nombre.equals("")) {
                String fileName = nombre + ".jpg";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagen = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
                Log.i("CAMERAACTIVITY", imagen.getAbsolutePath());
                Uri uri = Uri.fromFile(imagen);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    Log.i("CAMERAACTIVITY", "CALLBACK 11EXITOSO");
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Log.i("CAMERAACTIVITY", "CALLBACK 11EXITOSOppp");

                }
        }


            }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("created",true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("CAMERAACTIVITY","CALLBACK EXITOSO");
        if( requestCode == REQUEST_IMAGE_CAPTURE )
        {
            Log.i("CAMERAACTIVITY","REQUEST EXITOSO");

            if( resultCode == Activity.RESULT_OK)
            {
                Log.i("CAMERAACTIVITY","RESULT EXITOSO");

                if( imagen == null )
                {
                    Log.i("CAMERAACTIVITY","imagen null");
                }
                else if( imagen.exists())
                {
                    Log.i("FragmentAgregar",imagen.getAbsolutePath());

                    Log.i("CAMERAACTIVITY","IMAGEN EXISTE");




                    Log.i("CAMERAACTIVITY","PARAMS EXISTEN");

                    Intent agregarPuntos = new Intent(this, AgregarRutaActivity.class);
                    agregarPuntos.putExtra("nombre", nombre);
                    agregarPuntos.putExtra("dificultad", dificultad);
                    agregarPuntos.putExtra("pais","Colombia");
                    agregarPuntos.putExtra("parque", parque);
                    agregarPuntos.putExtra("imagen", imagen.getAbsolutePath());
                    try {
                        agregarPuntos.putExtra("altura", Integer.parseInt("altura"));
                        startActivity(agregarPuntos);
                    }
                    catch (Exception e) {
                    }


                    /*if( nh.isNetworkAvailable( ) )
                    {
                        ws.conectarWS();
                        try
                        {
                            ws.subirImagen(imagen,alturaRuta,parque,parque,dificultad,p1,p2,p3,p4,pais,nombreRuta);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getActivity(), "No se pudo conectar con el servicio", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No hay conexión a internet, la imagén se guardará en el dispositivo", Toast.LENGTH_LONG).show();
                    }*/




                }
                else
                {
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();

                }
            }
        }

    }

}
