package com.bftv.myapplication;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class MyAdapter extends BaseAdapter {

    private List<DateInfo> infos;

    public MyAdapter(List<DateInfo> infos) {
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        View views = View.inflate(viewGroup.getContext(),R.layout.item,null);
        TextView tv = views.findViewById(R.id.tv);
        tv.setText(infos.get(i).getNum());
        final Intent intent = new Intent(viewGroup.getContext(),PlayActivity.class);
        intent.putExtra("URL",infos.get(i).getUrl());
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewGroup.getContext().startActivity(intent);
            }
        });
        return views;
    }
}
