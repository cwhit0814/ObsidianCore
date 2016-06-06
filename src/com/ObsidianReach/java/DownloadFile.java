package com.ObsidianReach.java;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadFile {

	public static void main() throws IOException {

		String fileName = "plugins/ObsidianCore.jar"; // The file that will be saved on your computer
		URL link = new URL("https://dl.dropbox.com/s/c9849g3ksex0z1p/ObsidianCore.jar"); //The file that you want to download

		// Code to download
		InputStream in = new BufferedInputStream(link.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in.read(buf))) {
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(response);
		fos.close();
		// End download code

		System.out.println("Finished");

	}

}