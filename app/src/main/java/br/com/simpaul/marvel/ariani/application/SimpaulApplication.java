package br.com.simpaul.marvel.ariani.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.renderscript.Element;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import br.com.simpaul.marvel.ariani.BuildConfig;

public class SimpaulApplication extends Application {

    private static Context CONTEXT;
    private static String DOMAIN = "http://gateway.marvel.com/v1/public/";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    public static Context getAppContext() {
        return CONTEXT;
    }
    public static String getDomain() {
        return DOMAIN;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
    }

    private static String timestamp = getTimestamp();
    public static String getTimestamp() {
        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        //return dateFormat.format(new Date());
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return "MARVEL"+currentYear;
    }

    public static String generateHash(){
        String params = timestamp + BuildConfig.SECRET_KEY + BuildConfig.PUBLIC_KEY;

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(params.getBytes());

            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i< bytes.length; i++){
                stringBuilder.append(Integer.toString((bytes[i] & 0xff)+0x100, 16).substring(1));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
