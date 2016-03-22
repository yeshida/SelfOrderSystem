package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.constant.ValueDeliverKey;
import com.centerm.selforder.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主管界面
 * 用gridview实现
 */
public class MainManagerActivity extends SubpageActivity implements View.OnClickListener {

    private String menuTypeResponse;

    private GridView manager_gridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private Button button_manaher, button_cancellation, back_btn;

    // 图片封装为一个数组（6张图片,先代替）
    private int[] manager_img = new int[]{R.drawable.cqy_back, R.drawable.cqy_back_pre, R.drawable.cqy_face_happy,
            R.drawable.cqy_face_sad, R.drawable.cqy_user, R.drawable.cqy_password};

    private String[] manager_string = new String[]{"菜单管理", "操作员管理", "商户信息", "订单查询", "设置", "统计报表"};
    private ScreenUtils screenutils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Intent intent = getIntent();
        menuTypeResponse = intent.getStringExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE);

        init_data();


        //获取数据
        getData();
        //配置适配器
        manager_gridview.setAdapter(new manager_adapter());
        manager_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //菜单管理
                        Intent intent = new Intent(MainManagerActivity.this, MenuTypeEditActivity.class);
                        intent.putExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE, menuTypeResponse);
                        startActivity(intent);
                        break;
                    case 1:
                        //操作员管理
                        break;
                    case 2:
                        //商户信息
                        break;
                    case 3:
                        //订单查询
                        break;
                    case 4:
                        //设置
                        break;
                    case 5:
                        //统计报表
                        break;
                }
            }
        });
    }

    private class manager_adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return manager_img.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(MainManagerActivity.this).inflate(R.layout.item_manager_gridview, parent, false);
            }

            ImageView img = (ImageView) convertView.findViewById(R.id.item_manager_image);
            TextView tv = (TextView) convertView.findViewById(R.id.item_manager_text);
            img.setImageResource(manager_img[position]);
            tv.setText(manager_string[position]);
            convertView.setLayoutParams(new GridView.LayoutParams(screenutils.getWidth() / 3, (screenutils.getHeight() - getbasetheight()) / 2));

            //其他代码
            return convertView;
        }
    }


    private void init_data() {

        setSubTitle("商户名称");
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);

        screenutils = new ScreenUtils(this);
        this.manager_gridview = (GridView) findViewById(R.id.manager_gridView);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();

        button_manaher = (Button) findViewById(R.id.custome_btn1);// 主管按钮
        button_manaher.setOnClickListener(this);

        button_cancellation = (Button) findViewById(R.id.custome_btn2);//注销按钮
        button_cancellation.setOnClickListener(this);
    }


    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < manager_img.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", manager_img[i]);
            map.put("text", manager_string[i]);
            data_list.add(map);
        }

        return data_list;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.custome_btn1:
                break;
            case R.id.custome_btn2:
                finish();
        }
    }
}
