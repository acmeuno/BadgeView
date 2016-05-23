BadgeView
============
Customizable BadgeView for Android

Usage
============
Using the BadgeView is really simple. Just add the view to your layout XML.

```groovy
<com.carlosolmedo.badgeview.BadgeView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

You can modify the badge via xml or java
XML
--------
```groovy
<com.carlosolmedo.badgeview.BadgeView
        xmlns:badge_view="http://schemas.android.com/apk/res-auto"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        badge_view:badge_fillColor="@color/colorPrimary"
        badge_view:badge_text="New!"
        badge_view:badge_textColor="@color/colorAccent"
        badge_view:badge_textSize="18"/>
```
Java
--------
```groovy
badge.setBadgeText("Text");
badge.setTextColor({color});
badge.setBadgeTextSize(30);
badge.setBadgeColor({color});
```

Gradle
============
In your project gradle
```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
```
In your module gradle
```groovy
compile 'com.github.acmeuno:badge-view:1.0'
```
