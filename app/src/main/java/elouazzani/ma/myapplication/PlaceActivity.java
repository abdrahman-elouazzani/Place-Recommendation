package elouazzani.ma.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import elouazzani.ma.myapplication.Model.Place;

public class PlaceActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageButton expandButton;
    boolean expandButtonStatu=false;
    TextView descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        instancesObj();

    }

    private void instancesObj() {

        // toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpTo(PlaceActivity.this,
                        new Intent(PlaceActivity.this,MainActivity.class));
            }
        });

        // intent for getting data Place from itemView
        Intent intent=getIntent();
        Place place=(Place) intent.getSerializableExtra("place");

        // toolbar title
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(place.getTitle());

        // Text Title
        TextView textTitle=findViewById(R.id.textTitle);
        textTitle.setText(place.getTitle());

        // Text Info ( city and address )
        TextView infoText=findViewById(R.id.textInfo);
        infoText.setText(place.getCity()+" - "+place.getAddress());

        // description text
        descriptionText=findViewById(R.id.description);
        descriptionText.setText(place.getDescription());

        // set Image of the place
        ImageView imageView=findViewById(R.id.background);
        Glide.with(this).asBitmap().load(place.getImageByte()).into(imageView);

        //Rating Five Start
        RatingBar ratingBar=findViewById(R.id.ratingBar);

        //
        expandButton=findViewById(R.id.expand_button);


    }
    public void expandClick(View view) {
        if(view==expandButton) {

            if(expandButtonStatu) {
                descriptionText.setVisibility(View.VISIBLE);
                expandButton.setImageResource(R.mipmap.baseline_expand_less_black_24);
            }
            else {
                descriptionText.setVisibility(View.GONE);
                expandButton.setImageResource(R.mipmap.baseline_expand_more_black_24);
            }

            expandButtonStatu=!expandButtonStatu;
        }
    }


}
