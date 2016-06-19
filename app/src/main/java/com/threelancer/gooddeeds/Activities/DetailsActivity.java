package com.threelancer.gooddeeds.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.threelancer.gooddeeds.Fragments.DetailsFragment;
import com.threelancer.gooddeeds.R;


public class DetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton backNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        toolbar = (Toolbar) findViewById(R.id.toolbar_details);

        backNavigation = (ImageButton) findViewById(R.id.back_nav_btn);

        backNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(null == savedInstanceState){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_container, new DetailsFragment()).commit();
        }
    }
}
