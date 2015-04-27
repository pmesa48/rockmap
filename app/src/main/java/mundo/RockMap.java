package mundo;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import geometria.Point;
import geometria.Poligono;

public class RockMap
{

    private ArrayList<Ruta> rutasPorHacer;
    private TreeMap<String, ArrayList<Ruta>> rutasSistema;
    private ArrayList<Ruta> rutasRealizadas;
    private ArrayList<Ruta> rutasConsultadas;
    private Ruta rutaAVer;

    private ArrayList<Parque> parques;

    private TreeMap<String, ArrayList<String>> tablaDificultades;
    private String modoSeleccionado;

    private String[] encabezadosDificultad;
    private String numeroVSdificultad[];

    /**
     * Archivo con el contenido a cargar
     */
    private InputStream archivoContenido;

    /**
     * Instancia de RockMap
     */
    private static RockMap instancia;

    /**
     * Manejado de la base de datos
     */
    private DBManager db;

    /**
     * Contexto de la aplicacion
     */
    private Context ctx;

    /**
     * Array que determina en cada posicion una dificultad
     */
    private String[] niveles;


    /**
     * Devuelve la instancia del mundo
     * @param ctx contexto de la aplicacion
     * @return instacia actual del mundo
     */
    public static RockMap darInstancia(Context ctx)
    {
        if(instancia==null) instancia=new RockMap(ctx);
        return instancia;
    }

    /**
     * Constructor del mundo
     * @param ctx contexto de la aplicacion
     */
    public RockMap(Context ctx)
    {
        parques=new ArrayList<Parque>();
        rutasPorHacer = new ArrayList<Ruta>();
        rutasRealizadas = new ArrayList<Ruta>();
        rutasSistema=new TreeMap<String, ArrayList<Ruta>>();
        tablaDificultades=new TreeMap<String,ArrayList<String>>();
        this.ctx = ctx;
        db = new DBManager(ctx);
        //numeroVSdificultad=new String[...]; mejor inicilaizo en cargarDificultades
    }

    /**
     * Devuelve un arrayList con las rutas por hacer
     * @return arrayList con las rutas por hacer
     */
    public ArrayList<Ruta> getRutasPorHacer( )
    {
        return rutasPorHacer;
    }

    /**
     * Devuelve un arrayList con las rutas realizadas
     * @return arrayList con las rutas realizadas
     */
    public ArrayList<Ruta> getRutasRealizadas( )
    {
       return rutasRealizadas;
    }

    /**
     * Devuelve los parques de la aplicacion
     * @return parques en un arrayList
     */
    public ArrayList<Parque> getParques() {
        return parques;
    }

    /**
     * Obtiene el modo seleccionado de medicion
     * @return modo seleccionado
     */
    public String getModoSeleccionado() {
        return db.darModoActual();
    }

    /**
     * Cambia el modo seleccionado de medicion
     * @param modoSeleccionado es uno de los modos permitidos por al app
     */
    public void setModoSeleccionado(String modoSeleccionado) {
        this.modoSeleccionado = modoSeleccionado;
        convertirArreglo();
        db.cambiarModo(modoSeleccionado);
    }

    /**
     * Carga contenido predeterminado a la base de datos, solo usar en debug
     * @param arch archivo que se desea leer con el formato presentado en el SAD
     * @throws Exception si hay algun error leyendo el archivo
     */
    public void cargarContenido(InputStream arch) throws Exception
    {
        Log.i("MIRAR","Inicializar carga de contenido");
        archivoContenido=arch;
        BufferedReader br=new BufferedReader(new InputStreamReader(archivoContenido));
        Log.i("MIRAR","Encontro el archivo");

        //Estructuras de datos para procesar la entrada
        int numeroDeParques=0, numeroDeZonas=0, numeroDeRutas=0, numeroDePuntos=0, i=0, j=0;
        double xi=0, yi=0, x1=0, y1=0, x2=0, y2=0, popularidadParque=0, popularidadRuta=0, altura=0;
        String imagen="", nombreParque="", nombreZona="", nombreRuta="", dificultad="", tipoEscalada="", aux[], aux2[];
        Point[] puntos;
        Poligono poligono;
        Parque esteParque;
        Zona estaZona;
        Ruta estaRuta;

        //-----------------INIT leer archivo---------------
        numeroDeParques=Integer.parseInt(br.readLine());
        Log.i("MIRAR","Numero de Parques: "+numeroDeParques);
        while(numeroDeParques--!=0)// Crea un nuevo parque
        {
            // Lee la descricion del parque
            aux=br.readLine().split(":");
            nombreParque=aux[0];
            popularidadParque=Double.parseDouble(aux[1]);
            numeroDePuntos=Integer.parseInt(aux[2]);
            puntos=new Point[numeroDePuntos];

            for(i=3; i<aux.length; i++)
            {
                aux2=aux[i].split(",");
                xi=Double.parseDouble(aux2[0]);
                yi=Double.parseDouble(aux2[1]);
                puntos[i-3]=new Point(xi, yi);
            }
            poligono=new Poligono(puntos);
            esteParque=new Parque(nombreParque, popularidadParque,poligono);
            db.agregarParque(nombreParque,(int)popularidadParque);

            Log.i("MIRAR","Parque creado: "+esteParque);

            numeroDeZonas=Integer.parseInt(br.readLine());
            Log.i("MIRAR","Numero de zonas: "+numeroDeZonas);

            while(numeroDeZonas--!=0)
            {
                aux=br.readLine().split(":");
                nombreZona=aux[0];
                x1=Double.parseDouble(aux[1]);
                y1=Double.parseDouble(aux[2]);
                x2=Double.parseDouble(aux[3]);
                y2=Double.parseDouble(aux[4]);
                estaZona=new Zona(nombreZona,new Point(x1,y1),new Point(x2,y2),esteParque);
                db.agregarZona(nombreParque,nombreZona);
                Log.i("MIRAR","Zona creada: "+estaZona);

                numeroDeRutas=Integer.parseInt(br.readLine());
                while(numeroDeRutas--!=0)
                {
                    aux=br.readLine().split(":");
                    nombreRuta=aux[0];
                    imagen=aux[1];
                    popularidadRuta=Double.parseDouble(aux[2]);
                    dificultad=aux[3];
                    tipoEscalada=aux[4];
                    altura=Double.parseDouble(aux[5]);
                    estaRuta=new Ruta(nombreRuta,imagen,dificultad,popularidadRuta,tipoEscalada,altura,estaZona);
                    db.agregarRuta(nombreZona,nombreRuta,imagen,dificultad,(int)altura,"roca");
                    estaZona.agregarUnaRuta(estaRuta);

                    ArrayList<Ruta> rutasConEsaDific= rutasSistema.get(dificultad);
                    if(rutasConEsaDific==null) rutasConEsaDific=new ArrayList<Ruta>();
                    rutasConEsaDific.add(estaRuta);
                    rutasSistema.put(dificultad,rutasConEsaDific);
                }
                esteParque.agregarZona(estaZona);
            }
            parques.add(esteParque);
        }
        br.close();
        Log.i("MIRAR","finalizo carga "+getParques().get(0));
        Log.i("MIRAR","las rutas en el sistema son"+rutasSistema);
    }

    /**
     * Carga las dificultades iniciales del sistema
     * @param arch archivo con las dificultades, leer SAD para ver formato
     * @throws Exception si hay problemas leyendo el archivo
     */
    public void cargarDificultades(InputStream arch) throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(arch));
        db.agregarTiposDificultades();
        String linea = br.readLine();
        linea = br.readLine();
       while( linea != null )
       {
            String[] dif = linea.split(";");
           db.agregarRegistroDificultades(dif);
           linea = br.readLine();

       }

        br.close();
    }

    /**
     * Metodo que permite ver la dificultad correspondiente al nivel escogido por la barra del usuario
     * @param nivel nivel que determina que dificultad desea el usuario
     * @return la dificultad correspondiente al nivel
     */
    public String verDificultadSeleccionada(int nivel)
    {
        convertirArreglo();
        return niveles[nivel];
    }


    /**
     * Devuelve las dificultades del sistema por sistema de medicion
     * @return sistemas de mediciones de dificultad
     */
    public Cursor darDificultades()
    {
        return db.darDificultades();
    }

    /**
     * Devuelve todos los parques ingresados en el sistema
     * @return parques ingresados
     */
    public Cursor darParques()
    {
        return db.darParques();
    }

    /**
     * Convierte la informacion de base de datos a un ArrayList que permita ver las dificultades
     * por modo
     */
    public void convertirArreglo()
    {
        niveles = db.darDificultadesPorModo(modoSeleccionado);
    }

    /**
     * Metodo para busqueda de rutas
     * @param min dificultad minima
     * @param max dificultad maxima
     * @param altura altura de la ruta
     * @param parqueNombre nombre del parque
     * @return informacion de las rutas en base de datos
     */
    public Cursor busqueda(int min, int max, int altura, String parqueNombre)
    {
        modoSeleccionado = getModoSeleccionado();
        convertirArreglo();
        return db.buscarRutasPorParam(niveles[min],niveles[max],String.valueOf(altura),parqueNombre);
    }


    /**
     * Agrega una ruta a las rutas por hacer
     * @param nombre nombre de la ruta a agregar
     */
    public void agregarRutaPorHacer( String nombre )
    {
        db.agregarRutaARutasPorHacer(nombre);
    }

    /**
     * Lista las rutas por hacer
     * @return rutas por hacer
     */
    public Cursor darRutasPorHacer()
    {
        return db.buscarRutasPorHacer();
    }

    /**
     * Agrega una ruta a la base de datos
     * @param imagen path de la imagen de la ruta
     * @param altura altura de la ruta
     * @param zona zona correspondiente a la ruta (por ahora es el mismo parque)
     * @param parque parque donde pertenece la ruta
     * @param dificultad dificultad de la ruta
     * @param p1 latitud del punto de inicio
     * @param p2 longitud del punto de inicio
     * @param p3 latitud del punto final
     * @param p4 longitud del punto final
     * @param pais pais donde pertenece el parque
     * @param nombreRuta nombre de la ruta
     */
    public void agregarRuta(String imagen, int altura, String zona, String parque, String dificultad, double p1, double p2, double p3, double p4, String pais, String nombreRuta)
    {
        db.subirImagen(imagen,altura,zona,parque,dificultad,p1,p2,p3,p4,pais,nombreRuta);
    }

    /**
     * Retorna las dificultades correspondientes al modo seleccionado
     * @return array de strings con las dificultades correspondientes
     */
    public String[] darDificultadesDelModoActual()
    {
        return db.darDificultadSegunModoActual();
    }


    /**
     * Lista todas las rutas del sistema
     * @return todas las rutas
     */
    public ArrayList<Ruta> darRutas()
    {
        return db.darRutas();
    }


    /**
     * Agrega un parque al sistema
     * @param nombre nombre del parque
     * @param pais pais pais donde pertenece el parque
     * @param latitude latitud del parque
     * @param longitude longitud del parque
     */
    public void agregarParque(String nombre, String pais, double latitude, double longitude)
    {
        db.agregarParque(nombre,pais,latitude,longitude);
    }

    /**
     * Retorna una lista con los parques para presentar en el mapa
     * @return lista con parques
     */
    public ArrayList<Parque> darParquesParaMapa()
    {
        return db.darParquesParaMapa();
    }

    /**
     * Metodo para agregar un parque proveniente del webservice
     * @param zona zona zona correspondiente a la ruta (por ahora es el mismo parque)
     * @param parque parque donde pertenece la ruta
     * @param imagen path de la imagen de la ruta
     * @param dificultad dificultad de la ruta
     * @param altura altura de la ruta
     * @param p1 latitud del punto incial
     * @param p2 longitud del punto inciial
     * @param p3 latitud del punto final
     * @param p4 longitud del punto final
     * @param pais pais pais donde pertenece el parque
     * @param nombre nombre de la ruta a agregar
     */
    public void agregarRutaPorWebService(String zona, String parque, String imagen, String dificultad,
                                         int altura, float p1, float p2, float p3, float p4, String pais, String nombre)
    {
        db.agregarRutaPorWebService(zona,parque,imagen,dificultad,altura,p1,p2,p3,p4,pais,nombre);
    }


    /**
     * Retorna la ruta de la imagen segun su nombre
     * @param nombre nombre de la ruta
     * @return path de la imagen
     */
    public String darImagenRutaPorNombre(String nombre)
    {
        return db.darImagenDeRutaPorNombre(nombre);
    }


    /**
     * Metodo auxiliar para limpiar la base de datos
     */
    public void removeAll()
    {
        db.removeAll();
    }

    /**
     * Metodo auxilizar para crear la base de datos
     */
    public void createAll()
    {
        db.createAll();
    }
}
