package elouazzani.ma.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import elouazzani.ma.myapplication.DAO.FeedBackDAOImp;
import elouazzani.ma.myapplication.DAO.PlaceDAO;
import elouazzani.ma.myapplication.DAO.PlaceDAOImp;
import elouazzani.ma.myapplication.Model.FeedBack;
import elouazzani.ma.myapplication.Model.Place;

public class PlaceActivity extends AppCompatActivity {
    private Place place;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageButton expandButton;
    boolean expandButtonStatu=false;
    private TextView descriptionText, avgRatingText;
    private FeedBackDAOImp feedBackDAOImp;
    private RatingBar addRatingBar, ratingBarIndic;
    private Button sendFeedBackButton;

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
        place=(Place) intent.getSerializableExtra("place");

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
        feedBackDAOImp=new FeedBackDAOImp(this);
        ratingBarIndic=findViewById(R.id.ratingBar);
        addRatingBar=findViewById(R.id.addRating);
        avgRatingText=findViewById(R.id.avgRating);
        setRatingBarIndic();
        //
        expandButton=findViewById(R.id.expand_button);

        //
        sendFeedBackButton=findViewById(R.id.sendFeedBack);


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

    public void setRatingBarIndic() {

        float rate= feedBackDAOImp.averageRatingPlace(place);
        int total_rating=feedBackDAOImp.totalRatingPlace(place);
        if (rate > 0) {
            ratingBarIndic.setRating(rate);
            avgRatingText.setText(String.format("%.1f",rate)+" ("+total_rating+")");
        }
    }

   public void onClickSendFeedBack(View view) {

        if (view==sendFeedBackButton) {
            final float rate=addRatingBar.getRating();
            if(rate > 0) {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("");
                builder.setMessage("Your Rate is "+rate +" For this Place");
                builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean statu=feedBackDAOImp.saveFeedback(new FeedBack(rate,null,place));
                        if (statu) {
                            Toast.makeText(PlaceActivity.this, "FeedBack send success", Toast.LENGTH_LONG).show();
                            PlaceDAO placeDAO=new PlaceDAOImp(getApplicationContext());
                            placeDAO.updatePlaceByRate(place,feedBackDAOImp.averageRatingPlace(place));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else {
                Toast.makeText(PlaceActivity.this, "Please Rate this Place ", Toast.LENGTH_LONG).show();
            }

        }

   }


}
