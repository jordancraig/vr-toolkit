package com.jieehd.villain.toolkit;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Downloads extends PreferenceActivity {
	
	@Override
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.downloads);
	}

}
