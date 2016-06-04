package sagargupta.koboglolightbuttonutility;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ButtonListenerService extends Service
{
    public ButtonListenerService()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int startid, int startId)
    {
        Toast.makeText(this, "Kobo Screen-Refresh Input Listener-Service Started", Toast.LENGTH_LONG).show();
        ButtonListenerReceiver buttonReceiver = new ButtonListenerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); // Restore preferences
        if(preferences.getBoolean("volumeChangeScreenRefreshValue", true))
        {
            filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        }
        filter.addAction("android.intent.action.MEDIA_BUTTON");
        this.registerReceiver(buttonReceiver, filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Kobo Screen-Refresh Input Listener-Service Stopped", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}

class ButtonListenerReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (ScreenRefreshActivity.refreshInProgress == false)
        {
            Intent intentMain = new Intent(context, ScreenRefreshActivity.class);
            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentMain);
        }
    }
}