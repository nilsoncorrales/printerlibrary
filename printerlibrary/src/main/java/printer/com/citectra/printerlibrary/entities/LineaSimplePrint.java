package printer.com.citectra.printerlibrary.entities;


import printer.com.citectra.myprinter.constantes.Constantes;
import printer.com.citectra.myprinter.interfaces.ILinea;
import printer.com.citectra.myprinter.utils.FuncionesPrint;

/**
 * Created by innovaapps on 7/4/2016.
 */
public class LineaSimplePrint extends AbstractEntity  implements ILinea
{
    String linea01 = "";
    String linea02 = "";
    boolean isNegrita = false;

    public final static int ID = Constantes.IDLINEASIMPLE;

    public LineaSimplePrint(String linea01, String linea02) {
        super(ID);
        this.linea01 = linea01;
        this.linea02 = linea02;
    }

    public LineaSimplePrint(String linea01, String linea02, boolean isNegrita) {
        super(ID);
        this.linea01 = linea01;
        this.linea02 = linea02;
        this.isNegrita = isNegrita;
    }

    public String getLinea() throws Exception
    {
        return new FuncionesPrint().getLineaSimple(linea01, linea02, false);
    }

    @Override
    public boolean isNegrita() {
        return isNegrita;
    }

}
