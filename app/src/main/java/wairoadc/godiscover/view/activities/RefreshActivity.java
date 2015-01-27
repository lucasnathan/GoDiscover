package wairoadc.godiscover.view.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;



import java.io.IOException;

import wairoadc.godiscover.R;
import wairoadc.godiscover.view.fragments.RefreshFragment;

public class RefreshActivity extends HomeDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refresh);
        super.onCreate(savedInstanceState);
        if(findViewById(R.id.content_frame) != null) {
            if(savedInstanceState != null)
                return;
            RefreshFragment refreshFragment = new RefreshFragment();
            getFragmentManager().beginTransaction().add(R.id.content_frame,refreshFragment).addToBackStack(null).commit();

        }
    }


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
                    }
                });
        return alertDialogBuilder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isWifiOn()) {
            createWifiDialog().show();
        } else {
            if(isOnline()) {

            } else {
                Toast.makeText(this,"Error, check your internet connection!", Toast.LENGTH_LONG).show();
            }
        }

    }


}
