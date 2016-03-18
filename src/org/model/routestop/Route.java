package org.model.routestop;

import java.util.List;

import org.model.stopduration.StopTimeSet;

public class Route {
	private String id;
	private Stop[] stopSequence = new Stop[100];
	int size = 0;
	double startLat, startLong, stopLat, stopLong;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Stop[] getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Stop[] stopSequence) {
		this.stopSequence = stopSequence;
	}

	public int getTimeForRoute(String cabTripId, StopTimeSet stopTimeSet, int tripStartTime) {
		int timeAtStop = tripStartTime;
		for(int i = 0; i < size-1; i++) {
			//System.out.println(stopSequence[i].getId() + " " + stopSequence[i+1].getId());
			int timeBetweenStops;
			if(stopSequence[i] == null || stopSequence[i+1] == null) {
				System.out.println("Error " + cabTripId + " " + id + " " + i + " " + (i+1));
				timeBetweenStops = 2;
			}
			else
				timeBetweenStops = stopTimeSet.getTimeBetweenStops(stopSequence[i], stopSequence[i+1], timeAtStop);
			timeAtStop += timeBetweenStops;
		}
		return timeAtStop-tripStartTime;
	}

	public void appendStopAtPosition(Stop stop, int position) {
		stopSequence[position] = stop;
		size++;
	}

	public void addCoordinates(double startLat, double startLong, double stopLat, double stopLong) {
		this.startLat = startLat;
		this.startLong = startLong;
		this.stopLat = stopLat;
		this.stopLong = stopLong;
		
	}

	public int getTimeForRoute(StopTimeSet stopTimeSet, int tripStartTime) {
		int timeAtStop = tripStartTime;
		for(int i = 0; i < size-1; i++) {
			int timeBetweenStops;
			if(stopSequence[i] == null || stopSequence[i+1] == null)
				timeBetweenStops = 2;
			else
				timeBetweenStops = stopTimeSet.getTimeBetweenStops(stopSequence[i], stopSequence[i+1], timeAtStop);
			timeAtStop += timeBetweenStops;
		}
		return timeAtStop-tripStartTime;
	}

	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getStartLong() {
		return startLong;
	}

	public void setStartLong(double startLong) {
		this.startLong = startLong;
	}

	public double getStopLat() {
		return stopLat;
	}

	public void setStopLat(double stopLat) {
		this.stopLat = stopLat;
	}

	public double getStopLong() {
		return stopLong;
	}

	public void setStopLong(double stopLong) {
		this.stopLong = stopLong;
	}
	
	
}
