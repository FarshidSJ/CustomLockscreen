package com.farshid.customlockscreen;

public class LockScreenState {
    private String lockScreenStateText;
    private boolean lockScreenStateSwitch;

    public String getLockScreenStateText() {
        return lockScreenStateText;
    }

    public void setLockScreenStateText(String lockScreenStateText) {
        this.lockScreenStateText = lockScreenStateText;
    }

    public boolean isLockScreenStateSwitch() {
        return lockScreenStateSwitch;
    }

    public void setLockScreenStateSwitch(boolean lockScreenStateSwitch) {
        this.lockScreenStateSwitch = lockScreenStateSwitch;
    }
}
