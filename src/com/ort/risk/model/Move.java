package com.ort.risk.model;

import com.ort.risk.game.actions.WarAction;

/**
 * @author tibo
 * Define a possible move between regions
 */
public class Move {
	
	/**
	 * The name of the move
	 */
	private String name;
	
	
	/**
	 * name getter
	 * @return name of the move
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Printer of Move
	 * @return String representation of this Move
	 */
	public String toString() {
		return String.format(
				"%s - [ name = %s ]",
				this.getClass().getName(), this.name);
	}

	public Object[] execute(Region atkRegion, Player atkPlayer, Region defRegion, int nbAttack, int nbDef){
		Object[] ret = null;
		switch(this.getName()){
			case "Reinforcement":
				System.out.println(atkPlayer.getName() + " utilise 'Renforcement'");
				System.out.println("Mais cela n'a aucun effet...\n");
				break;
			case "Assault":
				//System.out.println(atkPlayer.getName() + " utilise 'Assaut'");
				ret = WarAction.Assault(atkRegion, atkPlayer, defRegion, nbAttack, nbDef);
				//System.out.println("Mais cela n'a aucun effet...\n");
				break;
			default:
				System.out.println("Action inconnu ???\n");
		}
		return ret;
	}
	
}
