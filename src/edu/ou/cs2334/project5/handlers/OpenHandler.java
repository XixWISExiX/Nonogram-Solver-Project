package edu.ou.cs2334.project5.handlers;

import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Implements the EventHandler interface to the open feature and must implement the handle method this then
 * uses the FileChooser instance variable to show an open dialog to the user.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent>{
	private Openable opener;
	
	/**
	 * Constructs a OpenHandler to then activate the open function when the open button is pressed.
	 * 
	 * @param window  Window of the given case.
	 * @param fileChooser  ileChooser of the given case.
	 * @param opener  Allows the class to open.
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
		super(window, fileChooser);
		this.opener = opener;
	}

	/**
	 * Uses the FileChooser instance variable to show an open dialog to the user
	 * and then implement that open.
	 */
	@Override
	public void handle(ActionEvent event) {
		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
            try {
				opener.open(file);
			} catch (IOException e) {
			}
        }
	}
}
