package wairoadc.godiscover.utilities;


import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.model.Spot;
import wairoadc.godiscover.model.Track;
import wairoadc.godiscover.model.Type;

/**
 * Created by Xinxula on 22/01/2015.
 */

//Reference: http://developer.android.com/training/basics/network-ops/xml.html
public class TrackXMLParser extends XMLParser {

    //Tags used on the XML model inside each track package
    private static final String ns = null;
    private static final String START_TAG = "track";
    private static final String VERSION_TAG = "version";
    private static final String TRACK_NAME_TAG = "name";
    private static final String PIC_PATH_TAG = "picPath";
    private static final String MAP_PATH_TAG = "mapPath";
    private static final String DESCRIPTION_TAG = "description";

    private static final String SPOTS_TAG = "spots";
    private static final String SPOT_TAG = "spot";
    private static final String SPOT_NAME_TAG = "name";
    private static final String SPOT_INFO_TAG = "info";
    private static final String SPOT_X_TAG = "x";
    private static final String SPOT_Y_TAG = "y";
    private static final String SPOT_LAT_TAG = "lat";
    private static final String SPOT_LONG_TAG = "long";

    private static final String CONTENT_TAG = "content";
    private static final String RESOURCE_TAG = "res";
    private static final String RESOURCE_TYPE_TAG = "type";
    private static final String RESOURCE_NAME_TAG = "name";
    private static final String RESOURCE_STORY_TAG = "story";
    private static final String RESOURCE_PATH_TAG = "path";


    public static Track parse(InputStream in) throws XmlPullParserException, IOException {
        Track track;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in,null);
            parser.nextTag();
            track = readTrack(parser);
        } finally {
            in.close();
        }
        return track;
    }

    private static Track readTrack(XmlPullParser parser) throws XmlPullParserException, IOException {
        Track track = new Track();

        parser.require(XmlPullParser.START_TAG,ns,START_TAG);
        while(parser.next() != XmlPullParser.END_TAG) {
            //Just a safety measure, this if basically tell that if the parser is not in the desired START_TAG it will skip the content
            //until it finds the desired tag.
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if(tagName.equals(VERSION_TAG)) {
                track.setVersion(Long.parseLong(readTag(parser,VERSION_TAG)));
            } else if(tagName.equals(TRACK_NAME_TAG)) {
                track.setName(readTag(parser,TRACK_NAME_TAG));
            } else if (tagName.equals(PIC_PATH_TAG)) {
                track.setResource(readTag(parser,PIC_PATH_TAG));
            } else if(tagName.equals(MAP_PATH_TAG)) {
                track.setMapPath(readTag(parser,MAP_PATH_TAG));
            } else if(tagName.equals(DESCRIPTION_TAG)) {
                track.setDescription(readTag(parser,DESCRIPTION_TAG));
            } else if (tagName.equals(SPOTS_TAG)) {
                track.setSpots(readSpots(parser));
            }
        }
        return track;
    }

    private static ArrayList<Spot> readSpots(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Spot> spots = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, SPOTS_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(SPOT_TAG)) {
                spots.add(readSpot(parser));
            } else {
                skip(parser);
            }
        }
        return spots;
    }

    private static Spot readSpot(XmlPullParser parser) throws XmlPullParserException, IOException {
        Spot spot = new Spot();
        parser.require(XmlPullParser.START_TAG, ns, SPOT_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(SPOT_NAME_TAG)) {
                spot.setName(readTag(parser,SPOT_NAME_TAG));
            } else if (name.equals(SPOT_INFO_TAG)) {
                spot.setInformation(readTag(parser,SPOT_INFO_TAG));
            } else if (name.equals(SPOT_X_TAG)) {
                spot.setX(Integer.parseInt(readTag(parser,SPOT_X_TAG)));
            } else if (name.equals(SPOT_Y_TAG)) {
                spot.setY(Integer.parseInt(readTag(parser,SPOT_Y_TAG)));
            } else if (name.equals(SPOT_LAT_TAG)) {
                spot.setLatitude(Double.parseDouble(readTag(parser,SPOT_LAT_TAG)));
            } else if (name.equals(SPOT_LONG_TAG)) {
                spot.setLongitude(Double.parseDouble(readTag(parser, SPOT_LONG_TAG)));
            } else if (name.equals(CONTENT_TAG)) {
                spot.setResources(readResources(parser));
            }
        }
        return spot;
    }

    private static ArrayList<Resource> readResources(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Resource> resources = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, CONTENT_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(RESOURCE_TAG)) {
                resources.add(readResource(parser));
            } else {
                skip(parser);
            }
        }
        return resources;
    }

    private static Resource readResource(XmlPullParser parser) throws XmlPullParserException, IOException {
        Resource resource = new Resource();
        parser.require(XmlPullParser.START_TAG, ns, RESOURCE_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(RESOURCE_TYPE_TAG)) {
                Type typeResource = new Type();
                String typeName = readTag(parser,RESOURCE_TYPE_TAG);
                switch (typeName) {
                    case(Type.IMAGE_TYPE):
                        typeResource.setName(Type.IMAGE_TYPE);
                        typeResource.set_id(1);
                        break;
                    case(Type.SOUND_TYPE):
                        typeResource.setName(Type.SOUND_TYPE);
                        typeResource.set_id(2);
                        break;
                    case(Type.VIDEO_TYPE):
                        typeResource.setName(Type.VIDEO_TYPE);
                        typeResource.set_id(3);
                        break;
                }
                resource.setType(typeResource);
            } else if(name.equals(RESOURCE_NAME_TAG)) {
                resource.setName(readTag(parser,RESOURCE_NAME_TAG));
            } else if(name.equals(RESOURCE_STORY_TAG)) {
                resource.setStory(readTag(parser,RESOURCE_STORY_TAG));
            } else if(name.equals(RESOURCE_PATH_TAG)) {
                resource.setPath(readTag(parser,RESOURCE_PATH_TAG));
            }
        }
        return resource;
    }
}
