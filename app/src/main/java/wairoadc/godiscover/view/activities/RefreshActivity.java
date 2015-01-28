package wairoadc.godiscover.view.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;



import java.io.IOException;

import wairoadc.godiscover.R;
import wairoadc.godiscover.services.DownloadService;
import wairoadc.godiscover.view.fragments.RefreshFragment;

public class RefreshActivity extends HomeDrawer {

    public static boolean IS_RUNNING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refresh);
        super.onCreate(savedInstanceState);
        if(findViewById(R.id.content_frame) != null) {
            if(savedInstanceState != null)
                return;
            RefreshFragment refreshFragment = new RefreshFragment();
            getFragmentManager().beginTransaction().add(R.id.content_frame,refreshFragment).commit();

        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RefreshActivity.RESULT_OK) {
                    Toast.makeText(RefreshActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RefreshActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();

                }
            }
        }
    };


    private boolean isWifiOn() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) return true;
        else return false;
    }

    //From:
    //http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts
    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    private AlertDialog createWifiDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Wifi Settings");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to enable WIFI ?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //enable wifi
                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //disable wifi
                        dialog.dismiss();
                        RefreshActivity.this.finish();
                    }
                });
        return alertDialogBuilder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
        if(!isWifiOn()) {
            createWifiDialog().show();
        } else {
            if(isOnline()) {
                IS_RUNNING = true;
            } else {
                Toast.makeText(this,"Error, check your internet connection!", Toast.LENGTH_LONG).show();
                RefreshActivity.this.finish();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        IS_RUNNING = false;
        unregisterReceiver(receiver);
    }
}
