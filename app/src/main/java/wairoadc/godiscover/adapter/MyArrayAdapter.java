package wairoadc.godiscover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.view.models.Audio;

/**
 * Created by Lucas on 9/02/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Audio>{
    public MyArrayAdapter(Context context, ArrayList<Audio> audios){
        super(context, 0, audios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Audio audio = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_audio_item, parent, false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.audio_name);
        TextView duration = (TextView) convertView.findViewById(R.id.song_duration);

        name.setText(audio.getName());
        duration.setText(audio.getDuration());

        return convertView;
    }
}
