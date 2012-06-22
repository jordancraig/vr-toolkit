package com.jieehd.villain.toolkit.utils;

import android.os.Build;

import com.jieehd.villain.toolkit.utils.ShellCommand.CommandResult;

public class Utils {
	public static final String LOGTAG = "VillainToolkit";

	public static String getAOKPVersion() {
		ShellCommand cmd = new ShellCommand();
		CommandResult aokpversion = cmd.su.runWaitFor("getprop ro.aokp.version");
		if(!aokpversion.success())
			getCMVersion();
		return aokpversion.stdout;
	}
	public static String getCMVersion() {
		ShellCommand cmd = new ShellCommand();
		CommandResult cmversion = cmd.su.runWaitFor("getprop ro.cm.version");
		if(!cmversion.success())
			return Build.DISPLAY;
		return cmversion.stdout;
	}
}