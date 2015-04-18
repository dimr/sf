import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import remixlab.proscene.Scene;
import toxi.geom.Vec2D;

public class SanMain extends PApplet implements SConstants {
	String json_routes = "./data/san-francisco/geo/geojson/routes.json";
	String json_stops = "./data/san-francisco/geo/geojson/stops.json";
	String stops_per_route = "./data/san-francisco/geo/geojson/stations_per_route.json";
	static PFont font;
	ArrayList<Route> routes;
	public static ArrayList<Station> stations;
	int counter = 0;
	float angle = 0;
	PImage roads;
	PeasyCam cam;
	float x;
	float z;
	float pitch, yawn, roll;
	float lerpFactor = -100;
	Button[] buttons;
	int route = -1;
	Vec2D mouseLocation;
	Vec2D firstButton, lastButton;

	float scaleBasic = 1;
	Map<String, ArrayList<String>> stationsPerRoute;

	// Scene scene;

	public void setup() {
		// String [] t =
		// loadStrings("/home/dimitris/workspace/RData/Urban-Data-Challenge/public-transportation/san-francisco/passenger-count.csv");
		// String [] td =
		// loadStrings("/home/dimitris/workspace/RData/Urban-Data-Challenge/public-transportation/san-francisco/scheduled-arrivals.csv");
		size(appletWidth +200,200+ appletHeight ,P3D);
		// scene = new Scene(this);
		// scene.setAxisIsDrawn(false);
		// scene.setGridIsDrawn(false);
		// scene.
		// scene.setCenter(new PVector(1110,1000));
	    //cam = new PeasyCam(this, (appletWidth + 200) / 2, (appletHeight + 200) / 2, 0, 100);
		cam = new PeasyCam(this,70,70,0,700);
		routes = DataLoader.loadRoutes(this, json_routes);
		font = createFont("Comfortaa", 48);
		// font = loadFont("Comfortaa-48.vlw");
		stations = DataLoader.loadStations(json_stops);
		 roads = loadImage("SanFrancisco800x600.png");
		// stationsPerRoute = new HashMap<String,ArrayList<Float>>();
		stationsPerRoute = DataLoader.getStationsPerRoute(stops_per_route);
		buttons = new Button[routes.size()];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button(this, new Vec2D(5, x), 70, 30, routes.get(i).getLineID(), routes.get(i).getLineAbbr(), routes.get(i)
					.getLineName());
			x += 31;
		}

		firstButton = buttons[0].getPosition().copy();
		lastButton = buttons[buttons.length - 1].getPosition().copy();
		// cam.setMinimumDistance(100);
		// cam.setMaximumDistance(500);

		//roads = loadImage("/home/dimitris/workspace/processing/sketch2/FrnciscoSHPtoImage/result.png");
		// Station s = routes.get(0).getStations((HashMap<String,
		// ArrayList<Float>>) stationsPerRoute).get(0);

		for (Route r : routes) {
			r.setStations((HashMap<String, ArrayList<String>>) stationsPerRoute, stations);
		}

	}

	public void draw() {
		background(20);
		// scale(scaleBasic);
		// lights();
		// rotateX(radians(lerp(pitch, 30, .04f)));
		pushMatrix();
		 translate((-width+200)/2,(-height+200)/2);
		// scale(scaleBasic);
		image(roads,offSet.x(),offSet.y ,appletWidth,appletHeight);
		popMatrix();
		// x = cos( radians( frameCount ) ) * 1000;
		// z = sin( radians( frameCount ) ) * 1000;
		//
		// camera(x,-150, 234, appletWidth/2,appletHeight/2,-500, 0, 1, 0 );
		fill(100, 0, 0);
		noStroke();
		// pushMatrix();
		// translate(0, 0, 1);
		// for (int i = 0; i < stations.size(); i++) {
		// Vec2D v = stations.get(i).getLocation();
		//
		// // ellipse(v.x, v.y, 4,4);
		// }
		// popMatrix();
		mouseLocation = new Vec2D(mouseX, mouseY);

		fill(100, 50);
	//	 routes.get(counter).drawRoute();
		// rectMode(RADIUS);
		// rect(finalPos.x,finalPos.y,100,100);
		// routes.get(route).drawRoute();
		// routes.get(counter).drawRoute();
		// pushMatrix();
		// translate(routes.get(counter).getCentroid().x,finalPos.y-routes.get(counter).getCentroid().y-200);
		// rotate(radians(angle-=.1));
		// routes.get(counter).drawShrinked();
		// drawShirnkedRoutes();
		// popMatrix();
		fill(255);
		if (mouseLocation.x <= 75 && mouseLocation.y >= 5) {
			cursor(MOVE);
			cam.setActive(false);
			// scene.beginScreenDrawing();

		} else {
			cam.setActive(true);
			// scene.endScreenDrawing();
			cursor(ARROW);
		}
		// ellipse(finalPos.x, finalPos.y, 10, 10);
		textInfo();
		// routes.get(counter).drawDisplaced();
		// ellipse(finalPos.x,(appletHeight+200)-100,40,40);
		drawGUI();
		camControls();
		// cam.setRotations(lerp(pitch,-64,-.04f),yawn,roll);
		pushMatrix();
		translate((-width+200)/2,(-height+200)/2);
		for (int i = 0; i < routes.size(); i++) {
			if (route == routes.get(i).getLineID()) {
				routes.get(i).drawRoute();
				//routes.get(i).drawStations();
				 break;
			}
		}
		popMatrix();
		drawShirnkedRoutes();
		// saveFrame("./pics/screen####.png");
	}

	void textInfo() {
		hint(DISABLE_DEPTH_TEST);
		cam.beginHUD();
		// scene.beginScreenDrawing();
		// for (Button b:buttons)
		// b.drawButton();
		for (int i = 0; i < routes.size(); i++) {
			if (route == routes.get(i).getLineID()) {
				text(routes.get(i).getLineName() + " \n" + String.valueOf(counter) + ") " + routes.get(counter).getLineID() + " # ",
						getWidth() / 2, 50);
				// drawGUI();
			}
		}
		cam.endHUD();
		// scene.endScreenDrawing();
		hint(ENABLE_DEPTH_TEST);
	}

	void drawShirnkedRoutes() {
		hint(DISABLE_DEPTH_TEST);
		cam.beginHUD();
		// scene.beginScreenDrawing();
		for (int i = 0; i < routes.size(); i++) {
			if (route == routes.get(i).getLineID()) {
				routes.get(i).drawShrinked();
			}
		}
		cam.endHUD();
		// scene.endScreenDrawing();
		hint(ENABLE_DEPTH_TEST);
	}

	public void keyPressed() {
		if (key == ' ') {

			scaleBasic = lerp(scaleBasic, 0, .1f);
		}
		// if (keyCode == UP) {
		// counter++;
		// } else if (keyCode == DOWN && counter != 0) {
		// counter--;
		// }else if (keyCode == DELETE){
		// System.out.println("dfdsaga");
		// }
		// }
		// counter %= routes.size();

	}

	public void camControls() {
		int x = 680;
		int y = 700;
		hint(DISABLE_DEPTH_TEST);
		cam.beginHUD();
		pushStyle();
		fill(100,70);
		rect(x-30,y-30, 400, 500);
		popStyle();
		double distance = cam.getDistance();
		float[] position = cam.getPosition();
		float[] rotations = cam.getRotations();
		float[] lookUp = cam.getLookAt();
		text("position = " + Arrays.toString(position) + "\ndistance = " + String.valueOf(distance) + "\nrotations = "
				+ Arrays.toString(rotations) + "\nlookUp = " + Arrays.toString(lookUp) + "\n" + String.valueOf(degrees(rotations[0]))
				+ " , " + String.valueOf(degrees(rotations[1])) + "  " + String.valueOf(degrees(rotations[2])), x,y);

		cam.endHUD();
		hint(ENABLE_DEPTH_TEST);
	}

	// public void mouseDragged(){
	// for (int i=0; i<buttons.length; i++){
	// buttons[i].navigate();
	// if (mouseLocation.y > buttons[i].getMouseLocInitital().y){
	// System.out.println("GOING UP");
	// float y = buttons[i].getPosition().y +( (buttons[i].getPosition().y-3) -
	// buttons[i].getPosition().y);
	// int h = buttons[i].getH();
	// //float y =
	// constrain(mouseLocation.y-h/2,buttons[i].getPosition().y-h,buttons[i].getPosition().y+h);
	// //float y= constrain(buttons[i].getPosition().y,y,y+buttons[i].getH());
	// buttons[i].setPosition(buttons[i].getPosition().x,y);
	// }else if(mouseLocation.y < buttons[i].getMouseLocInitital().y){
	// System.out.println("GOIG DOWN");
	// }
	// }
	// }
	public void mouseDragged() {
		if (mouseLocation.x <= 55 && mouseLocation.y >= 5) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].navigate();
				// Vec2D pos = buttons[i].getPosition();
				// buttons[i].setPosition(new
				// Vec2D(pos.x,lerp(pos.y,pos.y+12,.3f)));
				// System.out.println("POSITION "+pos);
				if (mouseLocation.y > buttons[i].getMouseLocInitital().y) {
					// System.out.println("UP");
					buttons[i].setPosition(new Vec2D(buttons[i].getPosition().x, buttons[i].getPosition().y - 3));
				} else if (mouseLocation.y < buttons[i].getMouseLocInitital().y) {
					buttons[i].setPosition(new Vec2D(buttons[i].getPosition().x, buttons[i].getPosition().y + 3));
					// System.out.println("DOWN");
				}
				if (buttons[0].getPosition().y > 10) {
					// buttons[i].getPosition().y=10;
				}
				// System.out.println(buttons[0].getPosition());
			}
		}
	}

	public void drawGUI() {

		hint(DISABLE_DEPTH_TEST);
		// scene.beginScreenDrawing();
		cam.beginHUD();
		for (int i = 0; i < buttons.length; i++) {

			buttons[i].drawButton();
			if (buttons[i].inside()) {
				route = buttons[i].getLineID();
			}
		}
		cam.endHUD();
		// scene.endScreenDrawing();
		hint(ENABLE_DEPTH_TEST);

	}

	// public ArrayList<Station> getStations(HashMap<String,ArrayList<Float>>
	// data,Route r){
	// ArrayList<Float> temp_routes = new ArrayList<Float>();
	// for (String s:data.keySet()){
	// if (s.equals(r.lineAbbr)){
	// //System.out.println(data.get(s).getClass().getCanonicalName());
	// temp_routes = (ArrayList)data.get(s);
	// break;
	// }
	// }
	//
	// for (Station station : SanMain.stations){
	// for (Float f : temp_routes){
	// if (station.getStopID()==f.floatValue()){
	// stations.add(station);
	// }
	// }
	// }
	// return stations;
	// }

	public static void main(String[] args) {
		PApplet.main(new String[] { SanMain.class.getName() });

	}

}
