package com.example.administrator.addpicturedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class Adapter extends BaseAdapter{
    List<String> path;
    Context context ;

    public Adapter(List<String> path, Context context) {
        this.path = path;
        this.context = context;
    }

    @Override
    public int getCount() {
        return path.size();
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
    public View getView(int i, View contentView, ViewGroup viewGroup) {

        ViewHolder vh =null;

        if (contentView==null){
            vh=new ViewHolder();


            contentView= LayoutInflater.from(context).inflate(R.layout.item_login,null);

            vh.img=contentView.findViewById(R.id.image);

            contentView.setTag(vh);

        }else {

            vh= (ViewHolder) contentView.getTag();
        }

        Glide.with(context).load(path.get(i)).into(vh.img);
        return contentView;
    }

    class ViewHolder {
        ImageView img;
    }
}
