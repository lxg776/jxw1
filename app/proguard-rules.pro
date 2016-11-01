# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in e:\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-dontshrink
-dontpreverify
-dontoptimize
-dontusemixedcaseclassnames

-flattenpackagehierarchy
-allowaccessmodification
-printmapping map.txt

-optimizationpasses 7
-verbose
-keepattributes Exceptions,InnerClasses
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}
-keep class com.xiwang.jxw.intf.WebImageIntf{*;}




-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#----------------------------------------------------------------------------
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep public class com.xiwang.jxw.R$*{
	public static final int *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * extends java.net.URLDecoder
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.backup.BackupAgentHelper

-dontwarn
-dontskipnonpubliclibraryclassmembers

-keepattributes Signature

-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keep class android.support.v4.** { *; }
-dontwarn android.support.v4.*
-dontwarn android.support.**

#-libraryjars libs/eventbus-2.4.0.jar
-keepclassmembers class ** {
    public void onEvent*(**);
}





#-libraryjars libs/armeabi/libentryexstd.so
#-libraryjars libs/armeabi/liblocSDK6a.so

#-libraryjars libs/alipaySDK-20150818.jar
#-libraryjars libs/BaiduLBS_Android.jar
#-libraryjars libs/achartengine-1.1.0.jar
#-libraryjars libs/fastjson-1.1.38.jar
#-libraryjars libs/httpmime-4.2.2.jar
#-libraryjars libs/libammsdk.jar
#-libraryjars libs/UPPayAssistEx.jar
#-libraryjars libs/UPPayPluginExStd.jar
#-libraryjars libs/umeng-analytics-v5.6.3.jar
#-libraryjars libs/UPPayAssistEx.jar
#-libraryjars libs/UPPayPluginExStd.jar
#-libraryjars libs/wftsdk2.0.jar
-keep public interface com.tencent.**
-keep public class com.tencent.** {*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable



-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*

-keep class com.umeng.scrshot.**

-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}

-dontwarn com.umeng.**




-dontwarn org.apache.commons.net.**
-dontwarn com.tencent.**
-dontwarn com.alibaba.fastjson.**
-dontwarn com.alipay.**
-dontwarn org.apache.http.entity.mime.**
-dontwarn com.baidu.**
-dontwarn android.support.**


-keep class com.alipay.** { *;}

-keep class org.apache.http.entity.mime.**{*;}
-keep class org.apache.http.entity.mime.content.**{*;}


#-keep class com.fuiou.pay.** {*;}
#-keep class com.fuiou.pay.activity.** {*;}
#-keep class com.fuiou.pay.plugin.** {*;}
#-keep class com.fuiou.pay.view.** { *; }

-keep class android.webkit.JavascriptInterface {*;}

-keep class com.fuiou.pay.plugin.JsPlugin {*;}
#-keep class com.fuiou.pay.activity.FyWebActivity {*;}

-keepclassmembers class com.fuiou.pay.activity.FyWebActivity {
   public *;
 }

 -keep public class com.fuiou.pay.plugin.JsPlugin$JSCallBack {
     public *;
 }

#-keepnames class com.fuiou.pay.plugin.JsPlugin$* {
#    public <fields>;
#    public <methods>;
#}




#-keepattributes *JavascriptInterface*


-keep class com.tencent.mm.sdk.openapi.** {*;}
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

-keep class com.baidu.mapapi.** {*; }
-keep class assets.** {*; }
-keep class com.baidu.** {*; }
-keep class vi.com.gdi.bgl.** {*; }
-keep class com.baidu.location.h.c.** {*; }




#-injars      bin/classes
-injars      libs
#-outjars     bin/classes-processed.jar



-keep class com.github.mikephil.charting.** { *; }

#-keep class com.baidu.** { *; }
#-keep class vi.com.gdi.bgl.android.**{*;}

-keep class com.alibaba.fastjson.** {*;}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#----------------------------------------------------------------------------
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


