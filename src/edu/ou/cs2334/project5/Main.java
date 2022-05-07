package edu.ou.cs2334.project5;

import java.io.IOException;

import edu.ou.cs2334.project5.presenters.NonogramPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The driver class that runs the application with given arguments.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class Main extends Application{
	private final static int IDX_CELL_SIZE = 0;
	private final static int DEFAULT_CELL_SIZE = 30;
	
	/**
	 * Calls the inherited launch method.
	 * 
	 * @param args  Reads the arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Reads the given arguments and starts the application.
	 */
	public void start(Stage primaryStage) throws IOException {
		NonogramPresenter presenter;
		if(getParameters().getUnnamed().isEmpty())
			presenter = new NonogramPresenter(DEFAULT_CELL_SIZE);
		else {
			int cellSize = Integer.parseInt(getParameters().getUnnamed().get(IDX_CELL_SIZE));
			presenter = new NonogramPresenter(cellSize);
		}
		Scene scene = new Scene(presenter.getPane());

		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram++");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
