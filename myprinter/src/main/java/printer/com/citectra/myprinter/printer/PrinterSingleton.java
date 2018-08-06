package printer.com.citectra.myprinter.printer;

import android.content.Context;

public class PrinterSingleton {

    private static Printer printer = null;
    private static Context context = null;

    /**
     * Set de contexto global
     * *uso necesario
     * @param context {@link Context}
     */
    public static void setContext(Context context)
    {
        PrinterSingleton.context = context;
    }

    /**
     * retorna instancia printer
     * @return
     */
    public static Printer getPrinter() {
//        if(PrinterSingleton.context == null)
//            throw new Exception("contexto null");
        if(printer == null)
            printer = new Printer(PrinterSingleton.context);
        return printer;
    }

    public static void setPrinter(Printer printer) {
        PrinterSingleton.printer = printer;
    }
}
