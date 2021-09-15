package com.YAStudio.buffmate;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {

    final float calGoalMin = 0;
    final float calGoalMax = 2000;
    float calGoalCurrent = 0;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        dialog = new Dialog(this);

        final Button btnAdd = findViewById(R.id.btnAddFood);
        final Button btnBack = findViewById(R.id.btnBack);
        final TextView tvCalCount = findViewById(R.id.tvCalCount);
        final TextView tvMessage = findViewById(R.id.tvFoodMessage);
        final ProgressBar pbCals = findViewById(R.id.pbFoodCal);
        tvCalCount.setText((String.valueOf(calGoalCurrent)));
        if(pbCals.getProgress() > 100){
            pbCals.setBackgroundResource(R.drawable.rectangle);
            tvMessage.setText("Warning! You are above daily Calorie intake!");
        }
        else{
            pbCals.setBackgroundResource(R.drawable.circle);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });
    }

    public void ShowPopup(View v){
        final TextView tvName;
        final TextView tvCal;
        final TextView tvCalCount;
        final TextView tvMessage;
        Button btnCancel;
        Button btnSave;
        dialog.setContentView(R.layout.addfoodpopup);
        tvCalCount = findViewById(R.id.tvCalCount);
        tvMessage = findViewById(R.id.tvFoodMessage);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnSave = dialog.findViewById(R.id.btnSave);
        tvName = dialog.findViewById(R.id.tbFoodName);
        tvCal = dialog.findViewById(R.id.tbCals);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(tvName.getText());
                int cal = Integer.parseInt(tvCal.getText().toString());
                Food tempFood = new Food();
                calGoalCurrent += tempFood.cal;
                ProgressBar pbCals = findViewById(R.id.pbFoodCal);
                Log.i("Prog", String.valueOf(calGoalCurrent));
                Log.i("Prog", String.valueOf(calGoalCurrent/calGoalMax));
                float progress = (calGoalCurrent/calGoalMax)*100;
                Log.i("Prog", String.valueOf(progress));
                pbCals.setProgress((int)progress);
                tvCalCount.setText(String.valueOf(calGoalCurrent));
                if(progress > 100){
                    tvMessage.setText("Warning! You are above daily Calorie intake!");
                    pbCals.setBackgroundResource(R.drawable.rectangle);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
