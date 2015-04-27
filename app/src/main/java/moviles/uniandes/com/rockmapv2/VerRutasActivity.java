package moviles.uniandes.com.rockmapv2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import mundo.DBManager;
import mundo.RockMap;

public class VerRutasActivity extends Activity
{
    private RockMap mundo;

    private SimpleCursorAdapter adapter;
    private DBManager manager;
    private Cursor cursor;
    private ListView listView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mundo = new RockMap(this);
        setContentView(R.layout.activity_ver_rutas);
        intent = getIntent();
        manager = new DBManager(this);
        listView = (ListView) findViewById(R.id.listView);



        String[] from = new String[]{DBManager.CN_RUTA_NOMBRE, DBManager.CN_RUTA_DIFICULTAD};
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};

        String buscar = intent.getStringExtra("buscar");
        if( buscar != null && buscar.equals("si")) {
            cursor = mundo.busqueda(intent.getIntExtra("ProgressMin", 0), intent.getIntExtra("ProgressMax", 0),
                    intent.getIntExtra("altura", 0), intent.getStringExtra("sp"));
            Log.i("BUSCAR","si");
        }
        else
        {
            Log.i("BUSCAR","no");

            cursor = mundo.darRutasPorHacer();
        }

        adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,cursor,from,to,0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor)parent.getItemAtPosition(position);
                String item = parent.getItemAtPosition(position).toString();
                Log.i("IMPORTANTE",item);

                String nombre = c.getString(c.getColumnIndex("nombre"));
                String dificultad = c.getString(c.getColumnIndex("dificultad"));
                Log.i("IMPORTANTE","Desplegando intent de ruta individual");
                Intent intent = new Intent(view.getContext(), RutaIndividualActivity.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("dificultad",dificultad);
                intent.putExtra("altura","50");
                intent.putExtra("agregar","noagregar");
                startActivity(intent);
            }
        });

    }
}
               /* Cursor c = (Cursor)listView.getSelectedItem();

                String nombre = c.getString(c.getColumnIndex("nombre"));
                String dificultad = c.getString(c.getColumnIndex("dificultad"));
                Log.i("IMPORTANTE","Desplegando intent de ruta individual");
                Intent intent = new Intent(view.getContext(), RutaIndividualActivity.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("dificultad",dificultad);
                intent.putExtra("altura","50");
                startActivity(intent);*/