package org.model.stopduration;

import java.util.ArrayList;

import org.model.routestop.Stop;

public class StopTimeSet {

	private ArrayList<StopTime> stopTimeList = new ArrayList<>();
	
	public ArrayList<StopTime> getStopTimeList() {
		return stopTimeList;
	}

	public void setStopTimeList(ArrayList<StopTime> stopTimeList) {
		this.stopTimeList = stopTimeList;
	}

	public int getTimeBetweenStops(Stop stopId1, Stop stopId2, int timeAtStop) {
		for(StopTime stopTime : stopTimeList) {
			//System.out.println(stopTime.getStop1().getId() + "###" + stopTime.getStop2().getId() + " " + stopId1.getId() + " " + stopId2.getId());
			/*if(stopTime.getStop1() == null || stopTime.getStop1().getId() == null || stopTime.getStop2() == null || stopTime.getStop2().getId() == null)
				return 2;*/
			if(stopTime.getStop1().getId().equals(stopId1.getId()) && stopTime.getStop2().getId().equals(stopId2.getId())) {
				//System.out.print(stopId1.getId() + stopId2.getId() + " ");
				return stopTime.getTimeToTravel(timeAtStop);
			}
		}
		return 0;
	}

	public StopTime getStopTime(String stopId1, String stopId2) {
		for(StopTime stopTime : stopTimeList) {
			if(stopTime.getStop1().getId().equals(stopId1) && stopTime.getStop2().getId().equals(stopId2))
				return stopTime;
		}
		return null;
	}

	public void addStopTime(StopTime stopTime) {
		stopTimeList.add(stopTime);
	}
}
