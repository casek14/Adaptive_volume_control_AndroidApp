package com.example.casek.babysitter.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by casek on 7.3.18.
 * This class handle saving and loading app settings
 */

public class SettingsManager {

    public String STORAGE_SETTINGS_NAME = "wifiSitter_settings.json";
    public String NOT_VALID_IP_ADDRESS = "IP address is not valid";
    public String NOT_VALID_IP_PORT = "Port is not valid";
    public String SHARED_PREFERENCES_NAME = "app_settings";
    public String SETTINGS_UPDATE_OK = "Settings successfully updated";


    /**
     * This function manage settings, if no settigs present, will create them,
     * if changed update them, settings are stored in internal storage as JSON file
     * @param context Context of the given activity
     * @param ipAddress IP address of the sound server
     * @param port Port, on which sound server listen
     * @param sampleRate Sample rate in Hz
     * @param mode Stereo (default) or Mono
     * @return
     */
    public String manageSettings(Context context,
                                 String ipAddress,
                                 String port,
                                 String sampleRate,
                                 String mode){

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(validateIpAddress(ipAddress)){
            editor.putString("ip",ipAddress);
            editor.commit();
        }else{
            return NOT_VALID_IP_ADDRESS;
        }

        if(validatePort(port)){
            editor.putString("port",port);
            editor.commit();
        }else{
            return NOT_VALID_IP_PORT;
        }

        createOrUpdateSettings(context,ipAddress,port,sampleRate,mode);

        return SETTINGS_UPDATE_OK;
    }




    /**
     * read settigns from storage
     * @return
     */
    private JSONObject readSettings(Context context){

        JSONObject jsonObject = null;

        try {
            InputStream inputStream = context.openFileInput(STORAGE_SETTINGS_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                JSONTokener jsonTokener = new JSONTokener(stringBuilder.toString());
                jsonObject = new JSONObject(jsonTokener);
            }
        }
        catch (FileNotFoundException e ) {
            Log.e("ERROR", "File not found: " + e.toString());
        }
        catch(JSONException eJson){
            Log.e("ERROR", "Json error: " + eJson.toString());
        }
        catch (IOException e) {
            Log.e("ERROR", "Can not read file: " + e.toString());
        }


        return new JSONObject();
    }


    /**
     * Create settings in storage
     * if exists update it
     * @return
     */
    private void createOrUpdateSettings(Context context,
                                        String ipAddress,
                                        String port,
                                        String sampleRate,
                                        String monoStereoMode){


        JSONObject jsonObject = createJsonFile(ipAddress, port, sampleRate, monoStereoMode);
        if (checkIfFileExists(context,STORAGE_SETTINGS_NAME)){
            context.deleteFile(STORAGE_SETTINGS_NAME);
        }

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(STORAGE_SETTINGS_NAME,Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Create JSON object
     * @return JSONObject
     */
    private JSONObject createJsonFile(String ipAddress,
                                      String port,
                                      String sampleRate,
                                      String mode){


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ipAddress",ipAddress);
            jsonObject.put("port", port);
            jsonObject.put("sampleRate",sampleRate);
            jsonObject.put("monoStereoMode", mode);
            Log.i("JSONFILE",jsonObject.toString());
        }catch (JSONException e){
            Log.e("ERROR CREATING JSON",e.toString());
        }

        return jsonObject;
    }

    /**
     * Check if the file exists in the internal storage
     * @param name
     * @return
     */
    private Boolean checkIfFileExists(Context context, String name){
        File f = context.getFileStreamPath(name);
        return f.exists();
    }

    /**
     * return True ip port is valid
     * @param port
     * @return
     */
    private Boolean validatePort(String port){
        int p = Integer.getInteger(port);
        if( p >= 0 && p <= 65535){
            return true;
        }

        return false;

    }

    /**
     * return True if the ip address is valid
     * @return
     */
    private Boolean validateIpAddress(String ip){

        String[] octet = ip.split("\\.");
        if (octet.length == 4 &&
            Integer.getInteger(octet[0] ) >=1 && Integer.getInteger(octet[0] ) <= 254 &&
            Integer.getInteger(octet[1] ) >=0 && Integer.getInteger(octet[1] ) <= 255 &&
            Integer.getInteger(octet[2] ) >=0 && Integer.getInteger(octet[2] ) <= 255 &&
            Integer.getInteger(octet[3] ) >=0 && Integer.getInteger(octet[3] ) <= 255){

            return true;
        }

        return false;
    }
}
