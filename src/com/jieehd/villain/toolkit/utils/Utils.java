package com.jieehd.villain.toolkit.utils;

import android.os.Build;

import com.jieehd.villain.toolkit.utils.ShellCommand.CommandResult;

public class Utils {
	private static final String NULL = null;
	public static final String LOGTAG = "VillainToolkit";

	public static String getModVersion() {
		ShellCommand cmd = new ShellCommand();
		CommandResult modversion = cmd.su.runWaitFor("getprop ro.modversion");
		if(modversion.stderr.equals(NULL)) {
			return Build.DEVICE.toUpperCase();
		} else {
			return modversion.stdout;
		}
	}
}