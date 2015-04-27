package mundo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Pablo Mesa on 11/03/2015.
 */
public class DBHelper extends SQLiteOpenHelper{


    /**
     * Nombre de la base de datos
     */
    public final static String DB_NAME = "rockmap.sqlite";

    /**
     * Version del esquema
     */
    private final static int DB_SCHEME_VERSION = 1;

    /**
     * Contexto de la aplicacion
     */
    private Context context;

    /**
     * Instancia de RockMap
     */
    private RockMap mundo;

    /**
     * Constructor del helper de la base de datos
     * @param context contexto de la aplicacion
     */
    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_SCHEME_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {


        db.execSQL(DBManager.CREATE_TABLE_PARQUES);
        db.execSQL(DBManager.CREATE_TABLE_RUTAS);
        db.execSQL(DBManager.CREATE_TABLE_ZONAS);
        db.execSQL(DBManager.CREATE_TABLE_MISRUTASPORHACER);
        db.execSQL(DBManager.CREATE_TABLE_RUTASREALIZADAS);
        db.execSQL(DBManager.CREATE_TABLE_DIFICULTADES);
        db.execSQL(DBManager.CREATE_TABLE_TIPOS_DIFICULTADES);
        db.execSQL(DBManager.CREATE_TABLE_MODE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("ALTER TABLE parques ADD COLUMN latitude REAL DEFAULT 0");
            db.execSQL("ALTER TABLE parques ADD COLUMN longitude REAL DEFAULT 0");
    }


    /**
     * Destruye la base de datos
     * @param db base de datos
     */
    public void removeAll(SQLiteDatabase db)
    {
        db.delete("rutas",null,null);
        db.delete("zonas",null,null);
        db.delete("parques",null,null);
        db.delete("rutasporhacer",null,null);
        db.delete("rutasrealizadas",null,null);
        db.delete("dificultades",null,null);
        db.delete("tipos_dificultades",null,null);
        db.delete("modo",null,null);
    }


    /**
     * Construye la base de datos
     * @param db base base de datos
     */
    public void createAll(SQLiteDatabase db)
    {

        db.execSQL(DBManager.CREATE_TABLE_PARQUES);
        db.execSQL(DBManager.CREATE_TABLE_RUTAS);
        db.execSQL(DBManager.CREATE_TABLE_ZONAS);
        db.execSQL(DBManager.CREATE_TABLE_MISRUTASPORHACER);
        db.execSQL(DBManager.CREATE_TABLE_RUTASREALIZADAS);
        db.execSQL(DBManager.CREATE_TABLE_DIFICULTADES);
        db.execSQL(DBManager.CREATE_TABLE_TIPOS_DIFICULTADES);
        db.execSQL(DBManager.CREATE_TABLE_MODE);
    }

}
