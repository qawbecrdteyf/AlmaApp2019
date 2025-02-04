package iitbbs.almafiesta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    Toolbar toolbar = null;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }
}*/
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.titlebar2);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HelperClass.putSharedPreferencesString(this,"Layout","0");
        setFragment(new dataFragment('n'));//init

        new CountDownTimer(2147483647, 1000) {

            public void onTick(long millisUntilFinished) {
                try {
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date2 = myFormat.parse("11/01/2019");
                    Date date1 = new Date();

                    long dif = date2.getTime() - date1.getTime();
                    long ddif = 0;
                    long hdif = 0;
                    long mdif = 0;
                    long sdif = 0;

                    if (dif > 0) {
                        ddif = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
                        hdif = TimeUnit.HOURS.convert(dif, TimeUnit.MILLISECONDS) % 24;
                        mdif = TimeUnit.MINUTES.convert(dif, TimeUnit.MILLISECONDS) % 60;
                        sdif = TimeUnit.SECONDS.convert(dif, TimeUnit.MILLISECONDS) % 60;

                        TextView days = (TextView) findViewById(R.id.days);

                        if (days != null) {
                            String s = "";
                            if (ddif > 9) s = s + ddif;
                            else s = s + "0" + ddif;
                            s = s + ":";
                            if (hdif > 9) s = s + hdif;
                            else s = s + "0" + hdif;
                            s = s + ":";
                            if (mdif > 9) s = s + mdif;
                            else s = s + "0" + mdif;
                            s = s + ":";
                            if (sdif > 9) s = s + sdif;
                            else s = s + "0" + sdif;
                            days.setText(s);
                        } else System.out.println("null");
                    } else {
                        TextView days = (TextView) findViewById(R.id.days);
                        TextView togo = (TextView) findViewById(R.id.togo);

                        if (days != null) {
                            days.setText("The Fun is On");
                            togo.setVisibility(View.GONE);
                        } else System.out.println("null");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            public void onFinish() {
                try {
                    TextView days = (TextView) findViewById(R.id.days);
                    TextView togo = (TextView) findViewById(R.id.togo);

                    if (days != null) {
                        days.setText("The Fun is On");
                        togo.setVisibility(View.GONE);
                    } else System.out.println("null");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.one:
                Toast.makeText(getApplicationContext(),"Button 1 clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                Toast.makeText(getApplicationContext(),"Button 2 clicked",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Weird",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            try {
                getFragmentManager().popBackStackImmediate();
            }catch(Exception e)
            {
                setFragment(new dataFragment('n'));
                Log.e("Got you",e.getMessage());
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            setFragment(new aboutFragment());
            toolbar.setTitle("About Us");
            return true;
        }
        else if (id == R.id.action_search) {
            setFragment(new searchFragment());
            toolbar.setTitle("Search");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void myFancyMethod3(View view)
    {
        switch (view.getId())
        {
            /*case R.id.textView7rang:
                HelperClass.putSharedPreferencesString(this,"event","Rangoli");
                setFragment(new registerFragment());
                break;*/
                //Add others...
        }
    }
    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        HelperClass.putSharedPreferencesString(this,"Layout","0");
        switch (id) {
            case R.id.nav_news:
                setFragment(new dataFragment('n'));
                toolbar.setTitle("Alma Fiesta");
                break;
            case R.id.nav_map:
                try {
                    setFragment(new mapFragment());
                    HelperClass.putSharedPreferencesString(this,"Map","1");
                    toolbar.setTitle("Map");
                }catch (Exception e)
                {
                    Log.e("Map problems",e.getMessage());
                }
                break;
            case R.id.nav_you:
                setFragment(new youFragment());
                toolbar.setTitle("Your details");
                break;
            case R.id.nav_team:
                setFragment(new dataFragment('t'));
                toolbar.setTitle("Co-ordinators");
                break;
            case R.id.nav_music:
                setFragment(new dataFragment('m'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_dance:
                setFragment(new dataFragment('c'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_drams:
                setFragment(new dataFragment('d'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_lito:
                setFragment(new dataFragment('l'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_arts:
                setFragment(new dataFragment('a'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_quiz:
                setFragment(new dataFragment('q'));
                toolbar.setTitle("Events");
                break;
            case R.id.nav_film:
                setFragment(new dataFragment('f'));
                toolbar.setTitle("Events");
                break;
        }

        return true;
    }
    public void CC(View view)
    {
        HelperClass.putSharedPreferencesString(this,"Map","2");
        setFragment(new mapFragment());
    }
    public void SES(View view)
    {
        HelperClass.putSharedPreferencesString(this,"Map","3");
        setFragment(new mapFragment());
    }
    public void SBS(View view)
    {
        HelperClass.putSharedPreferencesString(this,"Map","4");
        setFragment(new mapFragment());
    }
    public void Sports(View view)
    {
        HelperClass.putSharedPreferencesString(this,"Map","5");
        setFragment(new mapFragment());
    }
    public void SC(View view)
    {
        HelperClass.putSharedPreferencesString(this,"Map","6");
        setFragment(new mapFragment());
    }
    public void myFancyMethod(View view)
    {
        switch(view.getId())
        {
            case R.id.one:
                Button button=(Button)findViewById(R.id.one);
                String str=button.getText()+"";
                String url = "tel:" + str.substring(str.length()-12);
                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                break;
            case R.id.two:
                button=(Button)findViewById(R.id.two);
                str=button.getText()+"";
                url = "tel:" + str.substring(str.length()-12);
                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            default:
                //Toast.makeText(getApplicationContext(),"Weird",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @SuppressLint("ResourceType")
    public void setFragment(Fragment fragment) {
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.xml.enter_from_left,R.xml.exit_to_right,R.xml.enter_from_right,R.xml.exit_to_left);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void web(View view) {
        String url = "http://www.almafiesta.com";
        /*if (view.getId() == R.id.parDebate)
            url = "https://docs.google.com/forms/d/e/1FAIpQLSdILgFxK_74i0zvBgMQotkgkqt2-9nWGVECpfCm5NsEFm74qQ/viewform";*/
        if (view.getId() == R.id.fb)
            url = "https://www.facebook.com/almafiesta/";
        else if (view.getId() == R.id.insta)
            url = "https://www.instagram.com/almafiesta.iitbbs/";
            //else if(view.getId()==R.id.twitter)
            //  url = "https://twitter.com/AlmaFiesta";
        else if (view.getId() == R.id.youtube)
            url = "https://www.youtube.com/channel/UCVAHFAfxXx0ZaOyS5VczKiA";
        /*else if (view.getId() == R.id.register)
            url = "http://register.almafiesta.com/";*/
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void contact(View view) {
        TextView num = (TextView)findViewById(view.getId());
        String number = String.valueOf(num.getText());
        String url = "tel:" + number.substring(3);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
