package edu.ou.cs2334.project5.models;

/**
 * Enum to switch states from empty, filled, and marked
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public enum CellState {
	/**
	 * Empty cell.
	 */
	EMPTY,
	/**
	 * Filled cell.
	 */
	FILLED,
	/**
	 * Marked cell.
	 */
	MARKED;
	
	/**
	 * Determines the state of the CellState.
	 * 
	 * @param state  State of the CellState.
	 * @return  Returns true if the state is filled and false otherwise.
	 */
	public static boolean toBoolean(CellState state) {
		if(state == FILLED) return true; else return false;
	}
}
