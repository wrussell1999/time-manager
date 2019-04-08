package com.will_russell.timemanager;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements TimePicker.OnTimeChangedListener {
    private OnFragmentInteractionListener mListener;

    private Handler uiHandler;
    private TextView startView;
    private TextView totalTimeView;

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
        TimePicker timePicker = view.findViewById(R.id.time_to_leave_picker);
        timePicker.setOnTimeChangedListener(this);

        startView = view.findViewById(R.id.time_to_start_view);
        totalTimeView = view.findViewById(R.id.total_time_view);
        uiHandler = new Handler();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
                String startTime = "Start time: " + hourOfDay + ":" + minute;
                System.out.println(startTime);
                startView.setText(startTime);
                //totalTimeView.setText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
