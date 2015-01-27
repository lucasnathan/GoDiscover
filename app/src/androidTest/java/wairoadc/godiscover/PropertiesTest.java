package wairoadc.godiscover;

import android.test.AndroidTestCase;

import java.util.Properties;

import wairoadc.godiscover.utilities.Utility;

/**
 * Created by Xinxula on 27/01/2015.
 */
public class PropertiesTest extends AndroidTestCase {

    public void testPropertiesFile() throws Throwable {
        Properties p = Utility.getProperties(mContext,"api_keys.properties");
        assertEquals(true,p.get("aws_id").equals("865405818235"));
    }

}
