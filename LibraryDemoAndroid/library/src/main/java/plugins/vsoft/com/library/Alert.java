package plugins.vsoft.com.library;

/**
 * Created by Codemaker on 10-07-2020.
 */

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import android.os.Looper; //extend android application.

public class Alert extends Application
{
    // our native method, which will be called from Unity3D
    public void PrintString( final Context ctx, final String message )
    {
        //create / show an android toast, with message and short duration.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}