package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.bean.OrderedDish;
import com.centerm.selforder.constant.ValueDeliverKey;
import com.centerm.selforder.constant.PresKey;
import com.centerm.selforder.fragment.MenuShowFragment;
import com.centerm.selforder.fragment.OrderedDishesFragment;
import com.centerm.selforder.net.response.MenuTypeListResponse;
import com.centerm.selforder.utils.ViewUtils;

import java.util.List;
import java.util.Map;

/**
 * 主界面，即自助点餐界面。
 * 继承SubpageActivity是为了复用它的标题栏。
 */
public class MainOrderingActivity extends SubpageActivity implements OnClickListener {


    private final static int REQ_GO_MODFIY_ORDER = 0x250;
    private final static int REQ_GO_CONFIRM_ORDER = 0x251;


    //类型映射具体的菜单列表
    private Map<String, List<Map<String, String>>> idMapMenuList;
    //商户号，接口访问必须参数
    private String merchantId;
    //获取菜单类型的网络返回解析对象
    private MenuTypeListResponse menuTypeEntity;

    private OrderedDishesFragment orderedDishesFragment;
    private MenuShowFragment menuShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        merchantId = getDefaultPres().getString(PresKey.MERCHANT_ID, null);

        getCustomeButton2().setText("注销");
        getCustomeButton2().setOnClickListener(this);
        hideBackBtn();

        Intent intent = getIntent();
        if (intent != null) {
            String operId = intent.getStringExtra(ValueDeliverKey.KEY_OPER_ID);
            String merchantName = intent.getStringExtra(ValueDeliverKey.KEY_MERCHANT_NAME);
            menuTypeEntity = new MenuTypeListResponse(intent.getStringExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE));
            setSubTitle(merchantName == null ? "" : merchantName);
            getCustomeButton1().setText(operId == null ? "" : operId);
        }

        orderedDishesFragment = new OrderedDishesFragment();
        menuShowFragment = new MenuShowFragment(menuTypeEntity);

        getFragmentManager().beginTransaction()
                .add(R.id.left_container, orderedDishesFragment)
                .add(R.id.right_container, menuShowFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_GO_MODFIY_ORDER:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custome_btn1:
                break;
            case R.id.custome_btn2:
                logout();
                break;
        }
    }


    public void addOrUpdateOrderedDish(OrderedDish dish) {
        orderedDishesFragment.addOrUpdate(dish);
    }

    /**
     * 跳转到修改订单界面
     */
    public void goModifyOrder() {
        Intent intent = new Intent(this, ModifyOrderActivity.class);
        startActivityForResult(intent, REQ_GO_MODFIY_ORDER);
    }

    /**
     * 跳转到订单确认界面
     */
    public void goConfirmOrder() {
        Intent intent = new Intent(this, OrderPreviewActivity.class);
        startActivityForResult(intent, REQ_GO_CONFIRM_ORDER);
    }


    /**
     * 注销登录
     */
    private void logout() {
        ViewUtils.showToast(this, "注销登录，未实现");
    }


}
