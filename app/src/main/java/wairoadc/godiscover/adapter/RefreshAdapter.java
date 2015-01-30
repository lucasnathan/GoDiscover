package wairoadc.godiscover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.utilities.Utility;

/**
 * Created by Xinxula on 29/01/2015.
 */
public class RefreshAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> values;


    public RefreshAdapter(Context context, int resource, List<String> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;

    }

    static class ViewHolder {
        TextView text;
        ProgressBar progress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.track_download_list_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.track_item_download_id);
            viewHolder.progress = (ProgressBar)rowView.findViewById(R.id.track_item_download_progress);
            rowView.setTag(viewHolder);

        }
        ViewHolder holder = (ViewHolder)rowView.getTag();
        holder.text.setText(Utility.stripZipExtensionName(values.get(position)));
        return rowView;
    }
}
