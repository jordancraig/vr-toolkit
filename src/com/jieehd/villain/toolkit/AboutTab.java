package com.jieehd.villain.toolkit;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.jieehd.villain.toolkit.stats.AnonymousStats;

public class AboutTab extends PreferenceFragment {
    /** Called when the activity is first created. */
	
	public static Context cx;
	
	public class DisplayUi extends TabActivity {
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
				pInfo = cx.getPackageManager().getPackageInfo(cx.getPackageName(), 0);
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
	        
	        Preference licence = (Preference) findPreference("opensource_license");
	        licence.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
				@Override
				public boolean onPreferenceClick(Preference preference) {
					// TODO Auto-generated method stub
					Intent i = new Intent(cx, License.class);
					startActivity(i);
					return false;
				}
	        	
	        });
	        
	        Preference stats = (Preference) findPreference("toolkit_stats");
	        stats.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
				@Override
				public boolean onPreferenceClick(Preference preference) {
					// TODO Auto-generated method stub
					Intent i = new Intent(cx, AnonymousStats.class);
					startActivity(i);
					return false;
				}
	        	
	        });
	        
	        Preference follow_jieehd = (Preference) findPreference("follow_jieehd_pref");
	        follow_jieehd.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
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
	        
	        
	        Preference follow_vr = (Preference) findPreference("follow_vr_pref");
	        follow_vr.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	
				@Override
				public boolean onPreferenceClick(Preference arg0) {
					// TODO Auto-generated method stub
	                String url = "http://www.twitter.com/VillainROM";
	                Intent i = new Intent(Intent.ACTION_VIEW);
	                i.setData(Uri.parse(url));
	                startActivity(i);
					return false;
				}
	        	
	        });
	    }
	}
}
