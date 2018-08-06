package printer.com.citectra.myprinter.entities;
import printer.com.citectra.myprinter.constantes.Constantes;
import printer.com.citectra.myprinter.interfaces.ILinea;
import printer.com.citectra.myprinter.utils.FuncionesPrint;

/**
 * Created by innovaapps on 7/4/2016.
 */
public class LineaCenterPrint extends AbstractEntity implements ILinea
{
    String linea01 = "";
    boolean isNegrita = false;
    public final static int ID = Constantes.IDLINEACENTER;

    public LineaCenterPrint(String linea01)
    {
        super(ID);
        this.linea01 = linea01;
    }

    public LineaCenterPrint(String linea01, boolean isNegrita) {
        super(ID);
        this.linea01 = linea01;
        this.isNegrita = isNegrita;
    }

    // cadena -> kenny
    // |      kenny       |
    public String getLinea() throws Exception
    {
        return new FuncionesPrint().getLineaCabecera(linea01);
    }

    @Override
    public boolean isNegrita() {
        return isNegrita;
    }

}
