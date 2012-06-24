package com.jieehd.villain.toolkit.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

//usually, subclasses of AsyncTask are declared inside the activity class.
//that way, you can easily modify the UI thread from here
public class DownloadFiles extends AsyncTask<String, Integer, String> {
	public static String mPath;
 @Override
 protected String doInBackground(String... sUrl) {
     try {
         URL url = new URL(sUrl[0]);
         URLConnection connection = url.openConnection();
         connection.connect();
         // this will be useful so that you can show a typical 0-100% progress bar
         int fileLength = connection.getContentLength();

         // download the file
         InputStream input = new BufferedInputStream(url.openStream());
         OutputStream output = new FileOutputStream(mPath);

         byte data[] = new byte[1024];
         long total = 0;
         int count;
         while ((count = input.read(data)) != -1) {
             total += count;
             // publishing the progress....
             publishProgress((int) (total * 100 / fileLength));
             output.write(data, 0, count);
         }

         output.flush();
         output.close();
         input.close();
     } catch (Exception e) {
     }
     return null;
 }
}
