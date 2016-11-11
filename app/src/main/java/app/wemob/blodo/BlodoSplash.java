package app.wemob.blodo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlodoSplash extends AppCompatActivity {

    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blodo_splash);
        showHashKey(this);
        final BlodoSplash sPlashScreen = this;

        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(5000);
                    }
                }
                catch(InterruptedException ex){
                }



                // Run next activity
                SharedPreferences userpreferences=getSharedPreferences("blodouser",MODE_PRIVATE);
                if(userpreferences.getInt("status",0)==0) {
                    Intent intent = new Intent();
                    intent.setClass(sPlashScreen, BlodoRegister.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClass(sPlashScreen, BlodoDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mSplashThread.start();
    }
    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "app.wemob.blodo", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
