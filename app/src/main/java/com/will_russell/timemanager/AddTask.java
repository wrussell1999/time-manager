package com.will_russell.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_icon);

        Intent intent = getIntent();
        int position = intent.getIntExtra("task_index", -1);

        if (position != -1) {
            this.getSupportActionBar().setTitle(getResources().getString(R.string.edit_task_title));
            Task task = Task.tasksList.get(position);
            TextInputEditText nameView = findViewById(R.id.name_edittext);
            TextInputEditText lengthView = findViewById(R.id.duration_edittext);
            nameView.setText(task.getName());
            lengthView.setText(task.getLength().toString());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            getData(position);
            TaskFragment.notifyUpdate();
            Tasks.saveData(this);
            finish();
        });
    }

    private boolean getData(int position) {
        TextInputEditText name = findViewById(R.id.name_edittext);
        TextInputEditText length = findViewById(R.id.duration_edittext);
        if (name.getText().toString().equals("") || length.getText().toString().equals("")) {
            return false;
        }
        String toastMessage = "";
        if (position == -1) {
            Task task = new Task(name.getText().toString(), Integer.valueOf(length.getText().toString()));
            Task.tasksList.add(task);
            toastMessage = getResources().getString(R.string.toast_add_task);

        } else {
            Task task = Task.tasksList.get(position);
            task.setName(name.getText().toString());
            task.setLength(Integer.valueOf(length.getText().toString()));
            Task.tasksList.set(position, task);
            toastMessage = getResources().getString(R.string.toast_edit_task);
        }
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
