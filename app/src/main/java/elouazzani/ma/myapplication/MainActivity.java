package elouazzani.ma.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import elouazzani.ma.myapplication.DAO.PlaceDAOImp;
import elouazzani.ma.myapplication.Model.Place;
import elouazzani.ma.myapplication.Model.PlaceAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Place> placeList;
    private BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set Recycle view items

        PlaceAdapter placeAdapter=new PlaceAdapter(getApplicationContext(),
               getPlaceList());
        RecyclerView recyclerView=findViewById(R.id.mRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(placeAdapter);
        // set bottom toolbar
        BottomAppBar bottomAppBar= findViewById(R.id.bottombar);
        bottomAppBar.setTitle("Visit Me");
        setSupportActionBar(bottomAppBar);
       // bottomAppBar.setNavigationOnClickListener(this);
        //Floating add Button Action
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,AddPlaceActivity.class));
            }
        });
    }

    // load items places from db sqlite
    private List<Place> getPlaceList() {
        PlaceDAOImp placeDAOImp=new PlaceDAOImp(this);
        return placeDAOImp.getAllPlaces();
    }

    //
    private void openNavigationMenu() {

        //this will get the menu layout
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.navigation_view, null);
        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(bootomNavigation);
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        openNavigationMenu();

    }


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        for(int i=0;i<menu.size();i++)
        {
            MenuItem menuItem=menu.getItem(i);
            Drawable icon=menuItem.getIcon();
            if(icon!=null)
                DrawableCompat.setTint(icon, ContextCompat.getColor(this,R.color.colorWhite));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
