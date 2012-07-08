/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jieehd.villain.toolkit.stats;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.jieehd.villain.toolkit.R;

@SuppressWarnings("deprecation")
public class AnonymousStats extends PreferenceActivity
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener,
        Preference.OnPreferenceChangeListener {

    private static final String VIEW_STATS = "pref_view_stats";
    protected static final String ANONYMOUS_OPT_IN = "pref_anonymous_opt_in";
    protected static final String ANONYMOUS_FIRST_BOOT = "pref_anonymous_first_boot";
    protected static final String ANONYMOUS_LAST_CHECKED = "pref_anonymous_checked_in";
    protected static final String ANONYMOUS_ALARM_SET = "pref_anonymous_alarm_set";
    protected static final String ANONYMOUS_REPORTED_VERSION = "pref_anonymous_reported_version";
    private CheckBoxPreference mEnableReporting;
    private Preference mViewStats;
    private Dialog mOkDialog;
    private boolean mOkClicked;
    private SharedPreferences mPrefs;
    private Context cx;

    @SuppressLint("WorldReadableFiles")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPreferenceManager() != null) {
            addPreferencesFromResource(R.xml.annonymous_stats);
            PreferenceScreen prefSet = getPreferenceScreen();
            mPrefs = this.getSharedPreferences("VRToolkit", MODE_WORLD_READABLE);
            mEnableReporting = (CheckBoxPreference) prefSet.findPreference(ANONYMOUS_OPT_IN);
            mViewStats = (Preference) prefSet.findPreference(VIEW_STATS);
            boolean firstBoot = mPrefs.getBoolean(ANONYMOUS_FIRST_BOOT, true);
            if (mEnableReporting.isChecked() && firstBoot) {
                mPrefs.edit().putBoolean(ANONYMOUS_FIRST_BOOT, false).apply();
                ReportingServiceManager.launchService(cx);
            }
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(1);
        }
        
        Preference view = (Preference) findPreference("preview");
        view.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AnonymousStats.this, PreviewData.class);
				startActivity(i);
				return false;
			}
        	
        });
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mEnableReporting) {
            if (mEnableReporting.isChecked()) {
                // Display the confirmation dialog
                mOkClicked = false;
                if (mOkDialog != null) {
                    mOkDialog.dismiss();
                    mOkDialog = null;
                }
                mOkDialog = new AlertDialog.Builder(AnonymousStats.this).setMessage("Stats")
                        .setTitle("About")
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setPositiveButton(android.R.string.yes, this)
                        .setNeutralButton(("Learn More"), this)
                        .setNegativeButton(android.R.string.no, this)
                        .show();
                mOkDialog.setOnDismissListener(this);
            } else {
                // Disable reporting
                mPrefs.edit().putBoolean(ANONYMOUS_OPT_IN, false).apply();
            }
        } else if (preference == mViewStats) {
            // will add later
            Uri uri = Uri.parse("");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } else {
            // If we didn't handle it, let preferences handle it.
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!mOkClicked) {
            mEnableReporting.setChecked(false);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mOkClicked = true;
            mPrefs.edit().putBoolean(ANONYMOUS_OPT_IN, true).apply();
            ReportingServiceManager.launchService(cx);
        } else if (which == DialogInterface.BUTTON_NEGATIVE){
            mEnableReporting.setChecked(false);
        } else {
        	//will add later
            Uri uri = Uri.parse("");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

}
