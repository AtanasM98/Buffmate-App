package com.YAStudio.buffmate;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_up_add_food);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        String name = getIntent().getExtras().getString("name");
        final double cal = getIntent().getExtras().getDouble("cal");
        double protein = getIntent().getExtras().getDouble("protein");
        double fat = getIntent().getExtras().getDouble("fat");
        double carbs = getIntent().getExtras().getDouble("carbs");

        final TextView tvName = findViewById(R.id.tvName);
        final TextView tvProt = findViewById(R.id.tvProtein);
        final TextView tvCarbs = findViewById(R.id.tvCarbs);
        final TextView tvFat = findViewById(R.id.tvFat);
        final TextView tvCal = findViewById(R.id.tvCal);
        final Button btnAdd = findViewById(R.id.btnAddMeal);

        tvName.setText(name);
        tvProt.setText(""+protein);
        tvCarbs.setText(""+carbs);
        tvFat.setText(""+fat);
        tvCal.setText(""+cal);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Globals.Cal += cal;
                Globals.updateCal();
                finish();
            }
        });


    }
}
