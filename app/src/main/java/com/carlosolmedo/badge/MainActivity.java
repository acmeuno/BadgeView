package com.carlosolmedo.badge;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.carlosolmedo.badgeview.BadgeView;

public class MainActivity extends AppCompatActivity {

    BadgeView badge1, badge2, badge3;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        badge1 = (BadgeView) findViewById(R.id.badge1);
        badge2 = (BadgeView) findViewById(R.id.badge2);
        badge3 = (BadgeView) findViewById(R.id.badge3);

        badge3.setBadgeText(String.valueOf(counter));
        badge3.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        badge3.setBadgeTextSize(30);
        badge3.setBadgeColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
    }

    public void addBadge(View view) {
        badge1.setBadgeText(String.valueOf(counter++));
    }
}
