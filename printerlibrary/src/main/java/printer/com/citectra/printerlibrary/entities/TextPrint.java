package printer.com.citectra.printerlibrary.entities;

import printer.com.citectra.printerlibrary.interfaces.ILinea;
import printer.com.citectra.printerlibrary.printer.MODO;

public class TextPrint {

    ILinea iLinea;
    MODO modo = MODO.DEFAULT;

    public TextPrint(ILinea iLinea) {
        this.iLinea = iLinea;
    }

    public TextPrint(ILinea iLinea, MODO modo) {
        this.iLinea = iLinea;
        this.modo = modo;
    }

    public ILinea getiLinea() {
        return iLinea;
    }

    public MODO getModo() {
        return modo;
    }
}
