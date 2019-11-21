package elouazzani.ma.myapplication.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import elouazzani.ma.myapplication.DAO.FeedBackDAOImp;
import elouazzani.ma.myapplication.DAO.PlaceDAOImp;
import elouazzani.ma.myapplication.PlaceActivity;
import elouazzani.ma.myapplication.R;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    Context context;
    private List<Place> mPlaceList;

    public PlaceAdapter(Context context,List<Place> mPlaceList ) {
        this.context=context;
        this.mPlaceList=mPlaceList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumblnail;
        TextView titleText, ratingInfoText;
        RatingBar ratingBarIndic;
        Button action_share, action_explore;
        ImageButton more_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumblnail=itemView.findViewById(R.id.thumblnail);
            thumblnail.setClickable(true);
            titleText=itemView.findViewById(R.id.title);
            ratingInfoText=itemView.findViewById(R.id.ratingInfo);
            ratingBarIndic=itemView.findViewById(R.id.ratingBarIndic);
            action_share=itemView.findViewById(R.id.action_button_share);
            action_explore=itemView.findViewById(R.id.action_button_explore);
            more_button=itemView.findViewById(R.id.more_button);


        }
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, final int position) {
        final Place place=mPlaceList.get(position);
        if(place!=null) {

            holder.titleText.setText(place.getCity()+" - "+place.getTitle());
            Glide.with(context).asBitmap().load(place.getImageByte()).into(holder.thumblnail);
            FeedBackDAOImp feedBackDAOImp=new FeedBackDAOImp(context);
            float rate=feedBackDAOImp.averageRatingPlace(place);
            int total_rating=feedBackDAOImp.totalRatingPlace(place);
            holder.ratingBarIndic.setRating(rate);
            holder.ratingInfoText.setText(String.format("%.1f",rate)+"  ("+total_rating+")");

            holder.thumblnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=(new Intent(v.getContext(), PlaceActivity.class));
                    intent.putExtra("place",place);
                    v.getContext().startActivity(intent);

                }
            });

            holder.action_explore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=(new Intent(v.getContext(), PlaceActivity.class));
                    intent.putExtra("place",place);
                    v.getContext().startActivity(intent);
                }
            });

            holder.more_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu=new PopupMenu(context,v);
                    MenuInflater menuInflater=popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_place,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.item_remove:
                                    PlaceDAOImp placeDAOImp=new PlaceDAOImp(context);
                                    if(placeDAOImp.removePlaceItem(place)) {
                                        mPlaceList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeRemoved(position,mPlaceList.size());
                                        Toast.makeText(context, "item removed", Toast.LENGTH_LONG).show();
                                    }


                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mPlaceList!=null)
           return mPlaceList.size();
        return 0;
    }


}
