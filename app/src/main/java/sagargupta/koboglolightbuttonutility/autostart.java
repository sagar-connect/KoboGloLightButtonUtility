package sagargupta.koboglolightbuttonutility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class autostart extends BroadcastReceiver
{
    public autostart()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent1)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context); // Restore preferences
        if(preferences.getBoolean("autoStartupCheckboxValue", true))
        {
            Intent intent = new Intent(context, ButtonListenerService.class);
            context.startService(intent);
        }

        if(preferences.getBoolean("killLEDStartupCheckBoxValue", true))
        {
            try
            {
                Runtime.getRuntime().exec("ioctl -d /dev/ntx_io 127 0");
            }
            catch(Exception e)
            {
                Toast.makeText(context, "Trying to Kill LED Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
