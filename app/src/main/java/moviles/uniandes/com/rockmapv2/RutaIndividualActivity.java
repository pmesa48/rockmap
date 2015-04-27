package moviles.uniandes.com.rockmapv2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import mundo.ImageLoadTask;
import mundo.RockMap;


public class RutaIndividualActivity extends Activity {

    private Button boton1;

    private Button boton2;

    private EditText nombre;

    private EditText altura;

    private EditText dificultad;

    private RockMap mundo;

    private ImageView imvRuta;

    private Bitmap myBitmap;

    private Bitmap scaled;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_individual);
        setTitle("Detalle Ruta");
        mundo = new RockMap(this);
        nombre = (EditText)findViewById(R.id.editText);
        altura = (EditText)findViewById(R.id.editText2);
        dificultad = (EditText)findViewById(R.id.editText3);

        Intent i = getIntent();

        String nombrep = i.getStringExtra("nombre");
        String alturap = i.getStringExtra("altura");
        String dificultadp = i.getStringExtra("dificultad");

        boton1 = (Button)findViewById(R.id.button);
        boton2 = (Button)findViewById(R.id.button2);
        boton1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("AGREGAR RUTA", "agregando ruta");
                    mundo.agregarRutaPorHacer(nombre.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                }
                catch(Exception e )
                {
                    e.printStackTrace();
                    Log.e("ERROR",e.getMessage());
                    Log.e("ERROR",e.getStackTrace().toString());
                }
            }
        });

        nombre.setText(nombrep);
        nombre.setEnabled(false);
        altura.setText(alturap);
        altura.setEnabled(false);
        dificultad.setText(dificultadp);
        dificultad.setEnabled(false);

        imvRuta = (ImageView)findViewById(R.id.imvRuta);

        if(myBitmap!=null)
        {
            myBitmap.recycle();
            myBitmap=null;
            scaled.recycle();
            scaled=null;
        }

        String rutaImagen = mundo.darImagenRutaPorNombre(nombrep);
        Log.i("RutaIndividual","ruta: " + rutaImagen);

        if( !rutaImagen.equals("") )
        {
            if( rutaImagen.contains("/"))
            {
                File imagen = new File(rutaImagen);
                if(imagen.exists())
                {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    myBitmap = BitmapFactory.decodeFile(rutaImagen, options);
                    if( myBitmap != null ) {
                        int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
                        scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
                        imvRuta.setImageBitmap(scaled);
                    }
                }
                else
                {
                    Log.i("RutaIndividual", "No existe imagen");
                }
            }
            else
            {
                new ImageLoadTask(getString(R.string.getImagesService) + "/" + rutaImagen, imvRuta, myBitmap, scaled).execute();
            }
        }
        else
        {
            imvRuta.setImageResource(R.drawable.montana);
        }




        String param = i.getStringExtra("agregar");
        if(param.equals("agregar"))
        {
            ViewGroup layout = (ViewGroup) boton2.getParent();
            if(null!=layout) //for safety only  as you are doing onClick
                layout.removeView(boton2);
        }
        else
        {
            ViewGroup layout = (ViewGroup) boton1.getParent();
            if(null!=layout) //for safety only  as you are doing onClick
                layout.removeView(boton1);
        }
    }

}
