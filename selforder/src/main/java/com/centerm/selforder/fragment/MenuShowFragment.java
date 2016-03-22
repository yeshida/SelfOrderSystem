package com.centerm.selforder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.activity.MainOrderingActivity;
import com.centerm.selforder.adapter.SmallPicListAdapter;
import com.centerm.selforder.base.BaseFragment;
import com.centerm.selforder.bean.OrderedDish;
import com.centerm.selforder.constant.PresKey;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.net.request.GetMenuRequest;
import com.centerm.selforder.net.response.MenuListResponse;
import com.centerm.selforder.net.response.MenuTypeListResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.ViewUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/14.
 * 菜单显示界面，切换不同的Tab栏会切换不同的菜单类别
 */
public class MenuShowFragment extends BaseFragment implements View.OnClickListener {

    /**
     * 备用ID
     */
    private final static int[] RESERVE_IDS = new int[]{R.id.id1, R.id.id2, R.id.id3, R.id.id4, R.id.id5
            , R.id.id6, R.id.id7, R.id.id8, R.id.id9, R.id.id10
            , R.id.id11, R.id.id12, R.id.id13, R.id.id14, R.id.id15
            , R.id.id16, R.id.id17, R.id.id18, R.id.id19, R.id.id20};

    private RadioGroup typeContainer;
    private ImageView bigPicShow;
    private TextView dishNameShow;
    private Button chooseAttrsBtn;
    private TextView dishPriceShow;
    private View chooseAttrLayer;

    private EditText countsEdit;
    private RadioGroup tasteContainer;


    private ListView picListView;
    private SmallPicListAdapter picListAdapter;
    private int selectedPos;

    private MenuTypeListResponse menuTypeData;
    private MenuListResponse menuData;
    private int[] typeTabIds;
    private int[] tasteTabIds;

    private String merchantId;

    public MenuShowFragment() {
        super();
    }

    public MenuShowFragment(MenuTypeListResponse menuTypeData) {
        super();
        this.menuTypeData = menuTypeData;
        merchantId = getDefaultPres().getString(PresKey.MERCHANT_ID, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_show, null);
        return initView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_attr_btn:
                chooseAttrLayer.setVisibility(View.VISIBLE);
                break;
            case R.id.minus_btn:
                plusOrMinus(-1, menuData.getEntityList().get(selectedPos).getInventory());
                break;
            case R.id.plus_btn:
                plusOrMinus(1, menuData.getEntityList().get(selectedPos).getInventory());
                break;
            case R.id.cancel_btn:
                chooseAttrLayer.setVisibility(View.GONE);
                break;
            case R.id.confirm_btn:
                int orderedCounts = Integer.valueOf(countsEdit.getText().toString());
                int tasteCheckId = tasteContainer.getCheckedRadioButtonId();
                RadioButton button = (RadioButton) chooseAttrLayer.findViewById(tasteCheckId);
                String taste = button.getText().toString();
                OrderedDish dish = new OrderedDish(menuData.getEntityList().get(selectedPos), taste, orderedCounts);
                getMyActivity().addOrUpdateOrderedDish(dish);
                break;
        }
    }


    private View initView(View view) {
        typeContainer = (RadioGroup) view.findViewById(R.id.type_container);
        bigPicShow = (ImageView) view.findViewById(R.id.big_pic_show);
        dishNameShow = (TextView) view.findViewById(R.id.dish_name_show);
        chooseAttrsBtn = (Button) view.findViewById(R.id.choose_attr_btn);
        dishPriceShow = (TextView) view.findViewById(R.id.price_show);
        chooseAttrLayer = view.findViewById(R.id.layer_choose_dish_attrs);
        picListView = (ListView) view.findViewById(R.id.small_pic_list);
        picListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                if (picListAdapter == null || picListAdapter.getData() == null) {
                    return;
                }
                Map<String, String> itemData = picListAdapter.getData().get(position);
                fillBigPicViewArea(itemData);
            }
        });


        if (menuTypeData != null) {
            List<Map<String, String>> data = menuTypeData.getMenuTypeList();
            initTypeGroup(data);
        }
        return view;
    }

    /**
     * 添加顶部菜单类别的Tab栏
     *
     * @param data
     */
    private void addTypeTabs(List<Map<String, String>> data) {
        if (typeContainer == null) {
            return;
        }
        int size = data.size();
        if (size > RESERVE_IDS.length) {
            return;
        }
        typeTabIds = new int[size];
        System.arraycopy(RESERVE_IDS, 0, typeTabIds, 0, size);
        typeContainer.removeAllViews();
        for (int i = 0; i < size; i++) {
            Map<String, String> map = data.get(i);
            RadioButton tab = new RadioButton(getMyActivity());
            tab.setId(typeTabIds[i]);
            tab.setText(map.get(Key.MENUTP_NAME));
            tab.setTag(map.get(Key.MENUTP_ID));
            typeContainer.addView(tab);
        }
    }

    private void addTasteTabs(String[] tastes) {
        tasteContainer = (RadioGroup) chooseAttrLayer.findViewById(R.id.options_group);
        int size = tastes.length;
        if (size > RESERVE_IDS.length) {
            return;
        }
        tasteContainer.removeAllViews();
        tasteTabIds = new int[size];
        System.arraycopy(RESERVE_IDS, 0, tasteTabIds, 0, size);
        for (int i = 0; i < size; i++) {
            RadioButton tab = new RadioButton(getActivity());
            tab.setText(tastes[i]);
            tab.setId(tasteTabIds[i]);
            tasteContainer.addView(tab);
        }
    }

    private int plusOrMinus(int num, int max) {
        int nowCounts = Integer.valueOf(countsEdit.getText().toString());
        int result = nowCounts + num;
        if (result < 1) {
            return -1;
        } else if (result > max) {
            return 1;
        } else {
            countsEdit.setText("" + result);
            return 0;
        }
    }


    /**
     * 初始化类型选择Tab栏
     *
     * @param typeList
     */
    private void initTypeGroup(List<Map<String, String>> typeList) {
        addTypeTabs(typeList);
        typeContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String typeId = checkedIdMapTypeId(checkedId);
                requestMenu(merchantId, typeId);
            }
        });
        View view = getView().findViewById(R.id.id1);
        if ((view != null && view instanceof RadioButton)) {
            ((RadioButton) view).setChecked(true);
        }
    }


    /**
     * 填充大图显示区域
     *
     * @param data
     */
    private void fillBigPicViewArea(Map<String, String> data) {
        ImageLoader.getInstance()
                .displayImage(data.get(Key.PICTURE_LINK), bigPicShow, getDefaultImageOptions());
        String name = data.get(Key.PRODUCT_NAME);
        String tastes = data.get(Key.TASTE);
        if (tastes.equals("null") || tastes.equals("")) {
            chooseAttrLayer.setVisibility(View.GONE);
        } else {
            chooseAttrLayer.setVisibility(View.VISIBLE);
            chooseAttrsBtn.setOnClickListener(this);
            fillDishAttrChooseView(data);
        }
        String price = data.get(Key.PRICE);
        dishNameShow.setText(name);
        dishPriceShow.setText(price);
    }

    /**
     * 填充菜品属性选择视图
     *
     * @param data
     */
    private void fillDishAttrChooseView(Map<String, String> data) {
        TextView dishNameShow = (TextView) chooseAttrLayer.findViewById(R.id.dish_name_show);
        TextView dishDetailShow = (TextView) chooseAttrLayer.findViewById(R.id.dish_detail_show);
        TextView dishPriceShow = (TextView) chooseAttrLayer.findViewById(R.id.price_show);
        countsEdit = (EditText) chooseAttrLayer.findViewById(R.id.counts_edit);
        chooseAttrLayer.findViewById(R.id.minus_btn).setOnClickListener(this);
        chooseAttrLayer.findViewById(R.id.plus_btn).setOnClickListener(this);
        chooseAttrLayer.findViewById(R.id.cancel_btn).setOnClickListener(this);
        chooseAttrLayer.findViewById(R.id.confirm_btn).setOnClickListener(this);
        dishNameShow.setText(data.get(Key.PRODUCT_NAME));
        dishDetailShow.setText(data.get(Key.PRODUCT_DETAIL));
        dishPriceShow.setText(data.get(Key.PRICE));
        String tastes = data.get(Key.TASTE);
        if (tastes != null && !tastes.equals("") && !tastes.equals("null")) {
            String[] array = tastes.split("|");
            addTasteTabs(array);
        }
    }


    /**
     * 填充小图列表
     *
     * @param data
     */
    private void fillPicListView(List<Map<String, String>> data) {
        if (picListView == null || data == null) {
            return;
        }
        if (picListAdapter == null) {
            picListAdapter = new SmallPicListAdapter(getMyActivity(), data, getDefaultImageOptions());
            picListView.setAdapter(picListAdapter);
        } else {
            List<Map<String, String>> oldData = picListAdapter.getData();
            oldData.clear();
            oldData.addAll(data);
            picListAdapter.notifyDataSetChanged();
            picListView.performItemClick(picListView.getChildAt(0), 0, picListAdapter.getItemId(0));
        }
    }

    private MainOrderingActivity getMyActivity() {
        return (MainOrderingActivity) super.getActivity();
    }


    /**
     * 根据菜单类型ID，获取具体的菜单
     *
     * @param merchantId
     * @param typeId
     */
    private void requestMenu(String merchantId, String typeId) {
        //暂时不支持分页
        GetMenuRequest request = new GetMenuRequest(merchantId, typeId, 100, 0);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                MenuListResponse result = new MenuListResponse(response);
                if (result.isSuccess()) {
                    fillPicListView(result.getDataList());
                    menuData = result;
                } else {
                    ViewUtils.showToast(getMyActivity(), result.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                ViewUtils.showToast(getMyActivity(), "获取失败，" + statusCode);
            }
        };
        httpClient.post(getMyActivity(), request, handler);
    }

    private String checkedIdMapTypeId(int checkedId) {
        for (int i = 0; i < typeTabIds.length; i++) {
            if (checkedId == typeTabIds[i]) {
                return menuTypeData.getMenuTypeList().get(i).get(Key.MENUTP_ID);
            }
        }
        return null;
    }

}
