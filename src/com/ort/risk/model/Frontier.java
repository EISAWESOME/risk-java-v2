/**
 * 
 */
package com.ort.risk.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tibo
 * Define a region's frontier
 */
public class Frontier {

	/**
	 * Concerned region
	 */
	private Region regionEnd;
	
	/**
	 * Possible moves
	 */
	private List<Move> moves;

	
	/**
	 * @return the regionEnd
	 */
	public Region getRegionEnd() {
		return regionEnd;
	}

	/**
	 * @param regionEnd the regionEnd to set
	 */
	public void setRegionEnd(Region regionEnd) {
		this.regionEnd = regionEnd;
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
	 * Printer of Frontier
	 * @return String representation of this Frontier
	 */
	public String toString() {
		return String.format(
				"%s - [ regionEnd = %s, moves = [ %s ] ]",
				this.getClass().getName(), regionEnd.toString(),
				moves.stream().map(Move::toString).collect(Collectors.joining(", ")));
	}
	
}
