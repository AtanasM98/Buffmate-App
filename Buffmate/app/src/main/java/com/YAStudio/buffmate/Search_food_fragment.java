package com.YAStudio.buffmate;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_food_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_food_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final String baseURL = "https://api.edamam.com/api/food-database/parser?nutrition-type=logging&ingr=";
    final String endURL = "&app_id=7401c4eb&app_key=c3aceffeff29a5c8beaa5461b7e6c897";
    boolean b = true;
    boolean a = true;
    List<Food> hints = new ArrayList<>();

    private static int PERMISSION_INTERNET = 1;

    View view;


    public Search_food_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_food_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_food_fragment newInstance(String param1, String param2) {
        Search_food_fragment fragment = new Search_food_fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_food, container, false);
        //Check for permissions
        //Check for permission of using internet
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED){
            //ask for permission
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.INTERNET}, PERMISSION_INTERNET);
        }
        // Inflate the layout for this fragment

        final Button btnSearch = view.findViewById(R.id.btnSearch);
        final EditText lblRes = view.findViewById(R.id.lblResult);
        final EditText lblRel = view.findViewById(R.id.lblRelated);
        final EditText etSearch = view.findViewById(R.id.ptFoodName);

        lblRes.setEnabled(false);
        lblRel.setEnabled(false);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendGetRequest(etSearch.getText().toString());
            }
        });

        ExtraUI(false);

        return view;

    }

    private void OpenPopUp(final String name,final double cal,final double protein,final double fat, final double carbs){
        final Button btnResult = view.findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Pop.class);
                intent.putExtra("name",name);
                intent.putExtra("cal",cal);
                intent.putExtra("protein",protein);
                intent.putExtra("fat",fat);
                intent.putExtra("carbs",carbs);

                startActivity(intent);


            }
        });
    }

    private void CreateBtnInSv(final String txt){
        final Button newBtn = new Button(getActivity());
        final LinearLayout llHints = view.findViewById(R.id.llHints);

        newBtn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        newBtn.setText(txt);
        newBtn.setTextSize(18);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendGetRequest(txt);
                    }
                });
            }
        });

        llHints.addView(newBtn);
    }

    private void ResetSearchUi(){
        final EditText etSearch = view.findViewById(R.id.ptFoodName);
        final Button btnResult = view.findViewById(R.id.btnResult);
        final LinearLayout llHints = view.findViewById(R.id.llHints);
        llHints.removeAllViews();
        etSearch.setText("");
        btnResult.setText("");
        //btnResult.setOnClickListener(null);
    }

    private void ExtraUI(boolean a){
        final Button btnResult = view.findViewById(R.id.btnResult);
        final ScrollView sv = view.findViewById(R.id.svHints);
        final EditText lblRes = view.findViewById(R.id.lblResult);
        final EditText lblRel = view.findViewById(R.id.lblRelated);
        if (a){
            btnResult.setEnabled(true);
            btnResult.setVisibility(View.VISIBLE);

            sv.setEnabled(true);
            sv.setVisibility(View.VISIBLE);

            //lblRes.setEnabled(true);
            lblRes.setVisibility(View.VISIBLE);

            // lblRel.setEnabled(true);
            lblRel.setVisibility(View.VISIBLE);

        }else{
            btnResult.setEnabled(false);
            btnResult.setVisibility(View.INVISIBLE);

            sv.setEnabled(false);
            sv.setVisibility(View.INVISIBLE);

            // lblRes.setEnabled(false);
            lblRes.setVisibility(View.INVISIBLE);

            //  lblRel.setEnabled(false);
            lblRel.setVisibility(View.INVISIBLE);
        }
    }

    private void SendGetRequest(String searchTxt){

        Log.i("TEST" , "TEST 1");
        //clear previous search
        ResetSearchUi();
        Log.i("TEST" , "TEST 2");
        final Button btnResult = view.findViewById(R.id.btnResult);
        //pop up to add calories

         btnResult.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        //pop up
        }
        });

        //request JSON object by string
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        Log.i("TEST" , "TEST 3");

        ExtraUI(true);
        /*if (b){
           ExtraUI(true);
           b = false;
        }else{
            ExtraUI(false);
            b = true;
        }*/


        Log.i("Food","request sent");
        JsonObjectRequest objReq = new JsonObjectRequest(
                Request.Method.GET,
                baseURL + searchTxt +endURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resObj = new JSONObject((response.toString()));
                            //result
                            JSONArray results = resObj.getJSONArray("parsed");
                            JSONObject res1 = results.getJSONObject(0);

                            JSONObject food1 = res1.getJSONObject("food");

                            String nameOfFood = food1.getString("label");

                            JSONObject nut = food1.getJSONObject("nutrients");

                            double cal;
                            try {
                                cal = nut.getDouble("ENERC_KCAL");
                            }catch (Exception ex){
                                cal  = 0;
                            }

                            double prot;
                            try {
                                prot = nut.getDouble("PROCNT");
                            }catch (Exception ex){
                                prot = 0;
                            }

                            double fat;
                            try {
                                fat = nut.getDouble("FAT");
                            }catch (Exception ex){
                                fat = 0;
                            }

                            double carbs;
                            try {
                                carbs = nut.getDouble("CHOCDF");
                            }catch (Exception ex){
                                carbs =0;
                            }


                            JSONObject mes = res1.getJSONObject("measure");

                            String mes1;
                            try {
                                mes1 = mes.getString("label");
                            }catch (Exception ex){
                                mes1 = "";
                            }


                            int quant;
                            try {
                                quant = res1.getInt("quantity");
                            }catch (Exception ex){
                                quant =0;
                            }

                            Food f1 = new Food(nameOfFood,quant,mes1,cal,prot,fat,carbs);

                            Log.i("Food", f1.toString());

                            //hints


                            JSONArray hintsArray = resObj.getJSONArray("hints");
                            hints.clear();
                            hints = new ArrayList<>();
                            for (int i = 0; i < hintsArray.length(); i++){
                                JSONObject hint = hintsArray.getJSONObject(i);

                                JSONObject hintFood = hint.getJSONObject("food");

                                String nm = hintFood.getString("label");



                                Food f = new Food(nm,0,"",0,0,0,0);
                                hints.add(f);

                            }
                            Log.i("Food", String.valueOf(hints.size()));

                            btnResult.setText(f1.name);
                            for (int x = 0; x < hints.size(); x++){
                                CreateBtnInSv(hints.get(x).name);
                            }

                            OpenPopUp(f1.name,f1.cal,f1.protein, f1.fat,f1.carbs);

                        }catch (Exception e){
                            Log.i("Food", /*e.toString()*/"error sending");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Food", "error received");
                        Log.i("Food", error.toString());
                    }
                }
        );
        requestQueue.add(objReq);
    }

}
