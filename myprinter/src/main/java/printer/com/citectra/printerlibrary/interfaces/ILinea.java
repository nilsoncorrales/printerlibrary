package printer.com.citectra.printerlibrary.interfaces;

/**
 * Created by innovaapps on 7/4/2016.
 */
public interface ILinea
{
    int getID();
//    boolean isDoble();
    boolean isNegrita();
//    boolean isCenter();
    String getLinea() throws Exception;
}
