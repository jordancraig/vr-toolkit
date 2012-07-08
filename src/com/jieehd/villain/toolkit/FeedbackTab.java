package com.jieehd.villain.toolkit;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class FeedbackTab extends PreferenceActivity  {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.feedback);
	}
}
