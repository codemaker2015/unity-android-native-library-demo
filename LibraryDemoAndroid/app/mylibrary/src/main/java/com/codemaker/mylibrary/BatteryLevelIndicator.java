package com.codemaker.mylibrary;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryLevelIndicator extends Application {

    // Return the battery level as a float between 0 and 1
    // (1 being fully charged, 0 fulled discharged)
    public float GetBatteryPct(Context context) {
        Intent batteryStatus = GetBatteryStatusIntent(context);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        return batteryPct;
    }

    // Return whether or not we're currently on charge
    public boolean IsBatteryCharging(Context context) {
        Intent batteryStatus = GetBatteryStatusIntent(context);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }

    private Intent GetBatteryStatusIntent(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return context.registerReceiver(null, ifilter);
    }

}
