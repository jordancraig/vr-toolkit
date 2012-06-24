package com.jieehd.villain.toolkit;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class FetchFile extends AsyncTask<String, String, String> {
  
  protected String doInBackground(String... params) {
    final String ROMname = params[0];
    final String URL = params[1];
    try {
      URL getUrl = new URL(URL);
      URLConnection ucon = getUrl.openConnection();
      final int lengthOfFile = ucon.getContentLength();
      ucon.connect();
      InputStream input = new BufferedInputStream(getUrl.openStream());
      OutputStream output = new FileOutputStream(ROMTab.PATH + ROMname + ".zip");
      byte data[] = new byte[1024];
      int current;
      long total = 0;
      while ((current = input.read(data)) != -1) {
             output.write(data, 0, current);
        total += current;
        // updateProgress(total, lengthOfFile);
        }
      output.flush();
      output.close();
      input.close();
    } catch (Exception e) {
      // TODO: handle exception
    }  
  }
  
  protected void onProgressUpdate(final long total, final int lengthOfFile) {
  }
  
  protected void onPostExecute() {
    
  }
}