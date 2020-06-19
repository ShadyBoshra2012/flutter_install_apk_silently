import 'dart:async';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class FlutterInstallApkSilently {
  /// The channel name which it's the bridge between Dart and JAVA or SWIFT
  static const String _CHANNEL_NAME = "shadyboshra2012/flutterinstallapksilently";

  /// Methods name which detect which it called from Flutter.
  static const String _METHOD_INSTALL_APK = "installAPK";

  /// Error codes returned to Flutter if there's an error.
  //static const String _INSTALL_APK_ERROR = "400";
  //static const String _FILE_NOT_FOUND_ERROR = "404";

  /// Initialize the channel
  static const MethodChannel _channel = const MethodChannel(_CHANNEL_NAME);

  static Future<bool> installAPK({@required File file}) async {
    if (Platform.isAndroid) {
      try {
        final bool isInstalled = await _channel.invokeMethod(_METHOD_INSTALL_APK, <String, String>{'filePath': file.path});
        return isInstalled;
      } on PlatformException catch (e) {
        throw "Installation Error Occurred: Code: ${e.code}. Message: ${e.message}. Details: ${e.details}";
      }
    } else {
      // Return false if not Android.
      return false;
    }
  }
}
