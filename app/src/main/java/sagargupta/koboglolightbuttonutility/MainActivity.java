package sagargupta.koboglolightbuttonutility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Show activity
        setContentView(R.layout.activity_main);

        // Restore preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ((CheckBox)findViewById(R.id.autoStartupCheckbox)).setOnCheckedChangeListener(this);
        ((CheckBox)findViewById(R.id.killLEDStartupCheckBox)).setOnCheckedChangeListener(this);
        ((CheckBox)findViewById(R.id.volumeChangeScreenRefresh)).setOnCheckedChangeListener(null);
        ((CheckBox)findViewById(R.id.autoStartupCheckbox)).setChecked(preferences.getBoolean("autoStartupCheckboxValue", true));
        ((CheckBox)findViewById(R.id.killLEDStartupCheckBox)).setChecked(preferences.getBoolean("killLEDStartupCheckBoxValue", true));
        ((CheckBox)findViewById(R.id.volumeChangeScreenRefresh)).setChecked(preferences.getBoolean("volumeChangeScreenRefreshValue", true));
        ((CheckBox)findViewById(R.id.volumeChangeScreenRefresh)).setOnCheckedChangeListener(this);
    }

    public MainActivity()
    {

    }

    @Override
    public void onPause()
    {
        super.onPause();
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        // Restore preferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        switch(buttonView.getId())
        {
            case R.id.autoStartupCheckbox:
            {
                editor.putBoolean("autoStartupCheckboxValue", isChecked);
                break;
            }
            case R.id.killLEDStartupCheckBox:
            {
                editor.putBoolean("killLEDStartupCheckBoxValue", isChecked);
                break;
            }
            case R.id.volumeChangeScreenRefresh:
            {
                editor.putBoolean("volumeChangeScreenRefreshValue", isChecked);
                Intent startIntent = new Intent(MainActivity.this, ButtonListenerService.class);
                stopService(startIntent);
                startService(startIntent);
                break;
            }
        }
        editor.commit();
    }

    public void startListenerService(View view)
    {
        Intent startIntent = new Intent(MainActivity.this, ButtonListenerService.class);
        startService(startIntent);
    }

    public void stopListenerService(View view)
    {
        Intent startIntent = new Intent(MainActivity.this, ButtonListenerService.class);
        stopService(startIntent);
    }

    public void manualRefresh(View view)
    {
        Intent intentMain = new Intent(MainActivity.this, ScreenRefreshActivity.class);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMain);
    }

    public void setLightButtonScreenRefresh(View view)
    {
        remapKeyLayout(1);
    }

    public void setLightButtonBack(View view)
    {
        remapKeyLayout(2);
    }

    public void setLightButtonNothing(View view)
    {
        remapKeyLayout(0);
    }

    public void remapKeyLayout(int functionType)
    {
        try
        {
            Toast.makeText(this, "Configuring.. Please Wait..", Toast.LENGTH_SHORT).show();
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            DataInputStream inputStream = new DataInputStream(process.getInputStream());

            outputStream.writeBytes("mount -o rw,remount -t yaffs2 /dev/root /" + "\n");
            outputStream.flush();

//            Files.setPosixFilePermisions(f1.toPath(), EnumSet.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_EXECUTE));

            java.lang.String lightButtonData = "";
            switch (functionType)
            {
                case 0:
                {
                    lightButtonData = "";
                    break;
                }
                case 1:
                {
                    lightButtonData = "key 90    MEDIA_PLAY_PAUSE  WAKE\n";
                    break;
                }
                case 2:
                {
                    lightButtonData = "key 90    BACK              WAKE\n";
                    break;
                }
            }

            java.lang.String fileData = lightButtonData
            + "key 59    MENU              WAKE\n"
            + "key 412   BACK              WAKE\n"
            + "key 102   HOME              WAKE\n"
            + "key 67    ENTER             WAKE\n"
            + "key 25    DPAD_DOWN         WAKE\n"
            + "key 24    DPAD_UP           WAKE\n"
            + "key 10    DPAD_LEFT         WAKE\n"
            + "key 11    DPAD_RIGHT        WAKE\n"
            + "key 407   DPAD_CENTER       WAKE\n";

            outputStream.writeBytes("echo -n \""+ fileData +"\" > /system/usr/keylayout/mxckpd.kl"  + "\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();

            Toast.makeText(this, "Done! Please REBOOT the device for the changes to take effect!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (InterruptedException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
