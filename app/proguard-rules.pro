# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Androidsdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
-keep public class com.couchbase.lite.** { *; }
-dontwarn com.couchbase.lite.**
-keep public class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**

-keep class com.couchbase.touchdb.TDCollateJSON { *; }
-dontwarn com.couchbase.touchdb.TDCollateJSON.**

-keep class com.couchbase.lite.*{ *; }
-dontwarn com.couchbase.lite.**

-keep class com.couchbase.lite.util.*{ *; }
-dontwarn com.couchbase.lite.util.**

-keep class com.couchbase.lite.store.*{ *; }
-dontwarn com.couchbase.lite.store.**

-keep class com.couchbase.lite.Manager.*{ *; }
-dontwarn com.couchbase.lite.Manager.**

-keep class com.couchbase.lite.Database.*{ *; }
-dontwarn com.couchbase.lite.Database.**

-keep class com.couchbase.cbforest.*{ *; }
-dontwarn com.couchbase.cbforest.**

-keep class couchbase.lite.listener.*{ *; }
-dontwarn couchbase.lite.listener.**

-keep class Acme.Serve.Serve.*{ *; }
-dontwarn Acme.Serve.Serve.**

-keep class Acme.Serve.SimpleAcceptor.*{ *; }
-dontwarn Acme.Serve.SimpleAcceptor.**

-keep class com.couchbase.lite.listener.LiteServer.serve.*{ *; }
-dontwarn com.couchbase.lite.listener.LiteServer.serve.**

-keep class Acme.Serve.*{ *; }
-dontwarn Acme.Serve.**

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
