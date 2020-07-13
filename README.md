# Flutter Install APK Silently  

[![pub.dev](https://img.shields.io/pub/v/flutter_install_apk_silently.svg)](https://pub.dev/packages/flutter_install_apk_silently)  [![Donate Paypal](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://paypal.me/ShadyBoshra2012) <a href="https://www.buymeacoffee.com/ShadyBoshra2012" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/lato-blue.png" alt="Buy Me A Coffee" height="20px" width="85px" ></a> [![GitHub Follow](https://img.shields.io/github/followers/ShadyBoshra2012.svg?style=social&label=Follow)](https://github.com/ShadyBoshra2012)

## Explain

This plugin is used when you need to install APKs on Android without any UI interruption and silently on **Custom Android ROM** using rooted devices.

## Getting Started

Add this to your package's `pubspec.yaml` file:

```yaml
dependencies:
  flutter_install_apk_silently: ^0.1.1+1
```

### Android

You need to add some permission in `AndroidManifest.xml` file.

```
<!-- Install/delete permissions, only granted to system apps -->
<uses-permission android:name="android.permission.INSTALL_PACKAGES" />
<uses-permission android:name="android.permission.DELETE_PACKAGES" />
```

### Android Root on Emulator

You must have to root your device. To test the package on Emulator, you can follow these instructions [here](https://blog.ctdefense.com/install-and-root-your-android-emulator/).
Or you can root you real device and make your Custom Rom.

## Usage

You just have to import the package with

```dart
import 'package:flutter_install_apk_silently/flutter_install_apk_silently.dart';
```

Then you can download APK from internet or use one from device storage as file, and pass it to install.

> Please see example.

```
bool result = await FlutterInstallApkSilently.installAPK(file: file);
```

The method will return a boolean with the status of installation. And if it fails you can catch the error.

## Issues or Contributions

This is a beta version of plugin, so I am very appreciated for any issues or contribution you can help me with.

## License

MIT: [http://mit-license.org](http://mit-license.org). 

Copyright (c) 2019 Shady Boshra. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Find me on StackOverflow

<a href="https://stackoverflow.com/users/2076880/shady-boshra"><img src="https://stackoverflow.com/users/flair/2076880.png" width="208" height="58" alt="profile for Shady Boshra at Stack Overflow, Q&amp;A for professional and enthusiast programmers" title="profile for Shady Boshra at Stack Overflow, Q&amp;A for professional and enthusiast programmers"></a> 