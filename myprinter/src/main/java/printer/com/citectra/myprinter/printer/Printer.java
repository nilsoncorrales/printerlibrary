package printer.com.citectra.myprinter.printer;

import android.content.Context;
import android.util.Log;

import com.rt.printerlibrary.bean.SerialPortConfigBean;
import com.rt.printerlibrary.cmd.Cmd;
import com.rt.printerlibrary.cmd.EscFactory;
import com.rt.printerlibrary.connect.PrinterInterface;
import com.rt.printerlibrary.enumerate.ConnectStateEnum;
import com.rt.printerlibrary.enumerate.SettingEnum;
import com.rt.printerlibrary.factory.cmd.CmdFactory;
import com.rt.printerlibrary.factory.connect.PIFactory;
import com.rt.printerlibrary.factory.connect.SerailPortFactory;
import com.rt.printerlibrary.factory.printer.PrinterFactory;
import com.rt.printerlibrary.factory.printer.ThermalPrinterFactory;
import com.rt.printerlibrary.printer.RTPrinter;
import com.rt.printerlibrary.setting.TextSetting;
import com.rt.printerlibrary.utils.PrinterPowerUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import printer.com.citectra.myprinter.constantes.Constantes;
import printer.com.citectra.myprinter.entities.TextPrint;
import printer.com.citectra.myprinter.interfaces.ILinea;

public class Printer implements IPrinter {

//    private LabelPrinter rtPrinter;
    private RTPrinter rtPrinter = null;
    private PrinterFactory printerFactory;
    private PrinterPowerUtil printerPowerUtil;//To switch AP02 printer power.
    private Context context;
    private Object configObj;

    private String mChartsetName = "GBK";//"GBK";

    public Printer(Context context){
        this.context = context;
        printerFactory = new ThermalPrinterFactory();
        rtPrinter = printerFactory.create();
        printerPowerUtil = new PrinterPowerUtil(context);
        this.configObj = new SerialPortConfigBean().getDefaultConfig();
    }

    /**
     * TODO : FUNCIÓN DE IMPRESIÓN DE TEXTO LINEAL POR COMUNICACIÓN SERIAL
     * @throws UnsupportedEncodingException
     * Class RTPrinter :  connect, disconnect, write messages, read message, get the current printer interface(connection object).
     * Class Cmd :
     *  getHeaderCmd ->  Obtener comandos de encabezado
    Tsc: comando CLS, borrar caché
    Esc / Pin: inicialización de la impresora (No es necesario Agregarlo)
     *  getLFCRCmd -> Wrap + Enter  (si no se agrega no imprime)
     *  getBeepCmd() -> Pitido (pone un @al texto)
     *  getAllCutCmd -> Corte Total
     *  getPrintCopies -> configuración Imprimir copias (para Tsc)
     *  writeMsgAsync  -> enviar comandos de matriz de bytes en sub thread.
     *
     *
     */
    @Override
    public void printTextPrint(ArrayList<TextPrint> alTextPrint)throws Exception {
        if (rtPrinter != null && rtPrinter.getPrinterInterface() != null ) {
            CmdFactory escFac = new EscFactory();  //esto me devuelve un cmd
            Cmd escCmd = escFac.create();
            escCmd.append(escCmd.getHeaderCmd());//初始化, Initial el printer

            for (TextPrint textPrint : alTextPrint)
            {
                escCmd.append(lineToBytePrint(textPrint));
                escCmd.append(escCmd.getLFCRCmd());
            }

            escCmd.append(escCmd.getLFCRCmd());
            escCmd.append(escCmd.getLFCRCmd());
            escCmd.append(escCmd.getHeaderCmd());//初始化, TODO :CONSIDERAR POR OBSERVACION
            rtPrinter.writeMsgAsync(escCmd.getAppendCmds());

        }
    }

    @Override
    public void print(ArrayList<ILinea> alILinea)throws Exception {
        if (rtPrinter != null && rtPrinter.getPrinterInterface() != null ) {
            CmdFactory escFac = new EscFactory();  //esto me devuelve un cmd
            Cmd escCmd = escFac.create();
            escCmd.append(escCmd.getHeaderCmd());//初始化, Initial el printer

            for (int i = 0; i < alILinea.size(); i++)  // imprimir línea x línea..
            {
                ILinea iLinea = alILinea.get(i);
                if (iLinea.getID() == Constantes.IDLINEASIMPLE)
                    escCmd.append(this.lineToBytePrint(iLinea.getLinea(), MODO.DEFAULT, iLinea.isNegrita()));
                else if (iLinea.getID() == Constantes.IDLINEADOBLE)
                    escCmd.append(this.lineToBytePrint(iLinea.getLinea(), MODO.DOUBLEWIDTH, iLinea.isNegrita()));    // todo : enumerable DOUBLE
                else if (iLinea.getID() == Constantes.IDLINEACENTER)
                    escCmd.append(this.lineToBytePrint(iLinea.getLinea(), MODO.DEFAULT, iLinea.isNegrita()));    // todo : enumerable DOUBLE
                escCmd.append(escCmd.getLFCRCmd()); // 80-65-67 : PAC
            }
            escCmd.append(escCmd.getLFCRCmd());
            escCmd.append(escCmd.getLFCRCmd());
            escCmd.append(escCmd.getHeaderCmd());//初始化, TODO :CONSIDERAR POR OBSERVACION
            rtPrinter.writeMsgAsync(escCmd.getAppendCmds());

        }
    }

    /**
     * armar cada línea del append que se procesará dentro del método print..
     * @param textPrint {@link TextPrint}
     * @return
     * @throws UnsupportedEncodingException
     */
    public byte[] lineToBytePrint(TextPrint textPrint)throws Exception {
        TextSetting textSetting = new TextSetting();
        CmdFactory escFac = new EscFactory();  //esto me devuelve un cmd
        Cmd escCmd = escFac.create();
        // TODO : CUSTOMIZAR MÁS ESTILOS
        switch (textPrint.getModo()){
            case UNDERLINE:
                textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
                textSetting.setUnderline(SettingEnum.Enable);
                break;
            case DEFAULT:
                textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
                textSetting.setBold(SettingEnum.Disable);
                break;
            case DOUBLEWIDTH:   // aNcho => 2aNCHO
                textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
                textSetting.setDoubleWidth(SettingEnum.Enable);
                break;
            case DOUBLEHEIGHT:
                textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
                textSetting.setDoubleHeight(SettingEnum.Enable);
                break;
            case ANTIWHITE:
                textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
                textSetting.setIsAntiWhite(SettingEnum.Enable);
                break;

            default:
                break;
        }
        if(textPrint.getiLinea().isNegrita())
            textSetting.setBold(SettingEnum.Enable);

        return escCmd.getTextCmd(textSetting, textPrint.getiLinea().getLinea(), mChartsetName);
    }

    /**
     * armar cada línea del append que se procesará dentro del método print..
     * @return
     * @throws UnsupportedEncodingException
     */
    public byte[] lineToBytePrint(String linea, MODO modo, boolean isNegrita)throws Exception {
        TextSetting textSetting = new TextSetting();
        CmdFactory escFac = new EscFactory();  //esto me devuelve un cmd
        Cmd escCmd = escFac.create();
        textSetting.setIsEscSmallCharactor(SettingEnum.Enable);
        switch (modo) {
            case UNDERLINE:
                textSetting.setUnderline(SettingEnum.Enable);
                break;
            case DEFAULT:
                textSetting.setBold(SettingEnum.Disable);
                break;
            case DOUBLEWIDTH:   // aNcho => 2aNCHO
                textSetting.setDoubleWidth(SettingEnum.Enable);
                break;
            case DOUBLEHEIGHT:
                textSetting.setDoubleHeight(SettingEnum.Enable);
                break;
            case ANTIWHITE:
                textSetting.setIsAntiWhite(SettingEnum.Enable);
                break;
            case DOUBLEWIDTHHEIGHT:
                textSetting.setDoubleWidth(SettingEnum.Enable);
                textSetting.setDoubleHeight(SettingEnum.Enable);
                break;
            default:
                break;
        }
        if(isNegrita)
            textSetting.setBold(SettingEnum.Enable);
        return escCmd.getTextCmd(textSetting, linea.replace("°", " "), mChartsetName);
    }

    @Override
    public void getStatus(OnStatusCallback onStatusCallback) {

        if(rtPrinter != null && rtPrinter.getPrinterInterface() != null)
        {
            ConnectStateEnum connectStateEnum = rtPrinter.getConnectState();    // TODO : EVALUAR Q SUCEDE
            switch (connectStateEnum)
            {
                case NoConnect:
                    onStatusCallback.noConnect();
                    break;
                case Connected:
                    onStatusCallback.connected();
                    break;
                case Connecting:
                    onStatusCallback.connecting();
                    break;
            }
        }
        else {
            onStatusCallback.noConnect();
        }
    }

//    Connected, NoConnect, Connecting;
    public interface OnStatusCallback
    {
        void connected();
        void noConnect();
        void connecting();
    }

    @Override
    public void connect() {
        connectSerialPort((SerialPortConfigBean) configObj);
        printerPowerUtil.setPrinterPower(true);
        rtPrinter.getConnectState();
        Log.d("Conect" , rtPrinter.getConnectState()+"");
    }

    private void connectSerialPort(SerialPortConfigBean serialPortConfigBean) {
        PIFactory piFactory = new SerailPortFactory();
        PrinterInterface printerInterface = piFactory.create();
        printerInterface.setConfigObject(serialPortConfigBean);
        rtPrinter.setPrinterInterface(printerInterface);
        try {
            rtPrinter.connect(serialPortConfigBean);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void disconnect() {
        if(rtPrinter == null || rtPrinter.getPrinterInterface() == null)    // debe estar conectado para no generar excepción
            return;
        rtPrinter.disConnect();
        printerPowerUtil.setPrinterPower(false);
    }

//- *.print()
//    realiza replace de símbolo '°' por espacio en blanco ' '
//
//            - *.print()
//    permitir imprimir líneas vacías y quitar el espaciado por defecto.




}


