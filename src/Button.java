import processing.core.PApplet;
import toxi.geom.Vec2D;

public class Button {

    private PApplet pa;
    private Vec2D position;
    private int h, w;
    private String lineAbbreviation;
    private float size;
    private int lineID;
    private int counter;
    private String name;
    private Vec2D mouseLocInitital;
    private int buttonCounter = 0;
    int previous = -1;


    public Button(PApplet pa, Vec2D position, int w, int h, int lineID, String lineAbbreviation, String name) {
        this.pa = pa;
        this.position = position;
        this.w = w;
        this.lineID = lineID;

        this.h = h;
        this.lineAbbreviation = lineAbbreviation;
        size = 0;
        this.name = name;
        pa.textFont(SanMain.font, 12);

    }


    public int getLineID() {
        return lineID;
    }


    public void setLineID(int lineID) {
        this.lineID = lineID;
    }


    public Vec2D getPosition() {
        return position;
    }

    public void setPosition(Vec2D position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public String getLineAbbreviation() {
        return this.lineAbbreviation;
    }

    public void setId(String lineAbbr) {
        this.lineAbbreviation = lineAbbr;
    }


    public int setPrevious(int lineID) {
        return this.previous = lineID;
    }

    public int getPrevious() {
        return this.previous;
    }

    public String getName() {
        return this.name;
    }

    public void drawButton() {
        pa.pushStyle();
        int previous = -1;
        if (inside()) {
            pa.fill(89, 125, 132);
            counter++;
            //delay a bit
            if (counter > 20) {
                pa.pushStyle();
                pa.fill(100, 60);
                size = pa.lerp(size, name.length(), .3f);
                pa.rect(position.x + w + 10, position.y, size * 10, h, 5);
                pa.popStyle();

            }
            //delay text a bit more
            if (counter > 35) {
                // pa.textMode(pa.SCREEN);
                pa.pushStyle();
                pa.fill(255);

                pa.text(name, position.x + w + 20, position.y + h / 2);// +h/4+20);
                pa.popStyle();
            }
        } // IF INSIDE
        else {
            pa.fill(89, 125, 132, 70);
            counter = 0;
            size = 0;
            // setLineID(-1);
            // setPrevious(-1);
            // System.out.println("COUNTER RESET");
            // pa.cursor(pa.ARROW);
        }

        // pa.rectMode(pa.CORNER);
        pa.rect(position.x, position.y, w, h, 5);
        pa.popStyle();
        // pa.pushStyle();
        // pa.fill(0);
        pa.text(lineAbbreviation, position.x + 10, position.y + h / 2);
        // pa.ellipse(position.x+w/2,position.y+h/2,10,10);
        // pa.pStyle();

    }

    public Vec2D getMouseLocInitital() {
        return mouseLocInitital;
    }

    public void setMouseLocInitital(Vec2D mouseLocInitital) {
        this.mouseLocInitital = mouseLocInitital;
    }

    public boolean inside() {
        if (pa.mouseX >= position.x && pa.mouseX <= position.x + w
                && pa.mouseY >= position.y && pa.mouseY <= position.y + h) {
            return true;
        } else {
            return false;
        }
    }

    public void navigate() {
        counter = 0;
        // if (inside()){
        mouseLocInitital = new Vec2D(pa.mouseX, pa.mouseY);
        // if ()
        // position.set(position.x,pa.lerp(position.y, position.y+3, .4f));
        // position.set
        // }else{
        // }

    }
}
