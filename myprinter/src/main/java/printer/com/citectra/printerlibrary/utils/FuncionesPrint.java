package printer.com.citectra.printerlibrary.utils;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by innovaapps on 7/4/2016.
 */
public class FuncionesPrint
{
//    public static final int TAMANIO_F310 = 42;
//    public static final int TAMANIO_F312 = 48;
//    public static final int TAMANIO_AP02 = 32;
    public static final int TAMANIO_AP02 = 42;

    public FuncionesPrint(){}

    /**
     * Simple función que genera una linea simple , Texto Simple o Double
     * @param inicioLinea String
     * @param finalLinea String
     * @param isTextDouble String
     * @return String
     */
    public String getLineaSimple(String inicioLinea, String finalLinea, boolean isTextDouble)  throws Exception
    {
        return getLinea(inicioLinea, finalLinea, isTextDouble, false);
    }

    /**
     * Simple función que genera la línea de la cabecera...
     * @param linea String
     * @return String
     */
    public String getLineaCabecera(String linea) throws Exception
    {
        return getLinea(linea, "", false, true);
    }

    /**
     * Función que retornará una línea como subraya
     * para las cabeceras de las columnas de las tablas.
     * @return String con un length() de TAMANIO_LINEA : 42
     */
    public String getLineaSubraya(int tipoLinea) throws Exception
    {
        String caracter = "-"; // Default != 2
        if (tipoLinea == 2)
            caracter    = "=";
        return getLinea(getLineaCaracter(caracter, FuncionesPrint.TAMANIO_AP02), "", false, true);
    }

    /**
     * Simple función que retornará una línea
     * con un carácter en especial. (-)
     * @param caracter String
     * @param limite int capacidad de cadena 42/21
     * @return String línea
     */
    private String getLineaCaracter(String caracter, int limite)
    {
        StringBuilder strLinea = new StringBuilder(limite);
        for (int i = 0; i < limite; i++)
        {
            strLinea.append(caracter);
        }
        return strLinea.toString();
    }

    /**
    *  @param inicioLinea  String que irá al lado izquierdo de la cadena final.
    *  @param finalLinea   String que irá al lado derecho de la cadena final.
    *  @param isTextDouble boolean
    **/
    private String getLinea(String inicioLinea, String finalLinea, boolean isTextDouble, boolean isCabecera) throws Exception
    {
        StringBuilder str = new StringBuilder();
        str.append(String.format("%"+ FuncionesPrint.TAMANIO_AP02 + "s", " ")); // 32 | 42
        int strSize = str.length();

        if(inicioLinea.length() == 0 && finalLinea.length() > 0 && !isTextDouble  && !isCabecera)
        {
            str.replace(strSize - finalLinea.length(), strSize, finalLinea);
        }
        else if (inicioLinea.length() > 0 && finalLinea.length() > 0 && !isTextDouble  && !isCabecera)
        {   // ARMA CADENA SIMPLE CON DOS TEXTOS EN DIFERENTE       : ALINEACIÓN (LEFT - RIGTH)
            str.replace(0, inicioLinea.length(), inicioLinea);
            str.replace(strSize - finalLinea.length(), strSize, finalLinea);
        }
        else if (inicioLinea.length() > 0 && !isTextDouble && isCabecera)    // Ajustar texto al centro
        {
            int tamanioLinea    = inicioLinea.length();
            if (tamanioLinea > strSize)
                return "";
            int margen          = (strSize - tamanioLinea) / 2;  // Dará 0 si la cadena tiene un length de 42 - Sin Problema
            str.replace(margen, str.length() - margen, inicioLinea);
        }
        else if (inicioLinea.length() > 0 && finalLinea.length() > 0 && isTextDouble && !isCabecera)
        {
            str     = new StringBuilder();
            str.append(String.format("%" + FuncionesPrint.TAMANIO_AP02/2 + "s", " "));
            strSize = str.length(); // 21

            str.replace(0, inicioLinea.length(), inicioLinea);
            str.replace(strSize - finalLinea.length(), strSize, finalLinea);
        }else if (inicioLinea.length() > 0 && finalLinea.length() == 0 && !isTextDouble && !isCabecera )
        {
            str.replace(0, inicioLinea.length(), inicioLinea);      // texto simple al lado izquierdo - derecho vacío
        }
        return str.toString();
    }

    public String castNumeroSerie(int numeracion)throws Exception
    {
        return this.castNumeracion(8, String.valueOf(numeracion));
    }

    public String castIdDispositivo(int idDispositivo)throws Exception
    {
        return this.castNumeracion(4, String.valueOf(idDispositivo));
    }

    /**
     * método auxiliar para castear un string (num) con ceros '0'
     * @param num {@link String} num bateria
     * @param limite int
     * @return String
     */
    public String castNumConCeros(String num, int limite)
    {
        return this.castNumeracion(limite, num);
    }


    /**
     * Simple función que devolverá una cadena proporcional
     * partiendo de un Array simple.
     * @param elementos String[]
     * @return String Línea Tabla.
     */
    public String getLineaTabla(String[] elementos)throws Exception
    {
        StringBuilder str = new StringBuilder();
        final int tamanioPrimeraColumna = 2;
        final int capacidadProporcional = FuncionesPrint.TAMANIO_AP02  - tamanioPrimeraColumna;  // línea sin la primera columna(solo la primera no es proporcional..) ..
        final int tamanioProporcional   = capacidadProporcional / (elementos.length - 1);
        int mod = capacidadProporcional % (elementos.length - 1);   // mod que irá agregandose en los loops para ocupar toda la línea.

        for (int i = 0; i < elementos.length; i++)
        {
            int tamanio = tamanioPrimeraColumna;
            if (i > 0)
            {
                tamanio = tamanioProporcional + ((mod > 0) ? 1 : 0 );
                mod = mod - 1;
            }
            str.append(String.format("%" + tamanio + "s", elementos[i]));
        }
        return str.toString();
    }


    /**
     * Simple función para generar x lineas divididas
     * de un texto largo.
     * @param datos String
     * @return String Línea Tabla.
     */
    public ArrayList<String> getLineasCentricas(String datos)throws Exception
    {
        final int tamanioNormalPorLinea = 30;
        ArrayList<String> alLineas = new ArrayList<String>();
        int posAnterior = 0;
        ArrayList<String> alDatos = new ArrayList<String>(Arrays.asList(datos.split("")));
        alDatos.remove(0);
        do
        {
            int posCaracter = tamanioNormalPorLinea + posAnterior;
            do
            {
                if (posCaracter >= alDatos.size() -1)
                    break;
                posCaracter ++;
            }while (alDatos.size()-1 > posCaracter && alDatos.get(posCaracter) != null && !alDatos.get(posCaracter).equals(" "));

            if (alDatos.size() >= posCaracter && alDatos.get(posCaracter).equals(" "))
                alLineas.add(datos.substring(posAnterior, posCaracter));
            if (posCaracter >= alDatos.size()-1)
            {
                alLineas.add(datos.substring(posAnterior, datos.length()));
                alDatos = new ArrayList<String>();
            }
            posAnterior = posCaracter;
        }while (alDatos.size() > 0);
        return alLineas;
    }



    /**
     * simple función base para los castin de numeraciones de códigos..
     * @param limite int
     * @param numeracion String
     * @return String
     */
    private String castNumeracion(int limite, String numeracion)//throws Exception
    {
        if (numeracion.length() > limite)
            return numeracion;

        StringBuilder str = new StringBuilder();
        str.append(this.getLineaCaracter("0", limite));
        return (str.replace(str.length() - numeracion.length(), str.length(), numeracion)).toString();
    }


    public String getDobleLineaSubraya()throws Exception
    {
        return this.getLinea(this.getLineaCaracter("=", FuncionesPrint.TAMANIO_AP02), "", false, true);
    }

    public String getLineaSubraya()throws Exception
    {
        return this.getLinea(this.getLineaCaracter("-", FuncionesPrint.TAMANIO_AP02), "", false, true);
    }


}
