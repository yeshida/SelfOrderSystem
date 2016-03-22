package com.centerm.selforder.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.bean.OrderedDish;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by linwanliang on 2016/3/15.
 * 修改已点菜品界面
 */
public class ModifyOrderActivity extends SubpageActivity implements View.OnClickListener {

    private ListView orderedListView;
    private TextView amountShow;

    private LinkedList<OrderedDish> orderedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        orderedData = (LinkedList) getMyApp().getOrderedDishs();

        setSubTitle("我的菜单");
        orderedListView = (ListView) findViewById(R.id.my_ordered_list);
        amountShow = (TextView) findViewById(R.id.amount_show);
        findViewById(R.id.cancel_btn).setOnClickListener(this);
        findViewById(R.id.submit_btn).setOnClickListener(this);

        orderedListView.setAdapter(new OrderedListAdapter(this, orderedData, getDefaultImageOptions()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.submit_btn:
                break;
        }
    }

    private static class OrderedListAdapter extends BaseAdapter {

        private final static int TAG_KEY1 = 0x123;
        private final static int TAG_KEY2 = 0x124;

        private final static int RESOURCE = R.layout.ordered_dishes_list_item2;
        private Context context;
        private List<OrderedDish> data;
        private DisplayImageOptions options;

        private CompoundButton.OnCheckedChangeListener checkListener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        OrderedDish data = (OrderedDish) buttonView.getTag();
                        if (isChecked) {
                            data.setSelectedTaste(buttonView.getText().toString());
                        }
                    }
                };

        private View.OnClickListener clickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText bindView = (EditText) v.getTag(TAG_KEY1);
                        int max = (int) v.getTag(TAG_KEY2);
                        int now = Integer.valueOf(bindView.getText().toString());
                        switch (v.getId()) {
                            case R.id.minus_btn:
                                if (now > 1) {
                                    bindView.setText("" + (--now));
                                }
                                break;

                            case R.id.plus_btn:
                                if (now < max) {
                                    bindView.setText("" + (++now));
                                }
                                break;
                        }

                    }
                };


        public OrderedListAdapter(Context context, List<OrderedDish> data, DisplayImageOptions imageOptions) {
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
                convertView = inflater.inflate(RESOURCE, null);
            }
            bindView(convertView, data.get(position));
            return convertView;
        }

        private void bindView(View view, OrderedDish data) {
            view.setTag(data);
            ImageView icon = (ImageView) view.findViewById(R.id.dish_pic_show);
            TextView name = (TextView) view.findViewById(R.id.dish_name_show);
            RadioGroup tastesContainer = (RadioGroup) view.findViewById(R.id.tastes_container);
            TextView price = (TextView) view.findViewById(R.id.price_show);
            Button minus = (Button) view.findViewById(R.id.minus_btn);
            Button plus = (Button) view.findViewById(R.id.plus_btn);
            EditText counts = (EditText) view.findViewById(R.id.counts_edit);

            //加载图片
            ImageLoader.getInstance().displayImage(data.getPicUrl(), icon, options);
            //设置菜名和价格
            name.setText(data.getName());
            price.setText(data.getPrice() + "");
            //设置已点数量
            counts.setText(data.getCounts());
            //设置口味选择
            String[] tastes = data.getTasteArray();
            if (tastes != null) {
                String selectedTaste = data.getSelectedTaste();
                tastesContainer.removeAllViews();
                for (int i = 0; i < tastes.length; i++) {
                    RadioButton button = new RadioButton(context);
                    button.setTag(data);
                    button.setText(tastes[i]);
                    if (tastes[i].equals(selectedTaste)) {
                        button.setChecked(true);
                    }
                    button.setOnCheckedChangeListener(checkListener);
                }
            }
            //设置加减按钮事件
            plus.setTag(TAG_KEY1, counts);
            plus.setTag(TAG_KEY2, data.getCounts());
            plus.setOnClickListener(clickListener);
            minus.setTag(TAG_KEY1, counts);
            minus.setTag(TAG_KEY2, data.getCounts());
            minus.setOnClickListener(clickListener);
        }
    }
}
