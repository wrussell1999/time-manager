package com.will_russell.timemanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DetailsFragment extends Fragment implements TimePicker.OnTimeChangedListener {
    private OnFragmentInteractionListener mListener;

    private TimePicker timePicker;
    private Handler uiHandler;
    private TextView startView;
    private TextView totalTimeView;
    private TextView totalTasksView;

    public DetailsFragment() {}

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        timePicker = view.findViewById(R.id.time_to_leave_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        startView = view.findViewById(R.id.time_to_start_view);
        totalTimeView = view.findViewById(R.id.total_time_view);
        totalTasksView = view.findViewById(R.id.total_tasks_view);
        uiHandler = new Handler();
        return view;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        uiHandler.post(new UIThread(hourOfDay, minute));

    }

    class UIThread implements Runnable {
        private int hourOfDay;
        private int minute;

        public UIThread(int hourOfDay, int minute) {
            this.hourOfDay = hourOfDay;
            this.minute = minute;
        }

        @Override
        public void run() {
            try {
                String startTime = getResources().getString(R.string.placeholder_start_time) + calculateStartTime(hourOfDay, minute);
                startView.setText(startTime);
                String totalTime = getResources().getString(R.string.placeholder_total_time) + Task.getTotalLength()  + getResources().getString(R.string.placeholder_minute);
                totalTimeView.setText(totalTime);
                String totalTasks = getResources().getString(R.string.placeholder_tasks_total) + Task.tasksList.size();
                totalTasksView.setText(totalTasks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String calculateStartTime(int hour, int minute) {
            int totalLength = Task.getTotalLength();
            String startTime = hour + ":" + minute;
            if (hour < 10 && minute< 10) {
                startTime = "0" + hour + ":0" + minute;
            }
            else if (hour < 10) {
                startTime = "0" + hour + ":" + minute;
            }
            else if (minute < 10) {
                startTime = hour + ":0" + minute;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime dateTime = LocalTime.parse(startTime, formatter);
            dateTime = dateTime.minusMinutes(totalLength);
            String finalStartTime = dateTime.format(formatter);
            return finalStartTime;
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
