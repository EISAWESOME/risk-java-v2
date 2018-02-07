/**
 * 
 */
package com.ort.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.collectingAndThen;



/**
 * @author tibo
 * Define a region's frontier
 */
public class Frontier {

	/**
	 * Concerned region
	 */
	private String regionEndName;
	
	/**
	 * Possible moves
	 */
	private List<Move> moves = new ArrayList<Move>();

	
	/**
	 * @return the regionEnd
	 */
	public String getRegionEndName() {
		return regionEndName;
	}

	/**
	 * @param regionEndName the regionEnd to set
	 */
	public void setRegionEnd(String regionEndName) {
		this.regionEndName = regionEndName;
	}

	/**
	 * @return the moves
	 */
	public List<Move> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	/**
	 * @param move add a move to the moves list
	 */
	public void addMove(Move move) {
		this.moves.add(move);
	}
	
	
	/**
	 * Printer of Frontier
	 * @return String representation of this Frontier
	 */
	public String toString() {
		return String.format(
				"%s - [ regionEnd = %s, moves = [ %s ] ]",
				this.getClass().getName(), regionEndName,
				moves.stream().map(Move::toString).collect(Collectors.joining(", ")));
	}

	/**
	 *
	 * @param player
	 * @return true if
	 */
	public boolean isWarFrontier(Player player){
		Predicate<Region> p = e -> e.getName().equalsIgnoreCase(this.getRegionEndName());

		//If the player doesnt control a region with that name
		if(!player.getControlledRegions().stream().anyMatch(p)){
			for(Move move : this.getMoves()){
				if(!move.getName().equalsIgnoreCase("Reinforcement")){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public Region getWarTarget(Player player){
		Predicate<Region> p = e -> e.getName().equalsIgnoreCase(this.getRegionEndName());
		Map mapObj = Map.getInstance();
		List<Region> allRegions = mapObj.getRegions();

		//If the player doesnt control a region with that name
		if(!player.getControlledRegions().stream().anyMatch(p)){
			for(Move move : this.getMoves()){
				if(!move.getName().equalsIgnoreCase("Reinforcement")){
					List<Region> target = allRegions.stream()
							.filter(r -> r.getName().equalsIgnoreCase(this.getRegionEndName() ))
							.collect(Collectors.toList());

					return target.get(0);
				}
			}
			return null;
		} else {
			return null;
		}
	}


	
}
