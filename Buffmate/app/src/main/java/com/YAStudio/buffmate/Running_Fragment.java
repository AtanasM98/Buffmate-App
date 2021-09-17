package com.YAStudio.buffmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// I've worked on this fragment as well.
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Running_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Running_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Running_Fragment extends Fragment
    implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int DISTANCE_RAN_RESULT = 1;
    public List<DayRunInformation> dri  = new ArrayList<>();
    private LocationManager locationManager;
    private Calendar cal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("'Week' w");
    SimpleDateFormat weekNo = new SimpleDateFormat("w");
    View view;

    private OnFragmentInteractionListener mListener;

    public Running_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Running_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Running_Fragment newInstance(String param1, String param2) {
        Running_Fragment fragment = new Running_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void goToTracking(View v){
        Intent intent = new Intent(getActivity(), MapsTrackingActivity.class);
        startActivityForResult(intent, DISTANCE_RAN_RESULT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == DISTANCE_RAN_RESULT && resultCode == Activity.RESULT_OK){
            dri.add(new DayRunInformation(data.getFloatExtra("distance", 0)));
            setDayRuns();
        }
    }

    private void setDayRuns(){

        final TextView tvMon, tvTue, tvWed, tvThur, tvFri, tvSat, tvSun;
        tvMon = getActivity().findViewById(R.id.tvMon);
        tvTue = getActivity().findViewById(R.id.tvTue);
        tvWed = getActivity().findViewById(R.id.tvWed);
        tvThur = getActivity().findViewById(R.id.tvThur);
        tvFri = getActivity().findViewById(R.id.tvFri);
        tvSat = getActivity().findViewById(R.id.tvSat);
        tvSun = getActivity().findViewById(R.id.tvSun);

        for(int i = 0; i < dri.size(); i++)
        {
            if(dri.get(i).date.contains("Mon")){
                tvMon.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Tue")){
                tvTue.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Wed")){
                tvWed.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Thur")){
                tvThur.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Fri")){
                tvFri.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Sat")){
                tvSat.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }
            if(dri.get(i).date.contains("Sun")){
                tvSun.setText(String.valueOf(dri.get(i).distanceRan) + "m");
            }

        }
    }

    private void setLinks(){
        final TextView mondayTv = view.findViewById(R.id.tvMonText);
        final TextView tuesdayTv = view.findViewById(R.id.tvTueText);
        final TextView wednesdayTv = view.findViewById(R.id.tvWedText);
        final TextView thursdayTv = view.findViewById(R.id.tvThurText);
        final TextView fridayTv = view.findViewById(R.id.tvFriText);
        final TextView saturdayTv = view.findViewById(R.id.tvSatText);
        final TextView sundayTv = view.findViewById(R.id.tvSunText);

        mondayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(1, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        tuesdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(2, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        wednesdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(3, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        thursdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(4, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        fridayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(5, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        saturdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(6, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
        sundayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWithDay(7, Integer.parseInt(weekNo.format(cal.getTime())));
            }
        });
    }

    private void popUpWithDay(int day, int week){
        Intent intent = new Intent(getActivity(), MapsPopUp.class);
        intent.putExtra("day", day);
        intent.putExtra("week", week);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_running_, container, false);

        final TextView tvWeek = view.findViewById(R.id.tvWeekRun);
        tvWeek.setText(format.format(cal.getTime()));

        final Button btnTrack = view.findViewById(R.id.btnStartTrack);
        btnTrack.setOnClickListener(this);

        setDayRuns();
        setLinks();
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        goToTracking(v);
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
