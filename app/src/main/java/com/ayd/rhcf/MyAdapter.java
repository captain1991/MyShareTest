package com.ayd.rhcf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yxd on 2016/5/10.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<AppBean> list;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AppBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null ? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.app_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.name = (TextView) convertView.findViewById(R.id.app_name);
            holder.pac = (TextView) convertView.findViewById(R.id.app_package);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppBean appBean = list.get(position);

        holder.imageView.setImageDrawable(appBean.getDrawable());
        holder.name.setText(appBean.getName());
        holder.pac.setText(appBean.getPac());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView pac;
    }
}
