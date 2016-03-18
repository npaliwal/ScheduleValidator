package org.model.bus;

import java.util.ArrayList;

import org.model.routestop.Route;
import org.model.stopduration.StopTimeSet;

public class Bus {

	private String id;
	private ArrayList<Trip> tripList = new ArrayList<>();
	
	private static final double EARTH_RADIUS_IN_KM = 6371.0088;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addTrip(StopTimeSet stopTimeSet, Route route, int startTime) {
		Trip trip = new Trip();
		trip.setRoute(route);
		trip.setStartTime(startTime);
		trip.setEndTime(startTime + route.getTimeForRoute(stopTimeSet, startTime));
		if(!tripList.isEmpty()) {
			if(startTime <= tripList.get(0).getStartTime())
			tripList.add(0, trip);
		else if(startTime >= tripList.get(tripList.size()-1).getStartTime())
			tripList.add(trip);
		else
			for(int i = 1; i < tripList.size(); i++)
				if(startTime <= tripList.get(i).getStartTime()) {
					tripList.add(i, trip);
					break;
				}
		} else
			tripList.add(trip);
	}

	public ArrayList<String> validateTrips(String outputFile) {
		ArrayList<String> printableStrings = new ArrayList<>();
		printableStrings.add("Bus id,tripId,startTime,calculatedEndTime,breakBetweenTrips,distanceToTheNextTrip");
		for(int i = 0; i < tripList.size()-1; i++) {
			Trip trip1 = tripList.get(i);
			Trip trip2 = tripList.get(i+1);
			double distance = calculateDistance(trip1, trip2);
			printableStrings.add(id + "," + trip1.getRoute().getId() + "," + trip1.getStartTime() + "," + trip1.getEndTime() + "," + (trip2.getStartTime() - trip1.getEndTime()) + "," + distance);
		}
		printableStrings.add(id + "," + tripList.get(tripList.size()-1).getRoute().getId() + "," + tripList.get(tripList.size()-1).getStartTime() + "," + tripList.get(tripList.size()-1).getEndTime() + ",,");
		return printableStrings;
	}

	private double calculateDistance(Trip trip1, Trip trip2) {
		return distance(trip1.getRoute().getStopLat(), trip1.getRoute().getStopLong(),
				trip2.getRoute().getStartLat(), trip2.getRoute().getStartLong());
	}
	
	public double distance(
            double latitudeA, double longitudeA,
            double latitudeB, double longitudeB) {
        latitudeA = Math.toRadians(latitudeA);
        longitudeA = Math.toRadians(longitudeA);
        latitudeB = Math.toRadians(latitudeB);
        longitudeB = Math.toRadians(longitudeB);
        return 2 * EARTH_RADIUS_IN_KM * Math.asin(Math.sqrt(
                Math.pow(Math.sin((latitudeB - latitudeA) / 2), 2) +
                Math.cos(latitudeA) * Math.cos(latitudeB) *
                Math.pow((Math.sin((longitudeB - longitudeA) / 2)), 2)));
    }
	
}
