package edu.ou.cs2334.project5.interfaces;

import java.io.File;
import java.io.IOException;

/**
 * Interface made to open files.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public interface Openable {
	
	/**
	 * Opens the file.
	 * 
	 * @param file  The file which can be opened.
	 * @throws IOException  Throws this exception if the file is not found.
	 */
	void open(File file) throws IOException;
}