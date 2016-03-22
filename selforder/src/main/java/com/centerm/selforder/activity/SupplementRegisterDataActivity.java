package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.centerm.selforder.MyApplication;
import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.support.BaiduLocationService;
import com.centerm.selforder.net.request.RegisterDetailRequest;
import com.centerm.selforder.net.response.RegisterDetailResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.StringUtils;
import com.centerm.selforder.utils.ViewUtils;

public class SupplementRegisterDataActivity extends SubpageActivity implements View.OnClickListener {

    private EditText et_phone_num,et_manager_real_name,et_id_card_num,et_shop_name,et_detail_address;
    private TextView tv_province,tv_city,tv_area;
    private Button register_detail_suc,back_btn;
    private String string_et_phone_num,string_et_manager_real_name,string_et_id_card_num,string_et_shop_name,string_et_detail_address;
    private BaiduLocationService locationService;
    private boolean is_register_detail_suc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_register_data);
        setSubTitle("补充注册资料");
        init_data();

        //百度定位
    }

    private void init_data() {

        this.et_phone_num = (EditText) findViewById(R.id.et_phone_num);
        this.et_manager_real_name = (EditText) findViewById(R.id.et_manager_real_name);
        this.et_id_card_num = (EditText) findViewById(R.id.et_id_card_num);
        this.et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        this.et_detail_address = (EditText) findViewById(R.id.et_detail_address);

        this.tv_province = (TextView) findViewById(R.id.tv_province);
        this.tv_city = (TextView) findViewById(R.id.tv_city);
        this.tv_area = (TextView) findViewById(R.id.tv_area);

        this.register_detail_suc  = (Button) findViewById(R.id.register_detail_suc);
        this.register_detail_suc.setOnClickListener(this);

        this.back_btn = (Button) findViewById(R.id.back_btn);
        this.back_btn.setOnClickListener(this);


    }
    public void decide_is_allright()
    {
        boolean is_allright = true;
        //先判断手机号码
        string_et_phone_num = et_phone_num.getText().toString().trim();
        string_et_manager_real_name = et_manager_real_name.getText().toString();
        string_et_id_card_num = et_id_card_num.getText().toString().trim();
        string_et_shop_name = et_shop_name.getText().toString();
        string_et_detail_address = et_detail_address.getText().toString();

        if(StringUtils.isStrNull(string_et_phone_num) || !StringUtils.isMobileNumValid(string_et_phone_num))
        {
            is_allright = false;
            ViewUtils.showToast(SupplementRegisterDataActivity.this,"手机号码有误");
        }else if(StringUtils.isStrNull(string_et_manager_real_name))
        {
            is_allright = false;
            ViewUtils.showToast(SupplementRegisterDataActivity.this,"主管姓名为空");

        }else if(StringUtils.isStrNull(string_et_id_card_num))
        {
            //后面可以添加对身份证正确性的判断
            is_allright = false;
            ViewUtils.showToast(SupplementRegisterDataActivity.this,"身份证为空");
        }else if(StringUtils.isStrNull(string_et_shop_name))
        {
            is_allright = false;
            ViewUtils.showToast(SupplementRegisterDataActivity.this,"店铺名称为空");
        }else if(StringUtils.isStrNull(string_et_detail_address))
        {
            is_allright = false;
            ViewUtils.showToast(SupplementRegisterDataActivity.this,"店铺具体地址为空");

        }
       //前面是否再添加定位判断，有待考虑
        if(is_allright)
        {
            fill_data();
            is_register_detail(info.getUSER_ID(), info.getUserName(), info.getIdCard(), info.getManager_Phone(), info.getMchntName(), info.getMchntAddr());
            if(is_register_detail_suc)
            {
                Intent intent = new Intent(SupplementRegisterDataActivity.this,MainManagerActivity.class);
                startActivity(intent);
                finish();
            }else
            {
                ViewUtils.showToast(SupplementRegisterDataActivity.this,"用户注册详细资料失败");
            }

        }

    }

    private void fill_data() {
        info.setManager_Phone(string_et_phone_num);
        info.setManager_NAME(string_et_manager_real_name);
        info.setIdCard(string_et_id_card_num);
        info.setShop_Name(string_et_shop_name);
        info.setMchntAddr(string_et_detail_address);

    }
    //用户名正确 添加唯一性判断
    public void is_register_detail(String USER_ID,String USER_NAME,String ID_CARD,String MOBILE,
                                   String MCHNT_NAME,String MCHNT_ADDR)
    {
        is_register_detail_suc = false;

        RegisterDetailRequest request = new RegisterDetailRequest(USER_ID,USER_NAME,ID_CARD,MOBILE,MCHNT_NAME,MCHNT_ADDR);

        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {

                RegisterDetailResponse result = new RegisterDetailResponse(response);
                if (result.isSuccess()) {
                    //成功表示唯一
                    is_register_detail_suc = true;

                }else
                {
                    is_register_detail_suc = false;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_btn:
                finish();
                break;
            case R.id.register_detail_suc:
                decide_is_allright();
                break;
        }
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

         locationService.start();// 定位SDK
         // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request

    }
    //    /*****
//     * @see copy funtion to you project
//     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
//     *
//     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
               StringBuffer sb = new StringBuffer(256);
                //sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                /*  sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());*/
                if(!StringUtils.isStrNull(location.getProvince()))
                {
                    tv_province.setText(location.getProvince() + ">");
                }
                if(!StringUtils.isStrNull(location.getCity()))
                {
                    tv_city.setText(location.getCity() + ">");
                }
                if(!StringUtils.isStrNull(location.getDistrict()))
                {
                    tv_area.setText(location.getDistrict() + ">");
                }
                /*sb.append("\nprovince : ");
                sb.append(location.getProvince());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else*/
                if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                    ViewUtils.showToast(SupplementRegisterDataActivity.this, sb.toString());
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                    ViewUtils.showToast(SupplementRegisterDataActivity.this, sb.toString());
                }

               // logMsg(sb.toString());
            }
        }

    };

}
