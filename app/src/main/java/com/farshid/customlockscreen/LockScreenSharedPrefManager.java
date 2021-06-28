package com.farshid.customlockscreen;

import android.content.Context;
import android.content.SharedPreferences;

public class LockScreenSharedPrefManager {
    private SharedPreferences sharedPreferences;
    private static final String LOCK_SCREEN_SHARED_PREF_NAME = "com.farshid.customlockscreen.lock_screen_shared_pref_name";
    private static final String KEY_STATE_TEXT = "com.farshid.customlockscreen.state_text";
    private static final String KEY_STATE_SWITCH = "com.farshid.customlockscreen.state_switch";

    public LockScreenSharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(LOCK_SCREEN_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveState(LockScreenState lockScreenState) {
        if (lockScreenState != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_STATE_TEXT, lockScreenState.getLockScreenStateText());
            editor.putBoolean(KEY_STATE_SWITCH, lockScreenState.isLockScreenStateSwitch());
            editor.apply();
        }
    }

    public LockScreenState getLockScreenState() {
        LockScreenState lockScreenState = new LockScreenState();
        lockScreenState.setLockScreenStateText(sharedPreferences.getString(KEY_STATE_TEXT, "OFF"));
        lockScreenState.setLockScreenStateSwitch(sharedPreferences.getBoolean(KEY_STATE_SWITCH, false));
        return lockScreenState;
    }
}
