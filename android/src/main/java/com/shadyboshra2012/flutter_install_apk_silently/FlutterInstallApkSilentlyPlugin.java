package com.shadyboshra2012.flutter_install_apk_silently;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterInstallApkSilentlyPlugin
 */
public class FlutterInstallApkSilentlyPlugin implements FlutterPlugin, MethodCallHandler {
    /// The channel name which it's the bridge between Dart and JAVA
    private static final String CHANNEL_NAME = "shadyboshra2012/flutterinstallapksilently";

    /// Methods name which detect which it called from Flutter.
    private static final String METHOD_INSTALL_APK = "installAPK";

    /// Error codes returned to Flutter if there's an error.
    private static final String INSTALL_APK_ERROR = "400";
    private static final String FILE_NOT_FOUND_ERROR = "404";

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), CHANNEL_NAME);
        channel.setMethodCallHandler(this);
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(new FlutterInstallApkSilentlyPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case METHOD_INSTALL_APK:
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
                            result.success(true);
                        } else {
                            // Failed to be installed.
                            // Error in installing.
                            String errorDetails = convertStreamToString(process.getErrorStream());
                            result.error(process.exitValue() + "", "Failed to install", errorDetails);
                        }
                    } else {
                        // Failed to be installed.
                        // File not found.
                        result.error(FILE_NOT_FOUND_ERROR, "Failed to install", "File not found.");
                    }
                } catch (Exception ex) {
                    // Return an error.
                    result.error(INSTALL_APK_ERROR, ex.getMessage(), ex.getLocalizedMessage());
                }
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
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
