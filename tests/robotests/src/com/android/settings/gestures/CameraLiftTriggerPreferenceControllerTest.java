/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.android.settings.gestures;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.preference.PreferenceScreen;

import com.android.settings.testutils.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;

import com.android.settings.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static android.provider.Settings.Secure.CAMERA_LIFT_TRIGGER_ENABLED;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST_PATH, sdk = TestConfig.SDK_VERSION)
public class CameraLiftTriggerPreferenceControllerTest {

    private static final String KEY_CAMERA_LIFT_TRIGGER = "gesture_camera_lift_trigger";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Context mContext;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PreferenceScreen mScreen;
    private CameraLiftTriggerPreferenceController mController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mController = new CameraLiftTriggerPreferenceController(mContext, null,
                KEY_CAMERA_LIFT_TRIGGER);
    }

    @Test
    public void isAvailable_configIsTrue_shouldReturnTrue() {
        when(mContext.getResources().
                getBoolean(R.bool.config_cameraLiftTriggerAvailable))
                .thenReturn(true);

        assertThat(mController.isAvailable()).isTrue();
    }

    @Test
    public void isAvailable_configIsTrue_shouldReturnFalse() {
        when(mContext.getResources().
                getBoolean(R.bool.config_cameraLiftTriggerAvailable))
                .thenReturn(false);

        assertThat(mController.isAvailable()).isFalse();
    }

    @Test
    public void testSwitchEnabled_defaultConfig_shouldReturnTrue() {
        final Context context = RuntimeEnvironment.application;
        mController = new CameraLiftTriggerPreferenceController(context, null,
                KEY_CAMERA_LIFT_TRIGGER);

        assertThat(mController.isSwitchPrefEnabled()).isTrue();
    }

    @Test
    public void testSwitchEnabled_configIsNotSet_shouldReturnFalse() {
        // Set the setting to be enabled.
        final Context context = RuntimeEnvironment.application;
        Settings.System.putInt(context.getContentResolver(),
                CAMERA_LIFT_TRIGGER_ENABLED, 0);
        mController = new CameraLiftTriggerPreferenceController(context, null,
                KEY_CAMERA_LIFT_TRIGGER);

        assertThat(mController.isSwitchPrefEnabled()).isFalse();
    }

    @Test
    public void testSwitchEnabled_configIsSet_shouldReturnTrue() {
        // Set the setting to be disabled.
        final Context context = RuntimeEnvironment.application;
        Settings.System.putInt(context.getContentResolver(),
                CAMERA_LIFT_TRIGGER_ENABLED, 1);
        mController = new CameraLiftTriggerPreferenceController(context, null,
                KEY_CAMERA_LIFT_TRIGGER);

        assertThat(mController.isSwitchPrefEnabled()).isTrue();
    }

    @Test
    public void testEnablePreference_shouldSetSetting() {
        final Context context = RuntimeEnvironment.application;
        mController = new CameraLiftTriggerPreferenceController(context, null,
                KEY_CAMERA_LIFT_TRIGGER);
        mController.onPreferenceChange(null, true);

        assertThat(Settings.Secure.getInt(context.getContentResolver(),
                CAMERA_LIFT_TRIGGER_ENABLED, 0)).isEqualTo(1);
    }

    @Test
    public void testDisablePreference_shouldClearSetting() {
        final Context context = RuntimeEnvironment.application;
        mController = new CameraLiftTriggerPreferenceController(context, null,
                KEY_CAMERA_LIFT_TRIGGER);
        mController.onPreferenceChange(null, false);

        assertThat(Settings.Secure.getInt(context.getContentResolver(),
                CAMERA_LIFT_TRIGGER_ENABLED, 1)).isEqualTo(0);
    }
}