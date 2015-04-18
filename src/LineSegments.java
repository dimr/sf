import java.util.ArrayList;

import org.json.simple.JSONArray;

import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

public class LineSegments implements SConstants {

    private ArrayList<Vec2D>[] pieces;
    private int numberOfSegments;
    private PApplet pa;
    private Vec2D accumulator;
    int vertexCounter = 0;
    private ArrayList<Vec2D> allPoints;
    private float scaleFactor = .3f;
    float angle = 0;

    public LineSegments(PApplet pa, JSONArray arr) {
        this.pa = pa;
        pieces = (ArrayList<Vec2D>[]) new ArrayList[arr.size()];
        allPoints = new ArrayList<Vec2D>();
        numberOfSegments = arr.size();
        accumulator = new Vec2D(0, 0);
        for (int i = 0; i < numberOfSegments; i++) {
            JSONArray t = (JSONArray) arr.get(i);
            ArrayList<Vec2D> temp = new ArrayList<Vec2D>();
            for (int j = 0; j < t.size(); j++) {
                JSONArray s = (JSONArray) t.get(j);
                double lo = (Double) s.get(0);
                double lat = (Double) s.get(1);
                /*
				 * float f_lo = (float) lo; float f_la = (float) la;
				 */
                float f_lo = (float) (appletWidth * (lo - (-122.53867)) / ((-122.36545) - (-122.53867)));
                float f_la = (float) (appletHeight - appletHeight * (lat - 37.705765) / (37.836445 - 37.705765));
                Vec2D v = null;
                v = new Vec2D(f_lo, f_la).add(offSet);
                temp.add(v);
                accumulator.set(accumulator.add(v));
                vertexCounter++;
                // ArrayList<Vec2D> a= (ArrayList<Vec2D>) Reducer.reduced(temp,
                // .5f);
                // allPoints.add(v);
            }
            pieces[i] = temp;
        }
        accumulator.set(accumulator.x / vertexCounter, accumulator.y / vertexCounter);
        // for (int i = 0; i < allPoints.size(); i++) {
        // Vec2D v = allPoints.get(i).sub(accumulator);
        // displaced.add(v);
        // }

    }

    public int getNumberOfSegments() {
        return this.numberOfSegments;
    }

    public ArrayList<Vec2D>[] getPieces() {
        return this.pieces;
    }

    public void drawSegment(float lerpFactor) {
        for (int i = 0; i < this.pieces.length; i++) {
            d(this.pieces[i], lerpFactor);
        }
        if (pa.keyPressed) {

        }
    }

    public Vec2D getCentroid() {
        return this.accumulator;
    }

    public void drawDisplaced() {
        pa.pushStyle();
        pa.pushMatrix();
        pa.translate(finalPos.x, finalPos.y);
        pa.stroke(0, 0, 255);
        // pa.noStroke();
        pa.smooth();
        pa.pushMatrix();
        // pa.rotate(pa.radians(angle-=.001f));
        pa.rotate(pa.radians(pa.frameCount) * 2);
        // pa.strokeWeight(.4f);
        for (int i = 0; i < this.pieces.length; i++) {
            ArrayList<Vec2D> temp = this.pieces[i];
            for (int j = 1; j < temp.size(); j++) {
                Vec2D from = temp.get(j - 1).sub(accumulator);
                Vec2D to = temp.get(j).sub(accumulator);
                // Vec2D t = to.constrain(new Vec2D(-200,-200),new Vec2D(600
                // ,600));
                // pa.ellipse(to.x*scaleFactor, to.y*scaleFactor, 3,3);

                pa.line(from.x * scaleFactor, from.y * scaleFactor, to.x * scaleFactor, to.y * scaleFactor);

            }
        }
        pa.popMatrix();
        pa.popMatrix();
        pa.popStyle();
    }

    private void d(ArrayList<Vec2D> vs, float lerpFactor) {
        pa.pushStyle();
        pa.stroke(255, 0, 0);
        pa.strokeWeight(.7f);
        Vec2D from, to;
        Vec3D up = null, down = null;
        for (int i = 1; i < vs.size(); i++) {
            from = vs.get(i - 1);
            to = vs.get(i);
            pa.line(from.x, from.y, to.x, to.y);
            // if (pa.keyPressed){
            //
            up = new Vec3D(from.x, from.y, 100);
            down = new Vec3D(from.x, from.y, -100);
            pa.pushMatrix();
            pa.pushStyle();
            pa.stroke(100);
            pa.translate(0, 0, 100);
            // pa.ellipse(up.x,up.y,40,40);
            pa.strokeWeight(1.4f);
            // pa.line(down.x,down.y, down.z, up.x,up.y ,lerpFactor);// )
            // pa.ellipse(up.x,up.y,5,5);
            // pa.line(from.x, y1, x2, y2);

            // QUAD STRIP
//            pa.beginShape(pa.QUAD_STRIP);
//            pa.pushStyle();
//            pa.fill(100, 90);
//            pa.noStroke();
//            pa.vertex(from.x, from.y, -100);
//            pa.vertex(from.x, from.y, lerpFactor);
//            pa.vertex(to.x, to.y, -100);
//            pa.vertex(to.x, to.y, lerpFactor);
//            pa.endShape();
//            pa.popStyle();
//            pa.popStyle();


            pa.popMatrix();
            //END QUAD STRIP
        }

        pa.popStyle();
        // upValue=-100;
    }
	/*
	 * private void d(ArrayList<Vec2D> vs, float lerpFactor) { pa.pushStyle();
	 * pa.stroke(255, 0, 0); pa.strokeWeight(.7f); Vec2D from, to; for (int i =
	 * 1; i < vs.size(); i++) { from = vs.get(i - 1); to = vs.get(i);
	 * pa.line(from.x, from.y, to.x, to.y); // if (pa.keyPressed){
	 * 
	 * up = new Vec3D(from.x, from.y, 100); Vec3D down = new Vec3D(from.x,
	 * from.y, -100); pa.pushMatrix(); pa.pushStyle(); pa.stroke(100);
	 * pa.translate(0, 0, 100); // pa.ellipse(up.x,up.y,40,40);
	 * pa.strokeWeight(1.4f); pa.line(down.x,down.y, down.z, up.x,up.y
	 * ,lerpFactor);// ) // up.z/2); // pa.line(from.x, y1, x2, y2);
	 * pa.popStyle(); pa.popMatrix(); // System.out.println("OK"); // }
	 * 
	 * } pa.popStyle(); // upValue=-100; }
	 */
}