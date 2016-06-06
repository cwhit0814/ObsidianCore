package com.ObsidianReach.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileDownload {

	public static void dlFile(String DLURL, String FileName) throws IOException {

		URL url = new URL(DLURL);
		InputStream is = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(FileName));
			is = url.openStream();
		} catch (IOException exp) {
			exp.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception exp) {
			}
			try {
				os.close();
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
	
	public static void download(String fileURL, String destinationDirectory) throws IOException {
        // File name that is being downloaded
        String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/")+1);
         
        // Open connection to the file
        URL url = new URL(fileURL);
        InputStream is = url.openStream();
        // Stream to the destionation file
        FileOutputStream fos = new FileOutputStream(destinationDirectory + "/" + downloadedFileName);
  
        // Read bytes from URL to the local file
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
         
        System.out.print("Downloading " + downloadedFileName);
        while ((bytesRead = is.read(buffer)) != -1) {
            System.out.print(".");  // Progress bar :)
            fos.write(buffer,0,bytesRead);
        }
        System.out.println("done!");
  
        // Close destination stream
        fos.close();
        // Close URL stream
        is.close();
    }  
}