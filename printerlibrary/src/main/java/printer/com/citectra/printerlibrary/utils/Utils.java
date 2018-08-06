package printer.com.citectra.printerlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import printer.com.citectra.myprinter.enums.IDUTILS;

/**
 * Created by innovaapps on 18/10/2016.
 */
public class Utils {

    private static final String TAG = "APPFRAMEWORK";
    private static final String INTREGEX    = "^([0-9]{1})+$";


//    public static void LOG(String texto)
//    {
//        if (BuildConfig.DEBUG)
//            Log.w(TAG, texto);
//    }
//    public static void LOG_E(String error)
//    {
//        if (BuildConfig.DEBUG)
//            Log.e(TAG, error);
//    }

    public void toast(String texto, Context context)
    {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
    }


    /**
     * Simple función para comprobar si la cadena recibida tiene
     * formato INT.
     * @param linea String que se aplicará matches.
     * @return TRUE si la cadena es INT, FALSE caso contrario..
     **/
    public boolean lineIsInt(String linea) {
        Pattern pattern = Pattern.compile(INTREGEX);
        Matcher matcher = pattern.matcher(linea);
        return matcher.matches();
//        return Pattern.matches(INTREGEX, linea);
    }

    /**
     * Simple función que retornará la versión del aplicativo..
     * @param context Contexto de la actividad actualizar..
     * @param indice int indice de petición
     * @return
     */


    public String getVersion(Context context, int indice)
    {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (pInfo == null)
                return "";
            if(indice == 1) // retorna numero de versión
                return pInfo.versionCode + "";
            else	// Retorna nombre de version
                return pInfo.versionName + "";
        } catch (PackageManager.NameNotFoundException e) {}
        catch (Exception e){}
        return "";
    }
    /**
     * Simple funcion que retornará el IMEI del
     * dispotivo..
     * @return String Imei
     */
    @SuppressLint("MissingPermission")
    public String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public int getBattery(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }

    /**
     *  FUNCIÓN AUXILIAR QUE DEVOLVERÁ LA FECHA Y HORA EN ALGUNOS FORMATOS
     *  @param idutils IDUTILS indice para que evalue que Formato se requiere.
     *  @return String Fecha Formateada.
     **/
    public String getFechaHora(IDUTILS idutils)
    {
        String formatoFecha  = (idutils == IDUTILS.HHMMSS) ? "HH:mm:ss" :
                (idutils == IDUTILS.DDHHMMSS) ? "dd/MM/yyyy HH:mm:ss" :
                (idutils == IDUTILS.DDHHMMSSSS) ?"dd/MM/yyyy HH:mm:ss.SSS" :
                (idutils == IDUTILS.DDMMAA) ?"dd/MM/yyyy" : "";
        return simpleDateFormat(formatoFecha).format(Calendar.getInstance().getTime());
    }




    /**
     * @param tPasado Time
     * @return long seconds
     */
    public long getDiferenciaInSeconds(Time tPasado)
    {
        Time tActual = new Time();
        tActual.setToNow();
        return TimeUnit.MILLISECONDS.toSeconds(tActual.toMillis(true) - tPasado.toMillis(true));
    }

    /**
     * Combinamos dos arreglos y retornamos un único arreglo
     * @param a Primer Objeto Arreglo   -
     * @param b Segundo Objeto Arreglo  -
     * @return String[]
     */
    public String[] unirArrayString(String[] a, String[] b)
    {
        int length          = a.length + b.length;
        String[] result     = new String[length];          // 3 FILAS / 3 COLUMNAS
        //        region SINTAXYS DEVELOPER ARRAYCOPY
        //         SINTAXYS DEVELOPER
        //         arraycopy (Object src, int srcPos, Object dst, int dstPos, int length)
        //         arraycopy ( matriz origén, indice a partir del contenido origén, matriz destino, índice a partir del contenido destino, número de elementos a copiar.
        //         endregion
        System.arraycopy(a, 0, result, 0, a.length);        // Copiamos la primera matriz
        System.arraycopy(b, 0, result, a.length, b.length); // Copiamos la segunda matriz
        return result;
    }

    /**
     * @param formatoFecha String  FormatoFecha
     * @return SimpleDateFormat con lenguaje ESPANIOL y País PERÚ
     **/
    private SimpleDateFormat simpleDateFormat(String formatoFecha)
    {
        return new SimpleDateFormat(formatoFecha, new Locale("es", "pe"));
    }

    public void pintarBackground(View view, Drawable drawable)throws Exception
    {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
            view.setBackgroundDrawable(drawable);
        else
            view.setBackground(drawable);
    }
}
