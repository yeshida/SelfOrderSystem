package com.centerm.selforder.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiqingyuan on 16/3/14.
 */
public class MenuManagerActivity extends SubpageActivity implements View.OnClickListener{

    private GridView menu_type_gridview;
    private Button button_menu_preview,button_menu_edit,back_btn;
    private List<String> menu_type_list;//存储菜单名称
    private boolean is_edit = false;
    private int menu_type_count = 0;//先假设这个代表分类数量
    private ImageView item_img;
    private TextView item_tv;
    private ImageButton item_img_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        init_data();
        menu_type_count = load_menu_type_data();//初始化菜单数据，并将种类存储进变量

    }

    private void init_data() {

        setSubTitle("菜单管理");
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        menu_type_gridview = (GridView) findViewById(R.id.manager_gridView);
        button_menu_preview = (Button) findViewById(R.id.custome_btn1);//预览按钮
        button_menu_preview.setOnClickListener(this);
        button_menu_preview.setText("预览");

        button_menu_edit = (Button) findViewById(R.id.custome_btn2);//编辑按钮
        button_menu_edit.setOnClickListener(this);
        button_menu_edit.setText("编辑");


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.custome_btn1:
                //跳转到菜单预览界面
                break;
            case R.id.custome_btn2:
                //菜单编辑
                if(is_edit)
                {
                    //取消编辑菜单
                    is_edit = false;
                }else
                {
                    //正在编辑菜单
                    is_edit = true;
                }
                break;
        }
    }
    public int load_menu_type_data()
    {
        //发送数据获取菜单类别


        return 0;
    }

    private class menu_type_adapter  extends BaseAdapter
    {

        @Override
        public int getCount() {
            return menu_type_count + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView == null)
            {
                convertView = LayoutInflater.from(MenuManagerActivity.this).inflate(R.layout.item_manager_gridview,null);
                //根据is_edit来判断是否是编辑状态
                // is_edit = true : 进入菜单编辑模式
                // is_edit ＝ false : 直接根据与服务器交互得到的菜单数据进行加载
               // if()

            }
            return null;
        }
    }
}
