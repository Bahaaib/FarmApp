package com.ets.android_team.farmApp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ProductRecyclerAdapter extends RecyclerView.Adapter {

    //Here we recieve from the calling Fragment :
    // The cards container List & The Parent Activity context
    private Context context;
    private ArrayList<ProductModel> adapterModel;
    private InterstitialAd interstitialAd;
    private String testAd = "ca-app-pub-6702076183097498/9338536189";
    private CounterListener counterListener;

    {
        adapterModel = new ArrayList<>();
    }

    public ProductRecyclerAdapter(Context context, ArrayList<ProductModel> adapterModel) {
        this.context = context;
        this.adapterModel = adapterModel;
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(testAd);
        interstitialAd.loadAd(new AdRequest.Builder().build());

        //init listener
        counterListener = (CounterListener) context;


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


    @Override
    public int getItemCount() {
        return adapterModel.size();
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
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }
            });

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClicked() {
                    counterListener.onCounterIncreased();

                }
            });

        }


    }


}

