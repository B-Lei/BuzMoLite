package BuzMo.GUI;

/**
 * Created by lucas on 12/1/2016.
 * Class used to easily format Stdout requests
 * Class will simply align left if the characters entered are larger than the set width
 */
public class GUIOutput {
    enum ALIGN{LEFT, CENTER, RIGHT};
    private static GUIOutput ourInstance = new GUIOutput();
    private int width = 30; //Maximum Width of the GUI
    private ALIGN alignment = ALIGN.LEFT;

    public static GUIOutput getInstance() {
        return ourInstance;
    }

    private GUIOutput() {}


    //Writes a line to Stdout
    public void writeLine(){
        for(int i=0; i<width; i++){ System.out.print('-'); }
        System.out.println();
    }


    public void write(String in) {
        if (alignment == ALIGN.CENTER) {
            while (in.length() < width) {
                in = " " + in + " ";
            }
        }
        else if(alignment == ALIGN.RIGHT){
            while(in.length() < width){
                in = " " + in;
            }
        }
        System.out.println(in);
    }





    public void setWidth(int width){ this.width=width; }
    public int getWidth(){ return this.width; }
    public void setAlignment(ALIGN align){ this.alignment = align; }
    public ALIGN getAlignment(){ return this.alignment; }

}
