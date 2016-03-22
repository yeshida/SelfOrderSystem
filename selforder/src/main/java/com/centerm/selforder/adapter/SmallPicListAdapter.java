package com.centerm.selforder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.centerm.selforder.R;
import com.centerm.selforder.net.Key;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/15.
 * 菜单小图列表的数据适配器
 */
public class SmallPicListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> data;
    private DisplayImageOptions options;

    public SmallPicListAdapter(Context context, List<Map<String, String>> data, DisplayImageOptions imageOptions) {
        this.context = context;
        this.data = data;
        this.options = imageOptions;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? position : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dish_pic_list_item, null);
        }
        Map<String, String> itemData = data.get(position);
        convertView.setTag(itemData);
        ImageView picView = (ImageView) convertView.findViewById(R.id.small_pic_show);
        String url = itemData.get(Key.PICTURE_LINK);
        ImageLoader.getInstance().displayImage(url, picView, options);
        return convertView;
    }

//    private final static int RESOURCE = R.layout.dish_pic_list_item;
//    private final static String[] FROM = new String[]{Key.PICTURE_LINK};
//    private final static int[] TO = new int[]{R.id.small_pic_show};


    public List<Map<String, String>> getData() {
        return data;
    }
}
