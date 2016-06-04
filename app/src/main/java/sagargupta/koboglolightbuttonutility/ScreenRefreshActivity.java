package sagargupta.koboglolightbuttonutility;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScreenRefreshActivity extends AppCompatActivity
{
    static boolean refreshInProgress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_refresh);
        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable,500); //Hold the screen for some time
    }

    //Here's a runnable/handler combo
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            refreshInProgress = false;
            finish();
        }
    };
}