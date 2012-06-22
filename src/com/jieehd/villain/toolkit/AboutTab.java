package com.jieehd.villain.toolkit;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class AboutTab extends PreferenceActivity {
    /** Called when the activity is first created. */
	
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);
        
        Preference about_vr_toolkit = (Preference) findPreference("about_toolkit");
        about_vr_toolkit.setSummary(" Copyright (C) 2012 - VillainROM \n Fully open-source \n Tap to visit our website");
        
        about_vr_toolkit.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
                String url = "http://www.villainrom.co.uk";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
				return true;
			}
        	
        });
        
        PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String version = pInfo.versionName;
        
        Preference about_app_version = (Preference) findPreference("about_app");
        about_app_version.setSummary(version);
        
        about_app_version.setOnPreferenceClickListener(new OnPreferenceClickListener() {
        	
        	@Override
        	public boolean onPreferenceClick(Preference preference) {
        		//TODO Auto-generated method stub
				return true;
        	}
        });
        
        Preference follow_twitter = (Preference) findPreference("follow_twitter_pref");
        follow_twitter.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				// TODO Auto-generated method stub
                String url = "http://www.twitter.com/jordancraig94";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
				return false;
			}
        	
        });
    }
}
