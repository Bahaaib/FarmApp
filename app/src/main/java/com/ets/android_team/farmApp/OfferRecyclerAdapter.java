package com.ets.android_team.farmApp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<OfferModel> adapterModel;

    {
        adapterModel = new ArrayList<>();
    }

    public OfferRecyclerAdapter(Context context, ArrayList<OfferModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;
    }

    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.offer_card, parent, false);
        return new OfferRecyclerAdapter.OfferViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OfferRecyclerAdapter.OfferViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class OfferViewHolder extends RecyclerView.ViewHolder {

        private ImageView offerImage;


        public OfferViewHolder(View itemView) {
            super(itemView);

            offerImage = itemView.findViewById(R.id.offer_img);

        }


        //Here where all the glory being made..!
        public void BindView(final int position) {

            Picasso.with(context)
                    .load(adapterModel.get(position).getImg_url())
                    .fit()
                    .into(offerImage);

        }


    }
}
