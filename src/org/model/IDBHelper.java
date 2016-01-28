package org.model;

import java.util.List;

public interface IDBHelper {
	List<Stop> getAllStopsOfRoute(String routeId);
	void appendStopToRoute(Route route, Stop stop);
	void addStopToRouteAtPosition(Route route, Stop stop, int position);
	Route createNewRoute(String routeId);
	StopTimeSet createStopTimeSet();
	void appendStopTimeToSet(StopTimeSet stopTimeSet, StopTime stopTime);
	void createRouteStopEntry(String routeId, String stopId, int position);
	StopTime createStopTime(String stopId1, String stopId2);
	void createStopTimeTravelDurationEntry(String stopId1, String stopId2, int startTime, int endTime,
			int duration);
	Stop createNewStop(String stopId);
	TimeDurationTravel createTimeDurationTravel(int startTime, int endTime, int duration);
}
