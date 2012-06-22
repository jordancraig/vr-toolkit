package com.jieehd.villain.toolkit;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class AddFavorite extends PreferenceActivity {
	
	public static Context cx;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.accounts_screen);
	}
	
	@SuppressWarnings("deprecation")
	public void addPref(String...params) {
		  
		String tweak_name = params[0];
	  	Preference pref = new Preference(cx);
	  	pref.setTitle(tweak_name);
	  	pref.setIcon(R.drawable.ic_download_default);
	  	((PreferenceScreen) findPreference("accounts_screen")).addPreference(pref);
	  		  
	}

}
