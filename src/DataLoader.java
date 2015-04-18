import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import processing.core.PApplet;
import toxi.geom.Vec2D;

public class DataLoader {

    public static ArrayList<Station> loadStations(String json_file) {
        ArrayList<Station> stations = new ArrayList<Station>();
        JSONParser parser = new JSONParser();
        Object in = null;
        try {
            in = parser.parse(new FileReader(json_file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject start = (JSONObject) in;
        JSONArray features = (JSONArray) start.get("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject properties = (JSONObject) features.get(i);
            JSONObject pr = (JSONObject) properties.get("properties");
            String name = (String) pr.get("STOPNAME");
            double lon = ((Double) pr.get("LONGITUDE")).doubleValue();
            double lat = ((Double) pr.get("LATITUDE")).doubleValue();
            float f_lo = (float) lon;
            float f_lat = (float) (lat);
            double stop_id = ((Double) pr.get("STOPID")).doubleValue();
            stations.add(new Station(name, (float) stop_id, new Vec2D(f_lo, f_lat)));
        }
        // System.out.println(stations.size()+" Stations : Loaded ");

        // for (Station s: stations)
        // System.out.println(stations.get(0));
        // System.out.println(s.getStopID()+" "+s.getStopName());
        return stations;
    }

    public static ArrayList<Route> loadRoutes(PApplet pa, String json_file) {
        ArrayList<Route> routes = new ArrayList<Route>();
        JSONParser parser = new JSONParser();
        Object in = null;
        try {
            in = parser.parse(new FileReader(json_file));
        } catch (Exception e) {
        }

        JSONObject start = (JSONObject) in;
        JSONArray features = (JSONArray) start.get("features");
        for (int i = 0; i < features.size(); i++) {

            JSONObject pr = (JSONObject) features.get(i);
            JSONObject properties = (JSONObject) pr.get("properties");
            String lineAbbr = (String) properties.get("LINEABBR");
            String final_lineAbbr = allDigits(lineAbbr) ? fix(lineAbbr) : "X";
            String stop_id = (String) properties.get("STOPID");
            String name = (String) properties.get("LINENAME");
            long theID = ((Long) properties.get("LINEID")).longValue();
            int line_id = (int) theID;

            JSONObject ar = (JSONObject) pr.get("geometry");
            JSONArray ar2 = (JSONArray) ar.get("coordinates");
            if (!final_lineAbbr.equals("X"))
                routes.add(new Route(pa, final_lineAbbr, name, line_id, new LineSegments(pa, ar2)));
        }

        // for (Route r : routes)
        // System.out.println(r.getLineAbbr() + " " + fix(r.getLineAbbr()));
        return routes;
    }

    private static String fix(String name) {
        char[] a = name.toCharArray();
        StringBuilder result = new StringBuilder();
        if (a[0] == '0' && a[1] != '0') {
            for (int i = 1; i < a.length; i++) {
                result.append(a[i]);
            }
            return result.toString();

        } else if (a[0] == '0' && a[1] == '0') {
            for (int i = 2; i < a.length; i++) {
                result.append(a[i]);
            }
            return result.toString();
        }
        return name;
    }

    private static boolean allDigits(String name) {
        boolean result = true;
        char[] a = name.toCharArray();
        for (int i = a.length - 1; i >= 0; i--) {
            if (Character.isAlphabetic(a[i])) {
                return !result;
            }
        }
        return result;
    }

    public static HashMap<String, ArrayList<String>> getStationsPerRoute(String filename) {
        Map<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        JSONParser parser = new JSONParser();
        Object in = null;
        try {
            in = parser.parse(new FileReader(filename));

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject start = (JSONObject) (in);
        for (Object ss : start.keySet()) {
            if (((JSONArray) start.get(ss)).size() != 0) {
                // System.out.println(ss+" "+start.get(ss));
                result.put((String) ss, (ArrayList) start.get(ss));
            }
        }
        return (HashMap<String, ArrayList<String>>) result;
    }
}
