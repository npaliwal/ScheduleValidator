package org.model;

import java.util.List;

public interface IDBHelper {
	List<Stop> getAllStopsOfRoute(String routeId);
}
