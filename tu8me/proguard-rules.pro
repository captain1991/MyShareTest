# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Android_sdk/tools/proguard/proguard-android.txt
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
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keep class android.webkit.JavascriptInterface {*;}

#项目中所有调用js的类都应在此处注册，避免混淆，禁止所有的js方法混淆；
-keepclassmembers class com.xiaodong.tu8me.BaseWebViewActivity$MainJieInterface{
  public *;
}

