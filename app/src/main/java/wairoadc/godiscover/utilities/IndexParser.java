package wairoadc.godiscover.utilities;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.model.Track;

/**
 * Created by Xinxula on 27/01/2015.
 */
public class IndexParser extends XMLParser {
    //Tags used on the XML model inside each track package
    private static final String ns = null;
    private static final String INDEX_TAG = "index";
    private static final String FILE_TAG = "file";
    private static final String FILE_NAME_TAG = "fileName";
    private static final String VERSION_TAG = "version";

    public static List<Track> parse(InputStream in) throws XmlPullParserException, IOException {
        List<Track> fileListFromIndex = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in,null);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG,ns,INDEX_TAG);
            while(parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tagName = parser.getName();
                if(tagName.equals(FILE_TAG)) {
                    fileListFromIndex.add(readTrack(parser));
                } else {
                    skip(parser);
                }
            }
        } finally {
            in.close();
        }
        return fileListFromIndex;
    }

    private static Track readTrack(XmlPullParser parser) throws XmlPullParserException, IOException {
        Track track = new Track();
        parser.require(XmlPullParser.START_TAG, ns, FILE_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(FILE_NAME_TAG)) {
                track.setName(readTag(parser, FILE_NAME_TAG));
            } else if (name.equals(VERSION_TAG)) {
                track.setVersion(Long.parseLong(readTag(parser, VERSION_TAG)));
            } else {
                skip(parser);
            }
        }
        return track;
    }
}
