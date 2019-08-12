package com.zalo.trainingmenu.fundamental;


import android.os.Bundle;

import com.zalo.trainingmenu.R;
import com.zalo.trainingmenu.fundamental.index.ContentProviderDemoActivity;
import com.zalo.trainingmenu.fundamental.index.MultithreadingActivity;
import com.zalo.trainingmenu.model.Item;
import com.zalo.trainingmenu.fundamental.servicedemo.ServiceDemoActivity;
import com.zalo.trainingmenu.mainui.base.AbsMenuActivity;
import com.zalo.trainingmenu.fundamental.index.NetworkMenuActivity;

import java.util.ArrayList;
import java.util.List;

public class FundamentalActivity extends AbsMenuActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected List<Item> onRefreshDataList() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(Item.with(this).setTitle(R.string.service).setDescription(R.string.service_description).setDestinationActivityClass(ServiceDemoActivity.class).get());
        list.add(Item.with(this).setTitle(R.string.content_provider).setDescription(R.string.content_provider_description).setDestinationActivityClass(ContentProviderDemoActivity.class).get());
        list.add(Item.with(this).setTitle(R.string.network).setDescription(R.string.network_description).setDestinationActivityClass(NetworkMenuActivity.class).get());
        list.add(Item.with(this).setTitle(R.string.multithreading).setDescription(R.string.multithreading_description).setDestinationActivityClass(MultithreadingActivity.class).get());

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int title() {
        return R.string.fundamental;
    }
}