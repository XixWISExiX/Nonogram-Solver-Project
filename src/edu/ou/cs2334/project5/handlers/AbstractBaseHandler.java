package edu.ou.cs2334.project5.handlers;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * This class represents a general handler involving file selection.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class AbstractBaseHandler {
	/**
	 * Window of the given case.
	 */
	protected Window window;
	
	/**
	 * FileChooser of the given case.
	 */
	protected FileChooser fileChooser;
	
	/**
	 * Constructor which assigns its instace variables.
	 * 
	 * @param window  Window of the given case.
	 * @param fileChooser  FileChooser of the given case.
	 */
	protected AbstractBaseHandler(Window window, FileChooser fileChooser) {
		this.window = window;
		this.fileChooser = fileChooser;
	}
}