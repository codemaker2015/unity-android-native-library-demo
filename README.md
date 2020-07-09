# Unity Android Native Library Demo

This project is covering the Android platform and creating a native Unity3D plugin for that platform. It will cover the creation an .AAR file.

The AAR file type is primarily associated with ‘Axis Archive’ by Apache Software Foundation. Compressed file package for Apache Axis 2 which contains classes and data for a Web Service. The AAR is a JAR (Java Archive) file and is therefore based on the standard ZIP file format.

Now that we have that covered, let’s start creating our native plugin.

## Toolset

* Android Studio Editor software
* An Android device to test/compile
* Unity editor

## Android project setup

A. Open Android Studio and create a new Android Project.
Give your project a name and a namespace.

![Screenshot 1](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot1.png)

Select the supported platforms for your application.

![Screenshot 2](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot2.png)

Finally “Finish” the project setup.
Your project has now been created.

B. Create a new Android Module, of type “Android Library”
Go to “File” -> “New” -> “New Module…”
This will bring up the module creation window.

Select “Android Library”.

![Screenshot 3](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot3.png)

Give your library a name, and a namespace…

![Screenshot 4](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot4.png)

Now finish your library creation

## The Android part

Now that your project has been setup, let’s look at the android/java code of our native plugin. In contrast of Objective-C, a Java class only has 1 file. So no header and body files.

We want our native plugin to print a string passed from Unity3D.

Select your module src folder: projectFolder -> moduleFolder – > src – >main -> java -> yourPlugin_folder
right click and select “New” -> Java Class.

For our example we will use our class “Alert“.

```
package plugins.vsoft.com.library;

/**
 * Created by Codemaker on 09-07-2020.
 */

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import android.os.Looper; //extend android application.

public class Alert extends Application
{
    // our native method, which will be called from Unity3D
    public void PrintString( final Context ctx, final String message )
    {
        //create / show an android toast, with message and short duration.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

The above piece of code takes 2 parameters,

the current android context and the message that will be shown.

That’s it for our plugin code.

## Compiling your Native Library

Now that we have our plugin code and module setup, let’s have a look at getting our native plugin compiled.
For this you have to make sure that the minimum support SDK is the same as you have specified inside Unity3D.

To compile our Android Module press the “Make Project” button ( CTRL + F9 ) in your toolbar.

![Screenshot 5](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot5.png)

Once the action is done, you are ready to use your native Android plugin. Open your module folder in your OS explorer and go to the “outputs / aar ” folder. Here you will find the release and debug versions of your native plugin. For this example we will use the release version.

## Implementing the native plugin inside Unity3D

Open your Unity3D project and make sure you have the following folder structure:

#### Assets / Plugins / Android

Place your native android .AAR file inside this folder.

Now let’s write some code to call the plugin.

```
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NativeCodeRunner : MonoBehaviour
{
	void Start(){
		CallNativePlugin();
	}

	//method that calls our native plugin.
	public void CallNativePlugin() {
		// Retrieve the UnityPlayer class.
		AndroidJavaClass unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");

		// Retrieve the UnityPlayerActivity object ( a.k.a. the current context )
		AndroidJavaObject unityActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");

		// Retrieve the "Bridge" from our native plugin.
		// ! Notice we define the complete package name.              
		AndroidJavaObject alert = new AndroidJavaObject("plugins.vsoft.com.library.Alert");

		// Setup the parameters we want to send to our native plugin.              
		object[] parameters = new object[2];
		parameters[0] = unityActivity;
		parameters[1] = "Hello World!";

		// Call PrintString in bridge, with our parameters.
		alert.Call("PrintString", parameters);
	}
}

```

Now you’re ready to test your native plugin!

To test your project, attach an Android device to your computer and run your project on your device. After loading the application you should see an native Android Toast showing your passed text.
Here’s a screenshot of what your app should look like.

![Screenshot 6](https://github.com/codemaker2015/unity-android-native-library-demo/blob/master/Screenshots/Screenshot6.png)

End