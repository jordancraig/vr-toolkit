package com.jieehd.villain.toolkit.utils;

import android.os.AsyncTask;
import android.os.Build;

import com.jieehd.villain.toolkit.utils.ShellCommand.CommandResult;

public class Utils {

	public static final String LOGTAG = "VillainToolkit";


	public class Read extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			ShellCommand cmd = new ShellCommand();

			CommandResult modversion = cmd.sh.runWaitFor("getprop ro.modversion");

			if(modversion.stdout.equals("")) {				
				CommandResult cmversion = cmd.sh.runWaitFor("getprop ro.cm.version");

				if(cmversion.stdout.equals("")) {
					CommandResult aokpversion = cmd.sh.runWaitFor("getprop ro.aokp.version");

					if(aokpversion.stdout.equals(""))						
						return Build.DISPLAY;

					else					
						return aokpversion.stdout;

				} else {					
					return cmversion.stdout;
				}
			}

			return modversion.stdout;
		}

	}
}