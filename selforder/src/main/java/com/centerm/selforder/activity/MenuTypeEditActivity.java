package com.centerm.selforder.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.constant.ValueDeliverKey;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.net.response.MenuTypeListResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/18.
 * 菜单类型编辑界面
 */
public class MenuTypeEditActivity extends SubpageActivity {

    private MenuTypeListResponse menuTypeResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_type_edit);

        menuTypeResponse = new MenuTypeListResponse(getIntent().getStringExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE));
    }

    private static class MenuTypeGridAdapter extends SimpleAdapter {

        private final static int RESOURCE = R.layout.menu_type_grid_item;
        private final static String[] FROM = new String[]{Key.PICTURE_LINK, Key.MENUTP_NAME};
        private final static int[] TO = new int[]{R.id.type_pic_show, R.id.type_name_show};


        public MenuTypeGridAdapter(Context context, List<? extends Map<String, ?>> data) {
            super(context, data, RESOURCE, FROM, TO);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return super.getView(position, convertView, parent);
        }
    }


}
