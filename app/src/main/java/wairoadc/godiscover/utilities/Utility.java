package wairoadc.godiscover.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.regions.Regions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Xinxula on 21/01/2015.
 */
public class Utility {

    private static final String LOG_TAG = "Utility";
    private static final String PROPERTIES_FILE = "api_keys.properties";

   //Method from Stack Overflow
   //Source:
    public static boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                // zapis do souboru
                filename = ze.getName();

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);

                // cteni zipu a zapis
                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static CognitoCachingCredentialsProvider initializeCognitoProvider(Context context) {
        CognitoCachingCredentialsProvider credentials;
        Properties properties = getProperties(context,PROPERTIES_FILE);
        credentials = new CognitoCachingCredentialsProvider(
                context,
                properties.getProperty("aws_id"), /* AWS Account ID */
                properties.getProperty("id_pool"), /* Identity Pool ID */
                properties.getProperty("unauth_role"), /* Unauthenticated Role ARN */
                properties.getProperty("auth_role"), /* Authenticated Role ARN */
                Regions.US_EAST_1 /* Region */
        );

        return credentials;

    }

    public static String stripZipExtensionName(String name) {
        return name.replace(".zip","");
    }

    public static TransferManager connectToAmazon(Context context) throws NotAuthenticatedException {
        Log.i(LOG_TAG, "Connecting to Amazon S3 server....");
        CognitoCachingCredentialsProvider credentialsProvider = initializeCognitoProvider(context);
        if(null == credentialsProvider.getIdentityId() || "" == credentialsProvider.getIdentityId())
            throw new Utility.NotAuthenticatedException("Not Authenticated!");
        TransferManager transferManager = new TransferManager(credentialsProvider);
        return transferManager;
    }

    public static class NotAuthenticatedException extends Exception {
        public NotAuthenticatedException(String message) {
            super(message);
        }
    }

    //Source:
    //http://khurramitdeveloper.blogspot.co.nz/2013/07/properties-file-in-android.html
    public static Properties getProperties(Context context, String FileName) {

        Properties properties = new Properties();
        try {
            /**
             * getAssets() Return an AssetManager instance for your
             * application's package. AssetManager Provides access to an
             * application's raw asset files;
             */
            AssetManager assetManager = context.getAssets();
            /**
             * Open an asset using ACCESS_STREAMING mode. This
             */
            InputStream inputStream = assetManager.open(FileName);
            /**
             * Loads properties from the specified InputStream,
             */
            properties.load(inputStream);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("AssetsPropertyReader",e.toString());
        }
        return properties;
    }



}
