package com.example.klim3.apispacexdestroy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class Adapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Activity activity;
    private List<Item> items;

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Adapter(Activity activity,List<Item> items){
        this.activity=activity;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /***
     * @param position item position
     * @param convertView view inflated from custom_layout
     * @return convertView with initialized from items List data
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.custom_layout,null);
        }
        if( imageLoader == null)
            imageLoader=AppController.getInstance().getImageLoader();
            NetworkImageView imageView= (NetworkImageView) convertView.findViewById(R.id.imageView);
            TextView rocketName= (TextView) convertView.findViewById(R.id.tvRocketName);
            TextView launchDate= (TextView) convertView.findViewById(R.id.tvLaunchDate);
            TextView details= (TextView) convertView.findViewById(R.id.tvDetails);

            //getting data for row
            Item item=items.get(position);

            // setting data to views
            // imageView
            imageView.setImageUrl(item.getImage(), imageLoader);
            // rocketName
            rocketName.setText(item.getRocketName());
            // launchDate
            launchDate.setText(item.getLaunchDate());
            // details
            details.setText(item.getDetails());

        return convertView;
    }
}
