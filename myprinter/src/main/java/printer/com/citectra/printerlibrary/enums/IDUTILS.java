package printer.com.citectra.printerlibrary.enums;

/**
 * Created by innovaapps on 30/10/2015.
 */
public enum IDUTILS
{
    HHMMSS          (1),
    DDHHMMSS        (2),
    DDHHMMSSSS      (3),
    DDMMAA          (4),
    YYYYMMMDD       (5),
    HHMM            (6),

    //CLASES DE SEGUIMIENTO
    READER_APERTURA         (50),
    PRINTER_ELECTRONIC      (51),
    SERVICIO_LOCALIZACION   (52),
    BASE_SERVICIO           (53),
    SQLITE                  (54),
    BASE_SQLITE             (55),
    BIXOLON                 (56),
    MANAGER_BIXOLON         (57),



    // TIEMPO LIMITE PARA LOS ENVIOS DE DATAGRAMAS... SOLO SE NECESITARÁ SU VALOR, NO URGE DETALLES
//    TIEMPO_LIMITE_ENVIO_DATAGRAMA   (30),
    LIMITE_DE_VECES_PROCESO_ENVIO   (2),    // LÍMITE DE VECES QUE PUEDE TENER UN 'ENVÍO DE PROCESOS'
//    TIEMPO_FRECUENCIA_ENVIO_DATA_HUERFANA_CIERRE_CAJA(30),  // TIEMPO DE FRECUENCIA DE ENVIO DE DATAGRAMA DE TRANSACCIONES HUERFANAS
    TIEMPO_FRECUENCIA_ENVIO_DATA_GENERAL    (40),  // TIEMPO DE FRECUENCIA GENERAL PARA LOS ENVIO DE ENVIO DE DATAGRAMAS (APERTURAS/BLOQUES/CONFIGURACION/EXCEDIO_TIEMPO)
    LIMITE_DE_DIAS_DELETE_BOLETO    (-15),   // CANTIDAD DE DÍAS , QUE SE MANTENDRAN SIN ELIMINARSE...

    // ACCIÓN EDIT TEXT - PARA EL TECLADO VIRTUAL
    AGREGAR_TEXTO                   (20),
    BORRAR_TEXTO                    (21);

    private int n;

    /**
     * TIEMPO POR DEFAULT PARA LAS COMPROBACIONES DE DATA ALMACENADA...
     */
    private int TIEMPO_LIMITE_ENVIO = 30;

    IDUTILS(int n)
    {
        this.n = n;
    }
    public int getInt()
    {
        return n;
    }

    public String getString() {return String.valueOf(n);}
}

