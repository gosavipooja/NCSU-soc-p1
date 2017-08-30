package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import model.DbConnection;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	@Inject
	DbConnection dbConnection;
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public Result handleupdates() {
    	double distance = 0.0;
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        long timestamp = json.findPath("timestamp").longValue();
        double latitude = json.findPath("latitude").doubleValue();
        double longitude = json.findPath("longitude").doubleValue();
        if(username == null) {
            return badRequest("Missing parameter [username]");
        } else {
        	// Here I have obtained the current location along with name
        	distance = dbConnection.updateLocation(timestamp, username, latitude, longitude); //Established the connection and created table
            return ok("Hello " + username + "\tdistance " + distance);
        }
    }
}
