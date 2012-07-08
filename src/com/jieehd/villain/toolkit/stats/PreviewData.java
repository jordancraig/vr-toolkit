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

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.jieehd.villain.toolkit.R;


public class PreviewData extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener{

    private static final String UNIQUE_ID = "preview_id";

    private static final String DEVICE = "preview_device";

    private static final String COUNTRY = "preview_country";

    private static final String CARRIER = "preview_carrier";

    private Preference mId;

    private Preference mDevice;

    private Preference mCountry;

    private Preference mCarrier;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getPreferenceManager() != null) {

            addPreferencesFromResource(R.xml.preview_data);
            PreferenceScreen prefSet = getPreferenceScreen();

            mId = (Preference) prefSet.findPreference(UNIQUE_ID);
            mDevice = (Preference) prefSet.findPreference(DEVICE);
            mCountry = (Preference) prefSet.findPreference(COUNTRY);
            mCarrier = (Preference) prefSet.findPreference(CARRIER);

            mId.setSummary(Utilities.getUniqueID(getApplicationContext()));
            mDevice.setSummary(Utilities.getDevice());
            mCountry.setSummary(Utilities.getCountryCode(getApplicationContext()));
            mCarrier.setSummary(Utilities.getCarrier(getApplicationContext()));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}