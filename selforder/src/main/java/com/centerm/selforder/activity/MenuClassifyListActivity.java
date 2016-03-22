package com.centerm.selforder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.constant.ValueDeliverKey;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.net.response.MenuTypeListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/21.
 */
public class MenuClassifyListActivity extends SubpageActivity {
    private MenuTypeListResponse menuTypeResponse;
    private GridView gridView;
    private Button button1,button2;
    private static Context context;
    private static List<Map<String, String>> menuList;
    private MenuListGridAdapter adapter;
    private static Boolean isEdit=false;
    private int pos;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        context= getApplicationContext();
        menuList = new ArrayList<>();
        Map<String,String> strings1= new HashMap<String,String>();
        strings1.put("image", "image1");
        strings1.put("name", "name1");
        Map<String,String> strings2= new HashMap<String,String>();
        strings2.put("image", "image2");
        strings2.put("name", "name2");
        Map<String,String> strings3= new HashMap<String,String>();
        strings3.put("image", "image3");
        strings3.put("name", "name3");
        Map<String,String> strings4= new HashMap<String,String>();
        strings4.put("image", "image4");
        strings4.put("name", "name4");
        Map<String,String> strings5= new HashMap<String,String>();
        strings5.put("image", "image5");
        strings5.put("name", "name5");
        Map<String,String> strings6= new HashMap<String,String>();
        strings6.put("image", "image6");
        strings6.put("name", "name6");
        menuList.add(strings1);
        menuList.add(strings2);
        menuList.add(strings3);
        menuList.add(strings4);
        menuList.add(strings5);
        menuList.add(strings6);
        //解析菜单的数据
       // menuTypeResponse = new MenuTypeListResponse(getIntent().getStringExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE));
        //menuList= menuTypeResponse.getMenuTypeList();
        //获取网格布局
        gridView = (GridView) findViewById(R.id.menu_list_grid_view);
        //添加适配器
         adapter = new MenuListGridAdapter(this, menuList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                EditText editText=  (EditText)view.findViewById(R.id.list_name_show);
               str =  editText.getText().toString();
                Log.i("======str====", str);
            }
        });
        setSubTitle("测试界面");
        button1=getCustomeButton1();
        button1.setText("编辑");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit = true;
                adapter.refrash();

            }
        });

        button2= getCustomeButton2();
        button2.setText("完成");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map=menuList.get(pos);
                map.put("name", str);
                menuList.add(pos, map);
                isEdit = false;
                adapter.refrash();

            }
        });
    }
private static Context getContext(){
    return context;
}

private static class MenuListGridAdapter extends SimpleAdapter{
    private final static int RESOURCE = R.layout.menu_list_grid_item;
    private final static String[] FROM ={"image", "name"};
    private final static int[] TO = {R.id.list_pic_show,R.id.list_name_show};

    public MenuListGridAdapter(Context context, List<? extends Map<String, ?>> data) {
        super(context, menuList, RESOURCE, FROM, TO);
    }

    @Override
    public int getCount() {
        return menuList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditText editText=null;
        String s = String.valueOf(position);
        String s1 = String.valueOf(getCount());
        Log.i("======position=======",s);
        Log.i("======getCount=======",s1);
        if (position == getCount() - 1) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = (View) inflater.inflate(R.layout.menu_list_grid_item, null);
            TextView textView1 = (TextView) view.findViewById(R.id.list_pic_show);
            textView1.setText("新建图片");
            editText = (EditText) view.findViewById(R.id.list_name_show);
            editText.setText("新建名字");
            Log.i("======isEdit=======", String.valueOf(isEdit));
            //editText.setFocusableInTouchMode(isEdit);
            //editText.setFocusable(isEdit);
            editText.setEnabled(isEdit);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("===click====", "click");
                    new AlertDialog.Builder(getContext()).setTitle("新增类别").show();
                }
            });
            return view;
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_grid_item, null);
                TextView textView=(TextView)convertView.findViewById(R.id.list_pic_show);
                textView.setText(menuList.get(position).get("image"));
                EditText editText2=(EditText)convertView.findViewById(R.id.list_name_show);
                editText2.setText(menuList.get(position).get("name"));
                editText2.setEnabled(isEdit);
                return super.getView(position, convertView, parent);
            }
            editText = (EditText) convertView.findViewById(R.id.list_name_show);
            Log.i("======isEdit=======", String.valueOf(isEdit));
            // editText.setFocusableInTouchMode(isEdit);
            //editText.setFocusable(isEdit);
            editText.setEnabled(isEdit);
            return super.getView(position, convertView, parent);
        }


    }

    private  void refrash(){
        notifyDataSetChanged();
    }
}

}
