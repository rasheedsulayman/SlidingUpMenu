# SlidingUpMenu

##  \[ 🚧 Work in progress 🛠 👷🔧👷‍♀️️🔧️ 🚧 \]

[Screenshot of the most simplest configuration]

## Gradle Dependency

Add the dependency to your app's `build.gradle`:

```groovy
implementation 'com.r4sh33d:SlidingUpMenu:0.0.1'
```
## Usage
It is very easy to get started with `SlidingUpMenu`. Just specify a `Context`, a menu resource file: 

```kotlin
SlidingUpMenu(this, R.menu.sample_menu).show()
```

### Menu Items

You specify the menu items by supplying a `Menu` resource id and/or list of `MenuModel` items. If both menu resource id and list of MenuModel is specified, `SlidingUpMenu` will merge the sources together and present the menu items to users at once:

```kotlin
SlidingUpMenu(this, R.menu.sample_menu).show() // Menu resource id only
//or 
SlidingUpMenu(this, menuModelItems = menuItems).show() // List of MenuModel only
//or 
SlidingUpMenu(this, R.menu.sample_menu, menuItems).show() // Menu resource + List of MenuModel
```

```kotlin
SlidingUpMenu(this, R.menu.sample_menu).show {
   titleText(R.string.basic_title)
   menuModelSelected { slidingUpMenu, menuModel, position ->
      showClick(menuModel, position)
   }
}
```

### Title

You can specify the title for the menu dialog by supplying a `String` id to the `titleText` method.  

```kotlin
SlidingUpMenu(this, R.menu.sample_menu).show {
   titleText(R.string.basic_title)
}
```

You can also specify a `String` for the title:

```kotlin
titleText(titleText = "Basic Title")
```


```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener)
```
Then prepare the camera for capturing by calling the `start()` method:
 
```kotlin
hiddenCam.start()
```
You can now start capturing images with:
```kotlin
hiddenCam.captureImage()
```
When you are no longer actively capturing images, stop the camera engine to free the camera hardware by calling:

```kotlin
hiddenCam.stop()
```
Finally, to clean up call

```kotlin
hiddenCam.destroy()
```

That's all for basic setup. The captured images should be save at the storage folder provided.

## Customisation
`HiddenCam` attempts to use some default values to improve ease of use.
 Customization can be done by supplying additional arguments to the `HiddenCam()` constructor. 

### Capture Mode (Reccuring or one shot)
The capture mode is specified by the `CaptureTimeFrequency` class. 

#### OneShot
This capture mode should be used when you want to capture images manually with the `capture()` function. 
It is the default setting for the `CaptureTimeFrequency` parameter

```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, captureFrequency = OneShot)
```
#### Recurring
This capture mode should be used when you want to activate continuous captures at a specified interval. The captures will start as 
soon as you call the the `start()` function on your `HiddenCam` instance. Manual calls to the `capture()` function will be ignored.

```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, captureFrequency = Recurring(captureIntervalMillis))
```
where captureIntervalMillis is the interval.

### Resolution and Aspect Ratio
You can either set `Resolution` or `AspectRatio`, not both.
If `Resolution` is not set, `HiddenCam` will try to use the best resolution based on the 
selected (or default) `AspectRatio` and phone's camera hardware capability. Resolution can manually be specified by passing a `Size` object to `HiddenCam` constructor. 
For example, a resolution of 1080 X 1920 can be applied as follows:

```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, targetResolution = Size(1080, 1920))
```
To override the phone's default Aspect ratio, you can pass a `TargetAspectRatio` enum to the camera instance. For example, an aspect ratio 16:9 can be applied as follows:
 
```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, targetAspectRatio = TargetAspectRatio.RATIO_16_9)
```

### Rotation
By default, the camera [rotation](https://developer.android.com/training/camerax/configuration#rotation) is set to match the default display's rotation during the creation of the `HiddenCam` object.
This should work for most cases. If you need something more specific, you can set the rotation:
```kotlin
val rotation: Int = {...}
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, targetRotation = rotation)
```

### Camera Type
You can use use the `CameraType` enum to specify weather you want to use a front camera, or a back camera. The default settings is  `FRONT_CAMERA`.
For example, to use a back camera, you can do:
```kotlin
val hiddenCam = HiddenCam(context, baseStorageFolder, captureListener, cameraType = CameraType.BACK_CAMERA)
```

### Full Configuration
Eventually, a fully customised `HiddenCam` instance would look like this: 

```kotlin
hiddenCam = HiddenCam(
            context = context,
            baseFileDirectory = baseStorageFolder,
            imageCapturedListener = captureListener,
            targetAspectRatio = TargetAspectRatio.RATIO_16_9,
            targetResolution = Size(1920, 1080),
            targetRotation = windowManager.defaultDisplay.rotation,
            cameraType = CameraType.FRONT_CAMERA,
            captureFrequency = Recurring(captureIntervalMillis = 15 * 1000)
        )
```

##  License

    Copyright (c) 2019 Rasheed Sulayman.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
