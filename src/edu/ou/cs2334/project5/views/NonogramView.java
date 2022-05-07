package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.views.clues.LeftCluesView;
import edu.ou.cs2334.project5.views.clues.TopCluesView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class is a BorderPane that displays the row clues in the left position,
 * the column clues in the top position, and the cells in the middle position.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class NonogramView extends BorderPane{
	private final static String STYLE_CLASS = "nonogram-view";
	private final static String SOLVED_STYLE_CLASS = "nonogram-view-solved";
	private LeftCluesView leftCluesView;
	private TopCluesView topCluesView;
	private CellGridView cellGridView;
	private HBox bottomHBox;
	private Button loadBtn;
	private Button resetBtn;
	
	/**
	 * Constructs a NonogramView instance by add the style class "nonogram-view" (given as a constant).
	 */
	public NonogramView() {
		getStyleClass().add(STYLE_CLASS);
	}
	
	/**
	 * Initializes the view.
	 * 
	 * @param rowClues  The clues given to help solve the rows.
	 * @param colClues  The clues given to help solve the columns.
	 * @param cellLength  Length of the given cell.
	 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		leftCluesView = new LeftCluesView(rowClues, cellLength, getLength(rowClues));
		topCluesView = new TopCluesView(colClues, cellLength, getLength(colClues));
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);
		
		setLeft(leftCluesView);
		setTop(topCluesView);
		setAlignment(topCluesView, Pos.TOP_RIGHT);
		setCenter(cellGridView);
		
		initBottonHBox();
		setBottom(bottomHBox);
//		setMargin(topCluesView, new Insets(0, 0, 0, width*cellLength));
	}
	
	/**
	 * Returns the max length of the longest array
	 * 
	 * @param clues  Clues of rows or columns.
	 * @return  Returns the max length of the longest array
	 */
	public int getLength(int[][] clues) {
		int max = 0;
		for(int i = 0; i < clues.length; ++i) {
			if(clues[i].length > max)
				max = clues[i].length;
		}
		return max;
	}
	
	/**
	 * Initializes the bottomHBox variable.
	 */
	public void initBottonHBox() {
		bottomHBox = new HBox();
		bottomHBox.setAlignment(Pos.CENTER);
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox.getChildren().addAll(loadBtn, resetBtn);
//		bottomHBox.setSpacing(20);
//		bottomHBox.setPadding(new Insets(8, 0, 8, 0));
//		bottomHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));
	}
	
	/**
	 * Calls getCellView on the CellGridView to get the CellView with the given indices.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Column index of the Cell.
	 * @return  Returns the CellView of the given position.
	 */
	public CellView getCellView(int rowIdx, int colIdx){
		return cellGridView.getCellView(rowIdx, colIdx);
	}
	
	/**
	 * Calls setCellState on the CellGridView to update the state of the CellView with the given indices.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Column index of the Cell.
	 * @param state  State which the cell will change to.
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state){
		cellGridView.setCellState(rowIdx, colIdx, state);
	}
	
	/**
	 * Calls setRowState on the RowCluesView to update the state of the RowClueView with the given indeex.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param solved  Boolean value to change the view to solved or not solved.
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}
	
	/**
	 * Calls setColState on the ColCluesView to update the state of the ColClueView with the given index.
	 * 
	 * @param colIdx  Column index of the Cell.
	 * @param solved  Boolean value to change the view to solved or not solved.
	 */
	public void setColClueState(int colIdx, boolean solved) {
		topCluesView.setState(colIdx, solved);
	}
	
	/**
	 * If the puzzle is solved, the method adds the style class "nonogram-view-solved".
	 * Otherwise, it removes all occurrences of this style class.
	 * 
	 * @param solved  Boolean value to change the view to solved or not solved.
	 */
	public void setPuzzleState(boolean solved) {
		if(solved) {
			getStyleClass().add(SOLVED_STYLE_CLASS);
		}
		else
			getStyleClass().remove(SOLVED_STYLE_CLASS);
	}
	
	/**
	 * Returns the load button.
	 * 
	 * @return  Returns the load button.
	 */
	public Button getLoadButton() {
		return loadBtn;
	}
	
	/**
	 * Returns the reset button.
	 * 
	 * @return  Returns the reset button.
	 */
	public Button getResetButton() {
		return resetBtn;
	}
	
	/**
	 * Show a victory alert.
	 */
	public void showVictoryAlert() {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setTitle("Puzzle Solved");
		a.setHeaderText("Congratulations!");
		a.setContentText("You Win!");
		a.showAndWait();
	}
}
