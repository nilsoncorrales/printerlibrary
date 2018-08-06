package printer.com.citectra.printerlibrary.entities;


import printer.com.citectra.myprinter.constantes.Constantes;
import printer.com.citectra.myprinter.interfaces.ILinea;
import printer.com.citectra.myprinter.utils.FuncionesPrint;


/**
 * Created by innovaapps on 7/4/2016.
 */
public class LineaDoblePrint extends AbstractEntity implements ILinea
{
    String linea01 = "";
    String linea02 = "";
    public final static int ID = Constantes.IDLINEADOBLE;

    public LineaDoblePrint(String linea01, String linea02)
    {
        super(ID);
        this.linea01 = linea01;
        this.linea02 = linea02;
    }

    public String getLinea() throws Exception
    {
        return new FuncionesPrint().getLineaSimple(linea01, linea02, true); // true = isDoble
    }

    @Override
    public boolean isNegrita() {
        return false;
    }

}
