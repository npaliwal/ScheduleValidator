package org.datareader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.model.bus.Bus;
import org.model.routestop.Route;
import org.model.routestop.RouteList;
import org.model.routestop.Stop;
import org.model.stopduration.StopTime;
import org.model.stopduration.StopTimeSet;
import org.model.stopduration.TimeDurationTravel;

public class CSVReaderMain implements IDBHelper{
	RouteList routeList = new RouteList();
	StopTimeSet stopTimeSet = new StopTimeSet();
	ArrayList<Bus> busList = new ArrayList<>();
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(args[0] + "input")));
		String strSplit[];
		String csvRouteDetails = br.readLine();
		strSplit = br.readLine().split(" ");
		ArrayList<Integer> routeDetailsIndices = findIndices(strSplit);
		String routeDetailSeparator = br.readLine();
		String csvRouteStop = br.readLine();
		strSplit = br.readLine().split(" ");
		ArrayList<Integer> routeStopIndices = findIndices(strSplit);
		String routeStopSeparator = br.readLine();
		String csvTimeDuration = br.readLine();
		strSplit = br.readLine().split(" ");
		ArrayList<Integer> routeTimeDurationIndices = findIndices(strSplit);
		String routeTimeDurationSeparator = br.readLine();
		String csvSchedule = br.readLine();
		strSplit = br.readLine().split(" ");
		ArrayList<Integer> scheduleIndices = findIndices(strSplit);
		String scheduleSeparator = br.readLine();
		//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(args[0] + "output.csv"))));
		
		br.close();
		CSVReaderMain obj = new CSVReaderMain();
		
		obj.readFiles(args[0] + csvRouteDetails, routeDetailsIndices, routeDetailSeparator,
				args[0] + csvRouteStop, routeStopIndices, routeStopSeparator,
				args[0] + csvTimeDuration, routeTimeDurationIndices, routeTimeDurationSeparator,
				args[0] + csvSchedule, scheduleIndices, scheduleSeparator,
				args[0] + "output.csv");
		
		//System.setOut(System.out);
	}

	private static ArrayList<Integer> findIndices(String[] strSplit) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i = 0; i < strSplit.length; i++)
			array.add(Integer.parseInt(strSplit[i]) - 1);
		return array;
	}

	private void readFiles(String csvRouteDetails, ArrayList<Integer> routeDetailsIndices, String routeDetailSeparator,
			String csvRouteStop, ArrayList<Integer> routeStopIndices, String routeStopSeparator,
			String csvTimeDuration, ArrayList<Integer> routeTimeDurationIndices, String routeTimeDurationSeparator,
			String csvSchedule, ArrayList<Integer> scheduleIndices, String scheduleSeparator,
			String outputFile) throws IOException {
		/*
		 * populate the routeDetails
		 * */
		ArrayList<Integer> indices;
		BufferedReader br = new BufferedReader(new FileReader(new File(csvRouteDetails)));
		br.readLine();
		String strLin;
		indices = routeDetailsIndices;
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			String[] strSplit = strLin.split(routeDetailSeparator);
			String routeId = strSplit[indices.get(0)].replaceAll("\\\"", "");
			double startLat = Double.parseDouble(strSplit[indices.get(1)].replaceAll("\\\"", ""));
			double startLong = Double.parseDouble(strSplit[indices.get(2)].replaceAll("\\\"", ""));
			double stopLat = Double.parseDouble(strSplit[indices.get(3)].replaceAll("\\\"", ""));
			double stopLong = Double.parseDouble(strSplit[indices.get(4)].replaceAll("\\\"", ""));
			
			createRouteEntry(routeId, startLat, startLong, stopLat, stopLong);
		}
		br.close();
		
		
		/*
		 * populate the routeList
		 * 
		 */
		
		br = new BufferedReader(new FileReader(new File(csvRouteStop)));
		br.readLine();
		indices = routeStopIndices;
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			String[] strSplit = strLin.split(routeStopSeparator);
			String routeId = strSplit[indices.get(0)].replaceAll("\\\"", "");
			//System.out.println(routeId);
			String stopId = strSplit[indices.get(1)].replaceAll("\\\"", "");
			int position = Integer.parseInt(strSplit[indices.get(2)]);
			
			createRouteStopEntry(routeId, stopId, position);
		}
		br.close();
		
		/*
		 * populate the stopTimeSet
		 * 
		 */
		
		br = new BufferedReader(new FileReader(new File(csvTimeDuration)));
		br.readLine();
		indices = routeTimeDurationIndices;
		while((strLin = br.readLine()) != null) {
			if(strLin.length() >= 0) {
				//System.out.println(strLin);
				String[] strSplit = strLin.split(routeTimeDurationSeparator);
			String stopId1 = strSplit[indices.get(0)].replaceAll("\\\"", "");
			String stopId2 = strSplit[indices.get(1)].replaceAll("\\\"", "");
			int startTime = Integer.parseInt(strSplit[indices.get(2)].replaceAll("\\\"", ""));
			int endTime = Integer.parseInt(strSplit[indices.get(3)].replaceAll("\\\"", ""));
			int duration = Integer.parseInt(strSplit[indices.get(4)].replaceAll(",", ""));
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
		indices = scheduleIndices;
		while((strLin = br.readLine()) != null) {
			//System.out.println(strLin);
			String[] strSplit = strLin.split(scheduleSeparator);
			//System.out.println(strSplit[11]);
			
			String busId = strSplit[indices.get(0)];
			Bus bus = createBusEntry(busId);
			
			String routeId = strSplit[indices.get(2)];
			String[] times = strSplit[indices.get(3)].split(":");
			int startTime = Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]);
			
			//System.out.println(routeId);
			Route route = routeList.getRoute(routeId);
			if(route != null)
				bus.addTrip(stopTimeSet, route, startTime);
			//System.out.println(route.getId());
			/*if(route != null) {
				System.out.println("Time " + strSplit[indices.get(1)] + " " + route.getId() + " " + strSplit[indices.get(3)] + " " + route.getTimeForRoute(strSplit[indices.get(1)], stopTimeSet, startTime));
			}
			System.out.println();*/
		}
		
		for(Bus bus : busList) {
			Path file = Paths.get(outputFile);
			Files.write(file, bus.validateTrips(outputFile), Charset.forName("UTF-8"));
		}
	}

	@Override
	public Bus createBusEntry(String busId) {
		for(Bus bus : busList) {
			if(bus.getId().equals(busId))
				return bus;
		}
		Bus bus = new Bus();
		bus.setId(busId);
		busList.add(bus);
		return bus;
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
	public void createRouteEntry(String routeId, double startLat, double startLong, double stopLat, double stopLong) {
		Route route = routeList.getRoute(routeId);
		if(route == null) {
			route = createNewRoute(routeId);
			routeList.addRoute(route);
		}
		route.addCoordinates(startLat, startLong, stopLat, stopLong);
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
