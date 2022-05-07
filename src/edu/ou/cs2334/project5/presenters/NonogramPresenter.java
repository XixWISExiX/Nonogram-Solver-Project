package edu.ou.cs2334.project5.presenters;

import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.handlers.OpenHandler;
import edu.ou.cs2334.project5.interfaces.Openable;
import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.models.NonogramModel;
import edu.ou.cs2334.project5.views.NonogramView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The graphical view and model data are connected by this class. Because the size of a NonogramModel is immutable,
 * any size adjustments (rows or columns) require re-initializing the model and view information. The model reference
 * is completely updated. The view reference stays the same, though underlying data within the view undergoes change.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class NonogramPresenter implements Openable{
	private NonogramView view;
	private NonogramModel model;
	private int cellLength;
	private final static String DEFAULT_PUZZLE = "puzzles/space-invader.txt";
	
	/**
	 * Constructs and initializes the presenter.
	 * 
	 * @param cellLength  Length of the given cell.
	 * @throws IOException  Throws this exception if the file is not found.
	 */
	public NonogramPresenter(int cellLength) throws IOException {
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		this.cellLength = cellLength;
		initializePresenter();
	}
	
	private void initializePresenter() {
		initializeView();
		bindCellViews();
		synchronize();
		configureButtons();
	}
	
	/**
	 * Initializes the view.
	 */
	public void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if(getWindow() != null)
			getWindow().sizeToScene();
	}
	
	/**
	 * For each cell, the method adds an EventHandler to handle mouse clicks.
	 */
	public void bindCellViews() {
		for(int r = 0; r < model.getNumRows(); r++) {
			for(int c = 0; c < model.getNumCols(); c++) {
				final int row = r;
				final int col = c;
				view.getCellView(row, col).setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						MouseButton button = event.getButton();
				        if(button==MouseButton.PRIMARY)
				        	handleLeftClick(row,col);
				        if(button==MouseButton.SECONDARY)
				        	handleRightClick(row,col);
					}
				});
			}
		}
	}
	
	/**
	 * Updates the cell state at this grid position.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Col index of the Cell.
	 */
	public void handleLeftClick(int rowIdx, int colIdx) {
		if(model.getCellState(rowIdx, colIdx) == CellState.EMPTY || model.getCellState(rowIdx, colIdx) == CellState.MARKED)
			updateCellState(rowIdx, colIdx, CellState.FILLED);
		else
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
	}
	
	/**
	 * Updates the cell state at this grid position.
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Col index of the Cell.
	 */
	public void handleRightClick(int rowIdx, int colIdx) {
		if(model.getCellState(rowIdx, colIdx) == CellState.EMPTY || model.getCellState(rowIdx, colIdx) == CellState.FILLED)
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		else
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
	}
	
	/**
	 * Updates the model (but doesn't update the view if nothing changed).
	 * 
	 * @param rowIdx  Row index of the Cell.
	 * @param colIdx  Col index of the Cell.
	 * @param state  State which the cell will change to.
	 */
	public void updateCellState(int rowIdx, int colIdx, CellState state) {
		CellState s = model.getCellState(rowIdx, colIdx);
		model.setCellState(rowIdx, colIdx, state);
		if(state != s) {
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
		}
		
		if(model.isSolved())
			processVictory();
	}
	
	/**
	 * Synchronizes the state of the model and the view.
	 */
	public void synchronize() {
		for(int r = 0; r < model.getNumRows(); r++) {
			for(int c = 0; c < model.getNumCols(); c++) {
				view.setCellState(r, c, model.getCellState(r, c));
				view.setColClueState(c, model.isColSolved(c));
				view.setRowClueState(r, model.isRowSolved(r));
			}
		}
		view.setPuzzleState(model.isSolved());
	}
	
	/**
	 * Handles player victory.
	 */
	public void processVictory() {
		removeCellViewMarks();
		synchronize();
		view.showVictoryAlert();
	}
	
	/**
	 * Removes all marks (red X's) from the view.
	 */
	public void removeCellViewMarks() {
		for(int r = 0; r < model.getNumRows(); r++) {
			for(int c = 0; c < model.getNumCols(); c++) {
				if(model.getCellState(r, c) == CellState.MARKED)
					view.setCellState(r, c, CellState.EMPTY);
			}
		}
	}
	
	/**
	 * Sets the actions for the load and reset buttons.
	 */
	public void configureButtons() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File("."));
		view.getLoadButton().setOnAction(new OpenHandler(getWindow(), fileChooser, this));
		
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
                resetPuzzle();
            }
		});
	}
	
	/**
	 * Clears the model using the reset method and synchronize the model/view.
	 */
	public void resetPuzzle() {
		model.resetCells();
		synchronize();
	}
	
	/**
	 * Returns the pane of the presenter.
	 * 
	 * @return  Returns the Pane.
	 */
	public Pane getPane() {
		return view;
	}
	
	/**
	 * Returns the window of the presenter.
	 * 
	 * @return  Returns the Window.
	 */
	public Window getWindow() {
		try {
			return view.getScene().getWindow();
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Calls the model's saveToFile method using the given filename.
	 */
	public void open(File file) throws IOException {
		model = new NonogramModel(file);
		initializePresenter();
	}
}
