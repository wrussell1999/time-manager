package com.will_russell.timemanager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class Tasks extends AppCompatActivity {


    private SharedPreferences prefs = null;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private final int ADD_TASK_REQUEST = 1;
    private final int IMPORT_FILE_REQUEST = 2;
    private final int READ_ACCESS = 3;
    private final static int WRITE_ACCESS = 4;

    public final static String FILENAME = "task_data.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);

        prefs = getSharedPreferences("com.will_russell.timemanager", MODE_PRIVATE);
        readData();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
                addTaskIntent();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            String filename = "task_data.csv";
            File file = new File(getApplicationContext().getFilesDir(), filename);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    private void addTaskIntent() {
        Intent intent = new Intent(this, AddTask.class);
        startActivityForResult(intent, ADD_TASK_REQUEST);
    }

    public void readData() {
        try {
            FileInputStream in =  openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String input = "";
            Task.tasksList.clear();
            while ((input = reader.readLine()) != null) {
                input = input.trim();
                String[] taskString = input.split(",");
                Task.tasksList.add(new Task(taskString[0], Integer.valueOf(taskString[1])));
            }
            System.out.println("READ");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData(Context context, String filename) {

        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String output = "";
            for (int i = 0; i < Task.tasksList.size(); i++) {
                output = Task.tasksList.get(i).getName() + "," + Task.tasksList.get(i).getLength() + System.getProperty("line.separator");
                System.out.println("Output: " + output);
                outputStream.write(output.getBytes());
            }
            outputStream.flush();
            outputStream.close();
            System.out.println("SAVED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportData() {
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMPORT_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            // Check for valid CSV else show AlertDialog
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_ACCESS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    return;
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                saveData(this, FILENAME);
                return true;
            case R.id.action_import:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/csv");
                startActivityForResult(intent, IMPORT_FILE_REQUEST);
                return true;
            case R.id.action_export:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {}

        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = DetailsFragment.newInstance();
                    break;
                case 1:
                    fragment = TaskFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
