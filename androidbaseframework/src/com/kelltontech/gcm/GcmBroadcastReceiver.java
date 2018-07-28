/*
 *
 *  Proprietary and confidential. Property of Kellton Tech Solutions Ltd. Do not disclose or distribute.
 *  You must have written permission from Kellton Tech Solutions Ltd. to use this code.
 *
 */

package com.kelltontech.gcm;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public abstract class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        ComponentName comp = getComponentName(context);
//        // Start the service, keeping the device awake while it is launching.
//        startWakefulService(context, (intent.setComponent(comp)));
//        setResultCode(Activity.RESULT_OK);


    }

    /**
     * param context
     *
     * @return the component name. Example //ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
     */

    public abstract ComponentName getComponentName(Context context);

}

	
