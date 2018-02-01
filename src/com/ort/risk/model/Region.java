/**
 * 
 */
package com.ort.risk.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tibo
 * Define a region in a zone
 */
public class Region {

	/**
	 * Name of the region
	 */
	private String name;
	
	/**
	 * Bonus given by the region
	 */
	private int bonus;
	
	/**
	 * Every frontier of the region
	 */
	private List<Frontier> frontiers;

	
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
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the frontiers
	 */
	public List<Frontier> getFrontiers() {
		return frontiers;
	}

	/**
	 * @param frontiers the frontiers to set
	 */
	public void setFrontiers(List<Frontier> frontiers) {
		this.frontiers = frontiers;
	}
	
	
	/**
	 * Printer of Region
	 * @return String representation of this Frontier
	 */
	public String toString() {
		return String.format(
				"%s - [ name = %s, bonus =%d, frontiers = [ %s ] ]",
				this.getClass().getName(), this.name, this.bonus,
				frontiers.stream().map(Frontier::toString).collect(Collectors.joining(", ")));
	}
	
}
