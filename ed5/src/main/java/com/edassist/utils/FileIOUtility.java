package com.edassist.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class that helps to write file to disk
 */
public class FileIOUtility {

	/**
	 * Writes file to Disk
	 *
	 * @param name the name of the file
	 * @param path the path/directory where the file will be saved
	 * @param content the contnent of the file in a byte array
	 * @throws FileNotFoundException
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void writeFile(String name, String path, byte[] content) throws FileNotFoundException, SecurityException, IOException {

		FileOutputStream out = null;
		try {
			File file = new File(path, name);
			out = new FileOutputStream(file);
			out.write(content);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * Writes file to Disk
	 *
	 * @param name the name of the file
	 * @param path the path/directory where the file will be saved
	 * @param content the contnent of the file in a string
	 * @throws FileNotFoundException
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void writeFile(String name, String path, String content) throws FileNotFoundException, SecurityException, IOException {

		FileWriter out = null;
		try {
			File file = new File(path, name);
			out = new FileWriter(file);
			out.write(content);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
