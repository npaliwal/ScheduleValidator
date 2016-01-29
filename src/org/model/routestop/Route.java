package org.model.routestop;

import java.util.List;

import org.model.stopduration.StopTimeSet;

public class Route {
	private String id;
	private Stop[] stopSequence = new Stop[100];
	int size = 0;
	
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

	public int getTimeForRoute(StopTimeSet stopTimeSet, int tripStartTime) {
		int timeAtStop = tripStartTime;
		for(int i = 1; i < size; i++) {
			int timeBetweenStops = stopTimeSet.getTimeBetweenStops(stopSequence[i], stopSequence[i+1], timeAtStop);
			timeAtStop += timeBetweenStops;
		}
		return timeAtStop-tripStartTime;
	}

	public void appendStopAtPosition(Stop stop, int position) {
		stopSequence[position] = stop;
		size++;
	}
}
