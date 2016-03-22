package com.centerm.selforder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.centerm.selforder.activity.MainOrderingActivity;
import com.centerm.selforder.base.BaseFragment;
import com.centerm.selforder.bean.OrderedDish;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.centerm.selforder.R;


/**
 * Created by linwanliang on 2016/3/14.
 * 已点菜品视图
 */
public class OrderedDishesFragment extends BaseFragment implements View.OnClickListener {

    //    private final static String KEY_DISH_ID = "KEY_DISH_ID";
    private final static String KEY_DISH_NAME = "KEY_DISH_NAME";
    private final static String KEY_DISH_TYPE = "KEY_DISH_TYPE";
    private final static String KEY_DISH_COUNTS = "KEY_DISH_COUNTS";
    private final static String KEY_DISH_AMOUNTS = "KEY_DISH_AMOUNTS";


    private LinkedList<OrderedDish> orderedData;
    private List<Map<String, String>> listData;

    private TextView allAmountShow;
    private ListView orderedListView;
    private ListViewAdapter listAdapter;
    private Map<String, String> menuTypeMap;

    public OrderedDishesFragment() {
        super();
    }

    public OrderedDishesFragment(Map<String, String> menuTypeMap) {
        super();
        this.menuTypeMap = menuTypeMap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered_dishes, null);
        return initView(view);
    }

    private View initView(View view) {
        view.findViewById(R.id.clear_btn).setOnClickListener(this);
        view.findViewById(R.id.modify_btn).setOnClickListener(this);
        view.findViewById(R.id.submit_btn).setOnClickListener(this);
        orderedListView = (ListView) view.findViewById(R.id.my_ordered_list);
        allAmountShow = (TextView) view.findViewById(R.id.amount_show);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_btn:
                orderedData.clear();
                listAdapter.notifyDataSetChanged();
                allAmountShow.setText(calculateAllAmount());
                break;

            case R.id.modify_btn:
                if (getActivity() instanceof MainOrderingActivity) {
                    getMyApp().setOrderedDishs(orderedData);
                    ((MainOrderingActivity) getActivity()).goModifyOrder();
                }
                break;

            case R.id.submit_btn:
                if (getActivity() instanceof MainOrderingActivity) {
                    getMyApp().setOrderedDishs(orderedData);
                    ((MainOrderingActivity) getActivity()).goConfirmOrder();
                }
                break;
        }
    }

    /**
     * 添加菜品
     *
     * @param dish
     */
    public void addOrUpdate(OrderedDish dish) {
        if (orderedData == null) {
            orderedData = new LinkedList<OrderedDish>();
            listData = new ArrayList<Map<String, String>>();
            listAdapter = new ListViewAdapter(getActivity(), listData);
            orderedListView.setAdapter(listAdapter);
        }
        int position = -1;
        if (orderedData.contains(dish)) {
            position = orderedData.indexOf(dish);
            orderedData.add(position, dish);
        }
        Map<String, String> listItemData = null;
        if (position != -1) {
            listItemData = listData.get(position);
        } else {
            listItemData = new HashMap<String, String>();
        }
        listItemData.put(KEY_DISH_NAME, dish.getName()
                + "("
                + (dish.getTastes() == null ? "" : dish.getTastes())
                + ")");
        listItemData.put(KEY_DISH_COUNTS, dish.getCounts() + "");
        listItemData.put(KEY_DISH_TYPE, getMenuTypeMap().get(dish.getTypeId()));
        listItemData.put(KEY_DISH_AMOUNTS, dish.getAmounts() + "");
        listData.add(position, listItemData);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * 计算总金额
     *
     * @return
     */
    private String calculateAllAmount() {
        if (orderedData == null) {
            return "0.00";
        }
        double amount = 0;
        for (int i = 0; i < orderedData.size(); i++) {
            double d = orderedData.get(i).getAmounts();
            amount += d;
        }
        DecimalFormat formatter = new DecimalFormat("#.00");
        return formatter.format(amount);
    }

    /**
     * 删除菜品
     *
     * @param dish
     */
    public void deleteDish(OrderedDish dish) {
        if (dish == null || orderedData == null || !orderedData.contains(dish)) {
            return;
        }
        int position = orderedData.indexOf(dish);
        orderedData.remove(position);
        listData.remove(position);
        listAdapter.notifyDataSetChanged();
    }


/*    public void modifyDish(String dishId, String nameAndTaste, int counts, int amounts) {
        if (orderedData == null) {
            return;
        }
        Integer position = idMapPosition.get(dishId);
        if (position != null) {
            Map<String, String> map = orderedData.get(position);
            map.put(KEY_DISH_NAME, nameAndTaste);
            map.put(KEY_DISH_COUNTS, "" + counts);
            map.put(KEY_DISH_AMOUNTS, "" + amounts);
            listAdapter.notifyDataSetChanged();
            allAmountShow.setText(calculateAllAmount());
        }
    }*/

    /**
     * 获取菜单类型字典
     *
     * @return
     */
    public Map<String, String> getMenuTypeMap() {
        return menuTypeMap;
    }


    /**
     * 设置菜单类型字典
     *
     * @param menuTypeMap
     */
    public void setMenuTypeMap(Map<String, String> menuTypeMap) {
        this.menuTypeMap = menuTypeMap;
    }


    private static class ListViewAdapter extends SimpleAdapter {

        private final static int RESOURCE = R.layout.ordered_dishes_list_item;
        private final static String[] FROM =
                new String[]{KEY_DISH_NAME, KEY_DISH_TYPE, KEY_DISH_COUNTS, KEY_DISH_AMOUNTS};
        private final static int[] TO =
                new int[]{R.id.dish_name_and_taste_show, R.id.dish_type_show, R.id.dish_counts_show, R.id.dish_counts_show};


        public ListViewAdapter(Context context, List<? extends Map<String, ?>> data) {
            super(context, data, RESOURCE, FROM, TO);
        }
    }

}
