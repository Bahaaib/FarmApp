package com.ets.android_team.farmApp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ProductRecyclerAdapter extends RecyclerView.Adapter implements RewardedVideoAdListener {

    //Here we recieve from the calling Fragment :
    // The cards container List & The Parent Activity context
    private Context context;
    private ArrayList<ProductModel> adapterModel;
    private RewardedVideoAd rewardedVideoAd;

    {
        adapterModel = new ArrayList<>();
    }

    public ProductRecyclerAdapter(Context context, ArrayList<ProductModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        rewardedVideoAd.setRewardedVideoAdListener(this);

    }

    //Here We tell the RecyclerView what to show at each element of it..it'd be a cardView!
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new ProductViewHolder(view);
    }

    //Here We tell the RecyclerView what to show at each CardView..
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ProductViewHolder) holder).BindView(position);

    }

    private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd("ca-app-pub-6702076183097498/6513925667",
                new AdRequest.Builder().build());
    }

    @Override
    public int getItemCount() {
        return adapterModel.size();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.i("Ad Statuss", "Ad Loaded");
        rewardedVideoAd.show();

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.i("Ad Statuss", "Ad Opened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.i("Ad Statuss", "Ad Started");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.i("Ad Statuss", "Ad closed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.i("Ad Statuss", "Ad Left App");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.i("Ad Statuss", "Ad Failed to load");
    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.i("Ad Statuss", "Ad Completed");
    }

    //Here we bind all the children views of each cardView with their corresponding
    // actions to show & interact with them
    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout productLabel;
        private TextView availabilityText, productName, productPrice;
        private ImageView productImg;
        private CardView productCard;

        public ProductViewHolder(View itemView) {
            super(itemView);

            productLabel = itemView.findViewById(R.id.product_label);
            availabilityText = itemView.findViewById(R.id.available_tv);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImg = itemView.findViewById(R.id.product_img);
            productCard = itemView.findViewById(R.id.product_card);

        }


        //Here where all the glory being made..!
        public void BindView(final int position) {
            boolean isAvailable = adapterModel.get(position).getAvailability();

            if (!isAvailable) {
                productLabel.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                availabilityText.setText(context.getResources().getString(R.string.is_not_available));
                availabilityText.setTextSize(12);
                availabilityText.setPadding(0, 4, 0, 0);

                adapterModel.get(position).setPrice(0);
            } else {
                productLabel.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                availabilityText.setText(context.getResources().getString(R.string.is_available));
                availabilityText.setTextSize(16);
            }

            productName.setText(adapterModel.get(position).getName_ar());

            float fprice = adapterModel.get(position).getPrice();
            String price = String.format(Locale.getDefault(), "%.2f", fprice);
            String unit = context.getResources().getString(R.string.currency_unit);
            String total = price + " " + unit;
            productPrice.setText(total);

            Picasso.with(context)
                    .load(adapterModel.get(position).getImg_url())
                    .fit()
                    .into(productImg);

            productCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadRewardedVideoAd();
                }
            });

        }


    }


}

