package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import javafx.scene.layout.GridPane;

/**
 * This class is a GridPane that displays the cell states.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class CellGridView extends GridPane{
	private final static String STYLE_CLASS = "cell-grid-view";
	private CellView[][] cellViews;
	
	/**
	 * Creates a two-dimensional array of CellViews and add them to the GridPane.
	 * 
	 * @param numRows  Number of cellView rows.
	 * @param numCols  Number of cellView columns.
	 * @param cellLength  Length of the given cell.
	 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		getStyleClass().add(STYLE_CLASS);
		initCells(numRows, numCols, cellLength);
	}
	
	/**
	 * Initializes the cells of this view.
	 * 
	 * @param numRows  Number of cellView rows.
	 * @param numCols  Number of cellView columns.
	 * @param cellLength  Length of the given cell.
	 */
	public void initCells(int numRows, int numCols, int cellLength) {
		getChildren().clear();
		cellViews = new CellView[numRows][numCols];
		for(int r = 0; r < numRows; ++r) {
			for(int c = 0; c < numCols; ++c) {
				CellView cell = new CellView(cellLength);
				cellViews[r][c] = cell;
				add(cell,c,r);
			}
//			addRow(r,cellViews[r]);
		}
	}
	
	/**
	 * Gets the CellView using the given indices.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Column index of the Cell.
	 * @return  Returns the CellView of the given position.
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellViews[rowIdx][colIdx];
	}
	
	/**
	 * Updates the state of the CellView with the given indices.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Column index of the Cell.
	 * @param state  State which the cell will change to.
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellViews[rowIdx][colIdx].setState(state);
	}
}
