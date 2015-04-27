package mundo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pablomesa on 11/04/15.
 */
public class NetworkHelper {

    /**
     * Contexto de la aplicacion
     */
    private Context ctx;

    /**
     * Constructor del network helper
     * @param ctxP contexto de la aplicacion
     */
    public NetworkHelper(Context ctxP)
    {
        ctx = ctxP;
    }

    /**
     * Determina si la red esta disponible
     * @return true si esta disponible false en caso contrario
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
