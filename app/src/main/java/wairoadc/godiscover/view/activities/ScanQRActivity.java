package wairoadc.godiscover.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import wairoadc.godiscover.R;
import wairoadc.godiscover.utilities.IntentIntegrator;
import wairoadc.godiscover.utilities.IntentResult;

public class ScanQRActivity extends HomeDrawer implements OnClickListener{

    private ImageButton scanBtn;

    private String DEFAULT_MESSAGE;
    private String DEFAULT_YES;
    private String DEFAULT_NO;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan_qr);
        super.onCreate(savedInstanceState);
        DEFAULT_MESSAGE = getString(R.string.qr_code_message_english);
        DEFAULT_YES =getString(R.string.yes);
        DEFAULT_NO = getString(R.string.no);
        scanBtn = (ImageButton)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
        //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this,DEFAULT_MESSAGE, DEFAULT_YES, DEFAULT_NO);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
        //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
