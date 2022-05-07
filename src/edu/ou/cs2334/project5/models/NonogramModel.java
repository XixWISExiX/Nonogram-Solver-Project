package edu.ou.cs2334.project5.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class encapsulates the state and rules of the game. It stores arrays with the row clues,
 * column clues, and cell states (EMPTY, FILLED, or MARKED).
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class NonogramModel {

	private static final String DELIMITER = " ";
	private static final int IDX_NUM_ROWS = 0;
	private static final int IDX_NUM_COLS = 1;

	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;
	
	/**
	 * Initializes the object using the given arrays of row and column clues.
	 * 
	 * @param rowClues  The clues given to help solve the rows.
	 * @param colClues  The clues given to help solve the columns.
	 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);

		cellStates = initCellStates(getNumRows(), getNumCols());
	}
	
	/**
	 * Initializes the object using the row and column clues in the given file.
	 * 
	 * @param file  The file which contains the data necessary to make a Nonogram.
	 * @throws IOException  Throws this exception if the file is not found.
	 */
	public NonogramModel(File file) throws IOException {
		// Number of rows and columns
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		int numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		int numCols = Integer.parseInt(fields[IDX_NUM_COLS]);
		
		rowClues = readClueLines(reader, numRows);
		
		colClues = readClueLines(reader, numCols);
		
		cellStates = initCellStates(getNumRows(), getNumCols());		
			
		// Close reader
		reader.close();
	}

	/**
	 * Initializes the object using the row and column clues in the text file with the given name.
	 * 
	 * @param filename  The file location which contains the data necessary to make a Nonogram.
	 * @throws IOException  Throws this exception if the file is not found.
	 */
	public NonogramModel(String filename) throws IOException {
		this(new File(filename));
	}
	
	/* Helper methods */
	
	/**
	 * Returns the number of rows.
	 * 
	 * @return  Returns the number of rows.
	 */
	public int getNumRows() {
		return rowClues.length;
	}
	
	/**
	 * Returns the number of columns.
	 * 
	 * @return  Returns the number of columns.
	 */
	public int getNumCols() {
		return colClues.length;
	}
	
	/**
	 * Returns the state of the cell with the given row and column indices.
	 * 
	 * @param rowIdx  Row index of the CellState.
	 * @param colIdx  Column index of the CellState.
	 * @return  Returns the state of the cell with the given row and column indices.
	 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}
	
	/**
	 * Returns the boolean state of the cell with the given row and column indices.
	 * 
	 * @param rowIdx  Row index of the CellState.
	 * @param colIdx  Column index of the CellState.
	 * @return  Returns the boolean state of the cell with the given row and column indices.
	 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return CellState.toBoolean(getCellState(rowIdx, colIdx));
	}
	
	/**
	 * Sets the state of the cell with the given indices.
	 * 
	 * @param rowIdx  Row index of the CellState.
	 * @param colIdx  Column index of the CellState.
	 * @param state  State which the cell will change to.
	 * @return  Returns false if the CellState doesn't change and returns true if it does.
	 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if(state == null || cellStates[rowIdx][colIdx] == state || isSolved())
			return false;
		cellStates[rowIdx][colIdx] = state;
		return true;
	}
	
	/**
	 * Returns the row clues.
	 * 
	 * @return  Returns the row clues.
	 */
	public int[][] getRowClues(){
		return deepCopy(rowClues);
	}
	
	/**
	 * Returns the columns clues.
	 * 
	 * @return  Returns the columns clues.
	 */
	public int[][] getColClues(){
		return deepCopy(colClues);
	}
	
	/**
	 * Returns a copy of the row clue with the given index.
	 * 
	 * @param rowIdx  The row index of the row clue array.
	 * @return  Returns a copy of the row clue with the given index.
	 */
	public int[] getRowClue(int rowIdx) {
		return Arrays.copyOf(rowClues[rowIdx], (rowClues[rowIdx].length));
	}
	
	/**
	 * Returns a copy of the col clue with the given index.
	 * 
	 * @param colIdx  The col index of the col clue array.
	 * @return  Returns a copy of the col clue with the given index.
	 */
	public int[] getColClue(int colIdx) {
		return Arrays.copyOf(colClues[colIdx], (colClues[colIdx].length));
	}
	
	/**
	 * Returns true if the row with the given index is solved. Otherwise, return false.
	 * 
	 * @param rowIdx  The row index of the row clue array.
	 * @return  Returns true if the row with the given index is solved. Otherwise, return false.
	 */
	public boolean isRowSolved(int rowIdx) {
		if(Arrays.equals(projectCellStatesRow(rowIdx), rowClues[rowIdx]))
			return true;
		return false;
	}
	
	/**
	 * Returns true if the col with the given index is solved. Otherwise, return false.
	 * 
	 * @param colIdx  The col index of the col clue array.
	 * @return  Returns true if the col with the given index is solved. Otherwise, return false.
	 */
	public boolean isColSolved(int colIdx) {
		if(Arrays.equals(projectCellStatesCol(colIdx), colClues[colIdx]))
			return true;
		return false;
	}
	
	/**
	 * Returns true if the puzzle is solved; otherwise, return false.
	 * 
	 * @return  Returns true if the puzzle is solved; otherwise, return false.
	 */
	public boolean isSolved() {
		int numSolved = 0;
		int numTested = 0;
		for(int r = 0; r < rowClues.length; ++r) {
			if(isRowSolved(r))
				numSolved++;
			numTested++;
		}
		for(int c = 0; c < colClues.length; ++c) {
			if(isColSolved(c))
				numSolved++;
			numTested++;
		}
		if(numSolved == numTested)
			return true;
		return false;
	}
	
	/**
	 * Changes the state of all cells to EMPTY.
	 */
	public void resetCells() {
		for(int r = 0; r < getNumRows(); r++) {
			for(int c = 0; c < getNumCols(); c++) {
				cellStates[r][c] = CellState.EMPTY;
			}
		}
	}
	
	/**
	 * Returns the nonogram numbers of the given array of cell states.
	 * 
	 * @param cells  The different cell values which the projecter trys to project.
	 * @return  Returns the nonogram values of a given number of cell states.
	 */
	public static List<Integer> project(boolean[] cells){
		List<Integer> list = new LinkedList<Integer>();
		int count = 0;
		boolean called = false;
		for(int i = 0; i < cells.length; ++i) {
			if(cells[i]) {
				count++;
				called = true;
			}
			else {
				if(count > 0) {
					list.add(count);
					count = 0;
					called = false;
				}
			}
		}
		if(called || list.size() == 0)
			list.add(count);
		return list;
	}
	
	/**
	 * Returns the projection of the row with the given index.
	 * 
	 * @param rowIdx  The index of that given row.
	 * @return  Returns the projection of a given row.
	 */
	public int[] projectCellStatesRow(int rowIdx){
		boolean[] row = new boolean[getNumCols()];
		for(int i = 0; i < row.length; ++i) {
			row[i] = getCellStateAsBoolean(rowIdx, i);
		}
		List<Integer> listRow = project(row);
		int[] arrRow = new int[listRow.size()];
		for(int i = 0; i < arrRow.length; ++i) {
			arrRow[i] = listRow.get(i);
		}
		return arrRow;
	}
	
	/**
	 * Returns the projection of the column with the given index.
	 * 
	 * @param colIdx  The index of that given column.
	 * @return  Returns the projection of a given column.
	 */
	public int[] projectCellStatesCol(int colIdx){
		boolean[] col = new boolean[getNumRows()];
		for(int i = 0; i < col.length; ++i) {
			col[i] = getCellStateAsBoolean(i, colIdx);
		}
		List<Integer> listCol = project(col);
		int[] arrCol = new int[listCol.size()];
		for(int i = 0; i < arrCol.length; ++i) {
			arrCol[i] = listCol.get(i);
		}
		return arrCol;
	}
	
	// This is implemented for you
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];
		
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
		// Return the result
		return cellStates;
	}
	
	private static int[][] deepCopy(int[][] array) {
		// You can do this in under 10 lines of code. If you ask the internet
		// "how do I do a deep copy of a 2d array in Java," be sure to cite
		// your source.
		// Note that if we used a 1-dimensional array to store our arrays,
		// we could simply use Arrays.copyOf directly without this helper
		// method.
		// Do not ask about this on Discord. You can do this on your own. :)
		
		// Credit to Rorick on stack overflow
		if (array == null)
	        return null;
	    final int[][] result = new int[array.length][];
	    for (int i = 0; i < array.length; i++) {
	        result[i] = Arrays.copyOf(array[i], array[i].length);
	    }
	    return result;
	}
	
	// This method is implemented for you. You need to figure out how it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines)
			throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];
		
		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();
			
			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);
			
			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}
			
			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}
		
		// Return the result
		return clueLines;
	}
	
}
