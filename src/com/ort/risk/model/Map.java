/**
 * 
 */
package com.ort.risk.model;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tibo
 *  + un petit peu clément wallah
 * Define a map
 */
public class Map {
	
	/**
	 * The map's name
	 */
	private String name;
	
	/**
	 * Url leading to the map's image
	 */
	private String img;
	
	/**
	 * Every zones in the map
	 */
	private List<Zone> zones = new ArrayList<Zone>();
	
	/**
	 * Every moves possible
	 */
	private List<Mode> modes = new ArrayList<Mode>();
	
	/**
	 * Minimum number of reinforcement per tour
	 */
	private int nbMinReinforcement;
	
	/**
	 * Reinforcement bonus divider
	 */
	private int divider;

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the zones
	 */
	public List<Zone> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

    /**
     * @param zone the zones to add
     */
    public void addZone(Zone zone) {
        this.zones.add(zone);
    }

	/**
	 * @return the modes
	 */
	public List<Mode> getModes() {
		return modes;
	}

	/**
	 * @param modes the moves to set
	 */
	public void setModes(List<Mode> modes) {
		this.modes = modes;
	}

	/**
	 * @param mode add a mode to the existing list
	 */
	public void addMode(Mode mode) {
		this.modes.add(mode);
	}

	/**
	 * @return the nbMinReinforcement
	 */
	public int getNbMinReinforcement() {
		return nbMinReinforcement;
	}

	/**
	 * @param nbMinReinforcement the nbMinReinforcement to set
	 */
	public void setNbMinReinforcement(int nbMinReinforcement) {
		this.nbMinReinforcement = nbMinReinforcement;
	}

	/**
	 * @return the divider
	 */
	public int getDivider() {
		return divider;
	}

	/**
	 * @param divider the divider to set
	 */
	public void setDivider(int divider) {
		this.divider = divider;
	}
	
	
	/**
	 * Printer of Map
	 * @return String representation of this Map
	 */

	private Map()
	{}

	private static Map INSTANCE = new Map();

	public static Map getInstance(){
		return INSTANCE;
	}

	public String toString() {
		return String.format(
				"%s - [ name = %s, img = %s, nbMinReinforcement = %d, divider = %d, modes = [ %s ], zones = [ %s ] ]",
				this.getClass().getName(), this.name, this.img, this.nbMinReinforcement, this.divider,
				modes.stream().map(Mode::toString).collect(Collectors.joining(", ")),
				zones.stream().map(Zone::toString).collect(Collectors.joining(", ")));
	}
	
}
