package com.romanusynin.mobreg.mobreg;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRegionActivity  extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_region_layout);

        Button buttonOmsk = (Button) findViewById(R.id.buttonOmsk);
        buttonOmsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegionActivity.this, HospitalsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonOmskState = (Button) findViewById(R.id.buttonOmskState);
        buttonOmskState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegionActivity.this, RegionsActivity.class);
                startActivity(intent);
            }
        });

    }
}
