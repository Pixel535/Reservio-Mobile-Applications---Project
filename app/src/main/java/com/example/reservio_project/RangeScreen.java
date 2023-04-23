package com.example.reservio_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RangeScreen extends AppCompatActivity {

    private static final String TAG = "RangeScreen";
    EditText rangeInput;
    TextView errorMsg;
    Integer range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.range_layout);

        errorMsg = (TextView) findViewById(R.id.error);
        rangeInput = (EditText) findViewById(R.id.textInput);

        Button btnNavToRestaurantsNearMe = (Button) findViewById(R.id.GiveRangeButton);
        btnNavToRestaurantsNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rangeStr = rangeInput.getText().toString();
                if(!rangeStr.isEmpty())
                {
                    try {
                        range = Integer.parseInt(rangeStr);
                        if (range <= 5000)
                        {
                            Intent intent = new Intent(RangeScreen.this, RestaurantsNearMeScreen.class);
                            intent.putExtra("range", range);
                            startActivity(intent);
                        }
                        else
                        {
                            errorMsg.setVisibility(View.VISIBLE);
                            rangeInput.getText().clear();
                        }
                    }
                    catch (Exception e)
                    {
                        errorMsg.setVisibility(View.VISIBLE);
                        rangeInput.getText().clear();
                    }
                }
                else
                {
                    errorMsg.setVisibility(View.VISIBLE);
                    rangeInput.getText().clear();
                }
            }
        });
    }
}