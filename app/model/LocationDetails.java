package model;

public class LocationDetails {
	private String username;
	private long timestamp;
	private double latitude;
	private double longitude;
	
	public LocationDetails(String username, long timestamp, double latitude, double longitude) {
		super();
		this.username = username;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
