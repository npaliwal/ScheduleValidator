package org.model.routestop;

import java.util.ArrayList;

public class RouteList {

	private ArrayList<Route> list = new ArrayList<>();

	public ArrayList<Route> getList() {
		return list;
	}

	public void setList(ArrayList<Route> list) {
		this.list = list;
	}

	public Route getRoute(String routeId) {
		for(Route route : list) {
			if(route.getId().equals(routeId))
				return route;
		}
		return null;
	}

	public void addRoute(Route route) {
		list.add(route);
	}
	
	
}
