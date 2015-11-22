/*
 * Copyright (C) 2014-2016 The MoKee Open Source Project
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
 
package com.android.settings.profiles;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplayStatus;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import mokee.providers.MKSettings;

import com.android.internal.telephony.PhoneConstants;

public class ProfilesUtils {
        public static boolean deviceSupportsUsbTether(Context ctx) {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            return (cm.getTetherableUsbRegexs().length != 0);
        }

        public static boolean deviceSupportsWifiDisplay(Context ctx) {
            DisplayManager dm = (DisplayManager) ctx.getSystemService(Context.DISPLAY_SERVICE);
            return (dm.getWifiDisplayStatus().getFeatureState() != WifiDisplayStatus.FEATURE_STATE_UNAVAILABLE);
        }

        public static boolean deviceSupportsMobileData(Context ctx) {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.isNetworkSupported(ConnectivityManager.TYPE_MOBILE);
        }

        public static boolean deviceSupportsBluetooth() {
            return (BluetoothAdapter.getDefaultAdapter() != null);
        }

        public static boolean systemProfilesEnabled(ContentResolver resolver) {
            return (MKSettings.System.getInt(resolver, MKSettings.System.SYSTEM_PROFILES_ENABLED, 1) == 1);
        }

        public static boolean deviceSupportsNfc(Context ctx) {
            return NfcAdapter.getDefaultAdapter(ctx) != null;
        }

        public static boolean deviceSupportsCamera() {
            return Camera.getNumberOfCameras() > 0;
        }

        public static boolean deviceSupportsGps(Context context) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        }

        public static boolean adbEnabled(ContentResolver resolver) {
            return (Settings.Global.getInt(resolver, Settings.Global.ADB_ENABLED, 0)) == 1;
        }

        public static boolean deviceSupportsCompass(Context context) {
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            return (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
                    && sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null);
        }
}
