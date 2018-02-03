/**
 * 
 */
package com.ort.risk.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author tibo
 * Define a player
 */
public class Player {

	private String name;
	private int order;
	private List<Region> controlledRegions  = new ArrayList<Region>();

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}


	public void setOrder(int order){
		this.order = order;
	}

	public int getOrder(){
		return this.order;
	}

	public List<Region> getControlledRegions(){
		return this.controlledRegions;
	}

	public void setControlledRegions(List<Region> controlledRegions){
		this.controlledRegions = controlledRegions;
	}

	public void addControlledRegion(Region region){
		this.controlledRegions.add(region);
	}

	public void removeControlledRegion(Region region){
		for(Iterator<Region> iter = this.controlledRegions.listIterator(); iter.hasNext();){
			Region a = iter.next();
			if(a.getName().equals(region.getName())){
				iter.remove();
			}
		}
	}

	public Player(String name, int order) {
		this.name = name;
		this.order = order;
	}
}
