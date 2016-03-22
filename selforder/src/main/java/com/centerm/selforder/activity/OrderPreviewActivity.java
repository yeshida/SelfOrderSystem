package com.centerm.selforder.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centerm.selforder.MyApplication;
import com.centerm.selforder.R;
import com.centerm.selforder.base.BaseActivity;
import com.centerm.selforder.bean.OrderedDish;
import com.centerm.selforder.fragment.OrderOptionsFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by linwanliang on 2016/3/9.
 * 订单预览界面（订单确认）
 */
public class OrderPreviewActivity extends BaseActivity {

    private List<OrderedDish> orderedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);

        orderedData = getMyApp().getOrderedDishs();

        ListView orderedListView = (ListView) findViewById(R.id.my_ordered_list);
        orderedListView.setAdapter(new ListAdapter(this, orderedData, getDefaultImageOptions()));

        getFragmentManager().beginTransaction()
                .replace(R.id.right_container, new OrderOptionsFragment(orderedData)).commit();
    }


    private static class ListAdapter extends BaseAdapter {

        private final static int RESOURCE = R.layout.ordered_dishes_list_item3;
        private Context context;
        private List<OrderedDish> data;
        private DisplayImageOptions options;

        public ListAdapter(Context context, List<OrderedDish> data, DisplayImageOptions imageOptions) {
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
            return data == null ? null : data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.ordered_dishes_list_item3, null);
            }
            OrderedDish itemData = (OrderedDish) getItem(position);
            convertView.setTag(itemData);
            ImageView icon = (ImageView) convertView.findViewById(R.id.dish_pic_show);
            ImageLoader.getInstance().displayImage(itemData.getPicUrl(), icon, options);
            TextView name = (TextView) convertView.findViewById(R.id.dish_name_show);
            name.setText(itemData.getName());
            TextView taste = (TextView) convertView.findViewById(R.id.dish_taste_show);
            taste.setText(itemData.getSelectedTaste());
            TextView price = (TextView) convertView.findViewById(R.id.price_show);
            price.setText(itemData.getPrice() + "");
            TextView counts = (TextView) convertView.findViewById(R.id.counts_show);
            counts.setText(itemData.getCounts());

            return convertView;
        }
    }

}
