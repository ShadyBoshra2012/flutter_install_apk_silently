package com.shadyboshra2012.flutter_install_apk_silently;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class InstallingTask extends AsyncTask<String, Void, InstallingResult> {

    /// Error codes returned to Flutter if there's an error.
    private static final String INSTALL_APK_ERROR = "400";
    private static final String FILE_NOT_FOUND_ERROR = "404";

    private final MethodChannel.Result result;
    private final MethodCall call;

    InstallingTask(MethodChannel.Result result, MethodCall call) {
        this.result = result;
        this.call = call;
    }

    @Override
    protected InstallingResult doInBackground(String... param) {
        // Init the returning value result.
        InstallingResult installingResult;
        try {
            // Get the args from Flutter.
            String filePath = call.argument("filePath");

            File file = new File(filePath);
            // Check if file is exist.
            if (file.exists()) {
                String command = "pm install  '" + filePath + "'";
                Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
                // Run command.
                process.waitFor();
                // Check the result of the process.
                if (process.exitValue() == 0) {
                    // Return true for a success.
                    //result.success(true);
                    installingResult = new InstallingResult(true);
                } else {
                    // Failed to be installed.
                    // Error in installing.
                    String errorDetails = convertStreamToString(process.getErrorStream());
                    //result.error(process.exitValue() + "", "Failed to install", errorDetails);
                    installingResult = new InstallingResult(false, process.exitValue() + "", "Failed to install", errorDetails);
                }
            } else {
                // Failed to be installed.
                // File not found.
                //result.error(FILE_NOT_FOUND_ERROR, "Failed to install", "File not found.");
                installingResult = new InstallingResult(false, FILE_NOT_FOUND_ERROR, "Failed to install", "File not found.");
            }
        } catch (Exception ex) {
            // Return an error.
            //result.error(INSTALL_APK_ERROR, ex.getMessage(), ex.getLocalizedMessage());
            installingResult = new InstallingResult(false, INSTALL_APK_ERROR, ex.getMessage(), ex.getLocalizedMessage());
        }

        return installingResult;
    }

    @Override
    protected void onPostExecute(InstallingResult installingResult) {
        if(installingResult.isSuccess()){
            result.success(true);
        } else {
            result.error(installingResult.getError(), installingResult.getErrorMessage(), installingResult.getErrorDetails());
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}