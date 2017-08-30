package model;

import akka.actor.ActorSystem;
import play.api.libs.concurrent.CustomExecutionContext;

public class DatabaseExecutionContext extends CustomExecutionContext{

	@javax.inject.Inject
	public DatabaseExecutionContext(ActorSystem system) {
		super(system, "play.db");
	}

}
