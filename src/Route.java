import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

public class Route implements SConstants {

    private String name, lineAbbr;
    private int line_id;
    private LineSegments segments;
    private int numVecs = 0;
    private int[] result;
    private PApplet pa;
    // private ArrayList<Vec2D> allPoints;
    private float lerpFactor = -100;
    private ArrayList<Station> stations;

    public Route(PApplet pa, String lineAbbr, String name, int line_id,
                 LineSegments segments) {
        this.pa = pa;
        String[] m = (name.split("\\s+"));
        //System.out.println(m[0]+" || "+Arrays.toString(m));
        this.lineAbbr = lineAbbr;
        this.name = name;
        this.segments = segments;
        this.line_id = line_id;
        stations = new ArrayList<Station>();
        // allPoints = allPoints();
        //	pa = new PApplet();

    }

    public String getLineAbbr() {
        return lineAbbr;
    }

    public void setLineAbbr(String lineAbbr) {
        this.lineAbbr = lineAbbr;
    }

    public String getLineName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LineSegments getSegments() {
        return segments;
    }

    public void setSegments(LineSegments segments) {
        this.segments = segments;
    }

    public int getLineID() {
        return this.line_id;
    }


    //public ArrayList<Station> getStations(HashMap<String,ArrayList<Float>> data){
    public void setStations(HashMap<String, ArrayList<String>> data, ArrayList<Station> theStations) {
        ArrayList<String> temp_routes = new ArrayList<String>();
        for (String s : data.keySet()) {
            if (s.equals(this.lineAbbr)) {
                temp_routes = (ArrayList<String>) data.get(s);
                break;
            }
        }

        for (Station station : theStations) {
            for (String f : temp_routes) {
                if (station.getStopID() == Float.parseFloat(f)) {
                    stations.add(station);
                }
            }
        }
        //temp_routes = null;


    }

    public ArrayList<Station> getStationsForRoute() {
        return this.stations;
    }

    public void drawStations() {

        pa.pushStyle();
        pa.fill(0, 0, 255);
        pa.stroke(245, 0, 0);
        //pa.strokeWeight(20);
        for (Station s : stations) {
            Vec2D v = s.getLocation();

            pa.ellipse(s.getLocation().x, s.getLocation().y, 4, 4);
            //pa.rotateX(pa.PI);
           // pa.line(s.getLocation().x, s.getLocation().y, 0, s.getLocation().x, s.getLocation().y, 100);
            pa.pushMatrix();
            pa.translate(s.getLocation().x,s.getLocation().y,250);
            pa.rotateY(pa.radians(90));
            pa.text(s.getStopName(), 0,0, 0);
            pa.popMatrix();
        }
        pa.popStyle();
    }


    /**
     * calculates the how many VERTICES each sub-segment contains
     *
     * @return
     */
    // public int[] segs() {
    // ArrayList<Integer> a = new ArrayList<Integer>();
    // for (int i = 0; i < this.segments.getPieces().length; i++) {
    // a.add(this.segments.getPieces()[i].size());
    // }
    // result = new int[a.size()];
    // for (int i = 0; i < a.size(); i++) {
    // result[i] = a.get(i);
    // }
    // a = null;
    // if (result.length != this.segments.getNumberOfSegments())
    // throw new java.lang.RuntimeException("WRONG");
    // return result;
    // }

    /**
     * The number of vertices in ALL segments of EACH ROUTE
     *
     * @return The number of vertices in ALL segments of EACH ROUTE
     */
    // private int getSum(){
    // int sum=0;
    // for (int i=0; i<this.segs().length; i++){
    // sum+=this.segs()[i];
    // }
    // return sum;
    // }
    // public ArrayList<Vec2D> allPoints(){
    // ArrayList<Vec2D> result = new ArrayList<Vec2D>();
    // for (int i=0; i<this.segments.getNumberOfSegments(); i++){
    // for (Vec2D v: this.segments.getPieces()[i]){
    // result.add(v);
    // }
    // }
    // int sum = getSum();
    // numVecs = result.size();
    // if (numVecs != sum){
    // throw new java.lang.RuntimeException("OHHHHH");
    // }
    //
    //
    // return result;
    // }
    public void drawRoute() {

        this.segments.drawSegment(lerpFactor);
        lerpFactor = pa.lerp(lerpFactor, 0, .03f);
        if (lerpFactor > -0.1031225521) {
            //	System.out.println("Reseting LerpFactor");
            lerpFactor = -100;
        }
    }

    public Vec2D getCentroid() {
        return segments.getCentroid();
    }

    public void drawShrinked() {
        this.segments.drawDisplaced();
    }

    @Override
    public String toString() {
        return "Route [name=" + name + ", lineAbbr=" + lineAbbr + ", line_id="
                + line_id + "]";
    }


}
