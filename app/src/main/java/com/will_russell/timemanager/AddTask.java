package com.will_russell.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_icon);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                TaskFragment.notifyUpdate();
                finish();
            }
        });
    }

    private boolean getData() {
        TextInputEditText name = findViewById(R.id.name_edittext);
        TextInputEditText length = findViewById(R.id.duration_edittext);
        if (name.getText().toString().equals("") || length.getText().toString().equals("")) {
            return false;
        }
        Task task = new Task(name.getText().toString(), Integer.valueOf(length.getText().toString()));
        Task.tasksList.add(task);
        Toast toast = Toast.makeText(getApplicationContext(), "Task added!", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
