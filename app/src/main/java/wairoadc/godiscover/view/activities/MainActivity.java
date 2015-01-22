package wairoadc.godiscover.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GridViewAdapter;
import wairoadc.godiscover.model.ImageItem;

public class MainActivity extends Activity{
    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    // constant for identifying the dialog
    private static final int DIALOG_ALERT = 10;

    // existing code.....

    // adjust this method if you have more than
    // one button pointing to this method
    public void onClick(View view) {
        showDialog(DIALOG_ALERT);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, position + "#Selected",
                        Toast.LENGTH_LONG).show();
                showDialog(DIALOG_ALERT);
            }

        });
    }

    private ArrayList getData() {
        final ArrayList imageItems = new ArrayList();
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }

        return imageItems;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want directions?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes, I do", new RedirectToMaps());
                builder.setNegativeButton("No, thanks", new GoToInfo());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }
//Negative Answer
    private final class RedirectToMaps implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Going to google maps...",
                    Toast.LENGTH_LONG).show();
        }
    }
//Positive answer
    private final class GoToInfo implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            //MainActivity.this.finish();
            Intent intent= new Intent(MainActivity.this,InfoActivity.class);
            startActivity(intent);

        }
    }

}