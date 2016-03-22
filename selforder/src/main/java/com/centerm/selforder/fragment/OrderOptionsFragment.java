package com.centerm.selforder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.BaseFragment;
import com.centerm.selforder.bean.OrderedDish;
import com.centerm.selforder.utils.ViewUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by linwanliang on 2016/3/16.
 */
public class OrderOptionsFragment extends BaseFragment implements View.OnClickListener {

    private List<OrderedDish> orderedData;

    private TextView payModeShow;

    public OrderOptionsFragment() {
        super();
    }

    public OrderOptionsFragment(List<OrderedDish> orderedData) {
        super();
        this.orderedData = orderedData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_options, null);
        return initView(view);
    }

    private View initView(final View view) {
        view.findViewById(R.id.pay_mode_btn);
        payModeShow = (TextView) view.findViewById(R.id.pay_mode_show);
        CheckBox checkPack = (CheckBox) view.findViewById(R.id.check_pack);
        checkPack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.pack_cost_area).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.pack_cost_area).setVisibility(View.GONE);
                }
            }
        });
        TextView countsShow = (TextView) view.findViewById(R.id.counts_show);
        countsShow.setText("小计：共" + orderedData.size() + "样");

        TextView amountsShow = (TextView) view.findViewById(R.id.amount_show);
        amountsShow.setText(calculateAllAmount());

        view.findViewById(R.id.go_pay_btn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_mode_btn:
                ViewUtils.showToast(getActivity(), "选择支付方式");
                break;

            case R.id.go_pay_btn:
                ViewUtils.showToast(getActivity(), "生成订单，支付");
                break;
        }
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


}
