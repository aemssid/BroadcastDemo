package com.lndemo.broadcastdemo;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntegerRes;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public  EditText et_search;
    public View view;
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_search=(EditText)findViewById(R.id.et_search);
        networkStateReceiver=new NetworkStateReceiver();



    }


    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(networkStateReceiver,intentFilter);
    }

    public class NetworkStateReceiver extends BroadcastReceiver {



        public NetworkStateReceiver(){
            Log.i(getClass().getSimpleName(),"CREATED");
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getClass().getSimpleName(),"In on Receive");
            if(isNetworkAvailable(context))
            {
                et_search.setHint("Connected");

            }
            else
            {
                et_search.setHint(" Not Connected");

            }


        }

        public boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }
    }
}
