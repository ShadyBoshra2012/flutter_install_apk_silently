import 'dart:io';

import 'package:dio/dio.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_install_apk_silently/flutter_install_apk_silently.dart';
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _status = "idle";
  String _message = 'Please browse APK.';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            RaisedButton(
              onPressed: () async {
                setState(() {
                  _status = "loading";
                  _message = "Waiting to be installed.";
                });

                var tempDir = await getTemporaryDirectory();
                String fullPath = tempDir.path + "/app_${DateTime.now().millisecondsSinceEpoch}.apk";
                print('full path $fullPath');

                Dio dio = Dio();
                await download(dio, fullPath);

                File file = File(fullPath);

                Future.delayed(Duration(seconds: 1), () async {
                  FlutterInstallApkSilently.installAPK(file: file).then((isInstalled) {
                    if (isInstalled)
                      setState(() {
                        _status = "success";
                        _message = "The APK is installed successfully.";
                      });
                    else
                      setState(() {
                        _status = "failed";
                        _message = "The APK is installed failed.";
                      });
                  }).catchError((onError) => print(onError));
                });
              },
              child: Text("Download from Internet", style: TextStyle(fontSize: 24.0)),
            ),
            RaisedButton(
              onPressed: () async {
                File file = await FilePicker.getFile(type: FileType.custom, allowedExtensions: ['apk']);

                setState(() {
                  _status = "loading";
                  _message = "Waiting to be installed.";
                });
                Future.delayed(Duration(seconds: 1), () async {
                  FlutterInstallApkSilently.installAPK(file: file).then((isInstalled) {
                    if (isInstalled)
                      setState(() {
                        _status = "success";
                        _message = "The APK is installed successfully.";
                      });
                    else
                      setState(() {
                        _status = "failed";
                        _message = "The APK is installed failed.";
                      });
                  });
                });
              },
              child: Text("Browse APK", style: TextStyle(fontSize: 24.0)),
            ),
            (_status == "loading")
                ? Center(child: CircularProgressIndicator())
                : (_status == "success")
                    ? Center(child: Icon(Icons.check, color: Colors.green, size: 35.0))
                    : (_status == "failed") ? Center(child: Icon(Icons.clear, color: Colors.red, size: 35.0)) : Container(),
            Text("$_message", style: TextStyle(fontSize: 24.0))
          ],
        ),
      ),
    );
  }

  Future download(Dio dio, String savePath) async {
    try {
      Response response = await dio.get(
        'apk url',
        //onReceiveProgress: showDownloadProgress,
        //Received data with List<int>
        options: Options(
            responseType: ResponseType.bytes,
            followRedirects: false,
            validateStatus: (status) {
              return status < 500;
            }),
      );

      File file = File(savePath);
      var raf = file.openSync(mode: FileMode.write);
      // response.data is List<int> type
      raf.writeFromSync(response.data);
      await raf.close();
    } catch (e) {
      print(e);
    }
  }
}
