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
	private int nbTroupes;

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

	public void setNbTroupes(int nbTroupes){
		this.nbTroupes = nbTroupes;
	}

	public int getNbTroupes(){
		return this.nbTroupes;
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
		this.controlledRegions.remove(region);

	}

	public Player(String name, int order, int nbTroupes) {
		this.name = name;
		this.order = order;
		this.nbTroupes = nbTroupes;
	}
}
