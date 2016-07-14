package com.ceria.pkl.voteq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
static String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutHome);
        tabLayout.addTab(tabLayout.newTab().setText("Vote List"));
        tabLayout.addTab(tabLayout.newTab().setText("My Vote List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pagerHome);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        SharedPreferences sharedPrefernces = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
        token = sharedPrefernces.getString("token", "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_vote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddVoteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", "");
            editor.commit();
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
