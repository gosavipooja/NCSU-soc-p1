package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import play.db.Database;

public class DbConnection {
	private Database db;
	private DatabaseExecutionContext executionContext;

	@Inject
	public DbConnection(Database db, DatabaseExecutionContext executionContext) {
		this.db = db;
		this.executionContext = executionContext;
	}

	public /* CompletionStage<Void> */double updateLocation(long timestamp, String username, double latitude,
			double longitude) {
		double distance = 0;
		Connection connection = db.getConnection();
		createDBTable(connection);

		insert(connection, timestamp, username, latitude,
			longitude); 
		LocationDetails loc = selectTheFirstRecordOfAName(connection, username);
		
			distance = distanceTravelled(latitude, longitude,
			loc.getLatitude(),loc.getLongitude());
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return distance;
	}

	public void createDBTable(Connection connection) {
		String sqlQuery = "CREATE TABLE IF NOT EXISTS locationtable (\n" + "	id integer PRIMARY KEY,\n"
				+ "	timestamp integer,\n" + "	username text NOT NULL,\n" + "	latitude real,\n"
				+ "	longitude real\n" + ");";

		try {
			Statement stmnt = connection.createStatement();
			stmnt.execute(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insert(Connection connection, long timestamp, String username, double latitude, double longitude) {
		String sqlQuery = "INSERT INTO locationtable (timestamp,username,latitude,longitude) VALUES(?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1, (int) timestamp);
			pstmt.setString(2, username);
			pstmt.setDouble(3, latitude);
			pstmt.setDouble(4, longitude);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LocationDetails selectTheFirstRecordOfAName(Connection connection, String name) {
		String sqlQuery = "SELECT id, timestamp, username, latitude, longitude FROM locationtable WHERE username = ?";
		ResultSet rs = null;
		PreparedStatement  pstmt = null;
		LocationDetails loc = null;
		try {
			pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setString(1, name);
			pstmt.setMaxRows(1);
			rs = pstmt.executeQuery();
			System.out.println(rs.getInt("id"));
			System.out.println(rs.getDouble("latitude"));
			System.out.println(rs.getDouble("longitude"));
			loc = new LocationDetails(rs.getString("username"), rs.getInt("timestamp"), rs.getDouble("latitude"), rs.getDouble("longitude"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;
	}

	double distanceTravelled(double latitude1, double longitude1, double latitude2, double longitude2) {
		double earthRadius = 6371; // kmeters
		double dLat = Math.toRadians(latitude2 - latitude1);
		double dLng = Math.toRadians(longitude2 - longitude1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude1))
				* Math.cos(Math.toRadians(latitude2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;
		return dist;
	}
}
