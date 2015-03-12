import java.util.ArrayList;

import toxi.geom.Vec2D;

public class Station implements SConstants{

	private String stopName;
	private float stopID, longitude, latitude;
	private Vec2D location;

	public Station(String stopName, float stopID, Vec2D location) {
		this.stopName = stopName;
		this.stopID = stopID;
		setLocation(location);
		
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public float getStopID() {
		return stopID;
	}

	public void setStopID(float stopID) {
		this.stopID = stopID;
	}

	public Vec2D getLocation() {
		return location;
	}

	public void setLocation(Vec2D location) {
		this.location = location;
		location.setComponent(0, (float) (appletWidth * (location.x - (-122.53867) )/ ( (-122.36545) - (-122.53867)  )));
		location.setComponent(1,(float) (appletHeight - appletHeight * ( location.y- 37.705765 )/ (37.836445 -37.705765 )));
		location.set(location.add(offSet));
	}

	
	
	@Override
	public String toString() {
		return "Station [stopName=" + stopName + ", stopID=" + stopID
				+ ", location=" + location + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + Float.floatToIntBits(longitude);
		result = prime * result + Float.floatToIntBits(stopID);
		result = prime * result
				+ ((stopName == null) ? 0 : stopName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		if (Float.floatToIntBits(stopID) != Float.floatToIntBits(other.stopID))
			return false;
		if (stopName == null) {
			if (other.stopName != null)
				return false;
		} else if (!stopName.equals(other.stopName))
			return false;
		return true;
	}

//	public static void main(String [] args){
//		String json_file = "/home/dimitris/workspace/RData/Urban-Data-Challenge/public-transportation/san-francisco/geo/geojson/stops.json";
//
//		ArrayList<Station> stations = DataLoader.loadStations(json_file);
//		System.out.println(stations.get(0));
//	}
}
