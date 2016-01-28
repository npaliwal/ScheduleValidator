package org.model;

import java.util.ArrayList;



public class StopTime {

	private Stop stop1;
	private Stop stop2;
	private ArrayList<TimeDurationTravel> timeList = new ArrayList<>();
	
	
	
	public Stop getStop1() {
		return stop1;
	}



	public void setStop1(Stop stop1) {
		this.stop1 = stop1;
	}



	public Stop getStop2() {
		return stop2;
	}



	public void setStop2(Stop stop2) {
		this.stop2 = stop2;
	}



	public ArrayList<TimeDurationTravel> getTimeList() {
		return timeList;
	}



	public void setTimeList(ArrayList<TimeDurationTravel> timeList) {
		this.timeList = timeList;
	}



	public int getTimeToTravel(int timeAtStop) {
		for(TimeDurationTravel timeDurationTravel : timeList) {
			if(timeAtStop >= timeDurationTravel.getStartTime() &&
					timeAtStop < timeDurationTravel.getEndTime())
				return timeDurationTravel.getTravelTime();
		}
		return 0;
	}



	public void addTimeDurationTravel(TimeDurationTravel timeDurationTravel) {
		timeList.add(timeDurationTravel);
	}
}
