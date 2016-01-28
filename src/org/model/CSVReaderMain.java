package org.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSVReaderMain implements IDBHelper{
	RouteList routeList = new RouteList();
	StopTimeSet stopTimeSet = new StopTimeSet();
	public static void main(String args[]) throws IOException {
		String csvRouteStop = "Route_Stop_pt.csv";
		String csvTimeDuration = "Avg_Duration_Patch_Test.csv";
		String csvSchedule = "Schedule_Upload_Test.csv";
		
		CSVReaderMain obj = new CSVReaderMain();
		
		obj.readFiles(args[0] + csvRouteStop, args[0] + csvTimeDuration, args[0] + csvSchedule);
	}

	private void readFiles(String csvRouteStop, String csvTimeDuration, String csvSchedule) throws IOException {
		/*
		 * populate the routeList
		 * 
		 */
		
		BufferedReader br = new BufferedReader(new FileReader(new File(csvRouteStop)));
		br.readLine();
		String strLin;
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			String[] strSplit = strLin.split(",");
			String routeId = strSplit[0].replaceAll("\\\"", "");
			//System.out.println(routeId);
			String stopId = strSplit[1].replaceAll("\\\"", "");
			int position = Integer.parseInt(strSplit[2]);
			
			createRouteStopEntry(routeId, stopId, position);
		}
		br.close();
		
		/*
		 * populate the stopTimeSet
		 * 
		 */
		
		br = new BufferedReader(new FileReader(new File(csvTimeDuration)));
		br.readLine();
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			if(strLin.length() >= 0) {
			String[] strSplit = strLin.split(",");
			String stopId1 = strSplit[1].replaceAll("\\\"", "");
			String stopId2 = strSplit[2].replaceAll("\\\"", "");
			int startTime = Integer.parseInt(strSplit[6].replaceAll("\\\"", ""));
			int endTime = Integer.parseInt(strSplit[7].replaceAll("\\\"", ""));
			int duration = Integer.parseInt(strSplit[9]);
			//System.out.println(stopId1+ " " + stopId2 + " " + startTime + " " + endTime + " " + duration);
			createStopTimeTravelDurationEntry(stopId1, stopId2, startTime, endTime, duration);
			}
		}
		
		/*
		 * populate the findTimeTaken
		 * 
		 */
		
		br = new BufferedReader(new FileReader(new File(csvSchedule)));
		br.readLine();
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			String[] strSplit = strLin.split(",");
			//System.out.println(strSplit[11]);
			
			String routeId = strSplit[11];
			String[] times = strSplit[12].split(":");
			int startTime = Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]);
			
			Route route = routeList.getRoute(routeId);
			if(route != null) {
				System.out.println(route.getTimeForRoute(stopTimeSet, startTime));
			}
		}
		
	}

	@Override
	public void createStopTimeTravelDurationEntry(String stopId1, String stopId2, int startTime, int endTime,
			int duration) {
		StopTime stopTime = stopTimeSet.getStopTime(stopId1, stopId2);
		if(stopTime == null) {
			stopTime = createStopTime(stopId1, stopId2);
			stopTimeSet.addStopTime(stopTime);
		}
		TimeDurationTravel timeDurationTravel = createTimeDurationTravel(startTime, endTime, duration);
		stopTime.addTimeDurationTravel(timeDurationTravel);
	}

	@Override
	public TimeDurationTravel createTimeDurationTravel(int startTime, int endTime, int duration) {
		TimeDurationTravel timeDurationTravel = new TimeDurationTravel();
		timeDurationTravel.setStartTime(startTime);
		timeDurationTravel.setEndTime(endTime);
		timeDurationTravel.setTravelTime(duration);
		return timeDurationTravel;
	}

	@Override
	public StopTime createStopTime(String stopId1, String stopId2) {
		StopTime stopTime = new StopTime();
		Stop stop1 = createNewStop(stopId1);
		Stop stop2 = createNewStop(stopId2);
		stopTime.setStop1(stop1);
		stopTime.setStop2(stop2);
		return stopTime;
	}

	@Override
	public void createRouteStopEntry(String routeId, String stopId, int position) {
		Route route = routeList.getRoute(routeId);
		if(route == null) {
			route = createNewRoute(routeId);
			routeList.addRoute(route);
		}
		Stop stop = createNewStop(stopId);
		route.appendStopAtPosition(stop, position);
	}

	@Override
	public Stop createNewStop(String stopId) {
		Stop stop = new Stop();
		stop.setId(stopId);
		return stop;
	}

	@Override
	public List<Stop> getAllStopsOfRoute(String routeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void appendStopToRoute(Route route, Stop stop) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addStopToRouteAtPosition(Route route, Stop stop, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Route createNewRoute(String routeId) {
		Route route = new Route();
		route.setId(routeId);
		return route;
	}

	@Override
	public StopTimeSet createStopTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void appendStopTimeToSet(StopTimeSet stopTimeSet, StopTime stopTime) {
		// TODO Auto-generated method stub
		
	}
}
