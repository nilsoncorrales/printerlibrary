package printer.com.citectra.printerlibrary.printer;

import java.util.ArrayList;

import printer.com.citectra.myprinter.entities.TextPrint;
import printer.com.citectra.myprinter.interfaces.ILinea;

/**
public interface IBixolon {
 void print(String linea, final BixolonHandler.MODO modo, boolean isNegrita); // modo estilo (negrita, )
 void printComplete(String linea, final BixolonHandler.MODO modo, boolean isNegrita, BixolonHandler.OnPrintResult onPrintResult); // optional   TODO : ESPERAR
 void cut(); // si es q hay algún método parecido.. TODO : NO HABILITADA
 void getStatus(BixolonHandler.OnStatusResult onStatusResult)throws Exception;
 void connect()throws Exception; // co
 void disconnect()throws Exception;
 }

 *
 */
public interface IPrinter {
    void print(ArrayList<ILinea> alILinea)throws Exception;
    void printTextPrint(ArrayList<TextPrint> alTextPrint)throws Exception;
    void getStatus(Printer.OnStatusCallback onStatusCallback);
    void connect();
    void disconnect();

}
