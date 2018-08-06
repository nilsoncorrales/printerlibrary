package printer.com.citectra.myprinter.printer;

/**
 * TODO : POR PERSONALIZAR CON MÁS MÉTODOS ()
 */
public enum MODO {

    UNDERLINE           (1),
    DEFAULT             (2),
    DOUBLEWIDTH         (3),
    DOUBLEHEIGHT        (4),
    ANTIWHITE           (5),
    DOUBLEWIDTHHEIGHT   (6);


    int codigo;

    MODO(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }


}
