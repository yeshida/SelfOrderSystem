package com.centerm.selforder.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.activity.RegisterPageOneActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by caiqingyuan on 16/3/8.
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {

    private static ImageView img_cheeck_email;
    private static final String TAG = "EmailAutoCompleteTextView";

    private String[] emailSufixs = new String[]{"@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com",
            "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com", "@vip.sina.com"};



    public static String email_address;
    public EmailAutoCompleteTextView(Context context) {
        super(context);

        init(context);
    }

     public static void set_imageview(ImageView img)
     {
         img_cheeck_email = img;
     }
    public static String getEmail_address() {
        return email_address;
    }

    public static void setEmail_address(String email_address) {
        EmailAutoCompleteTextView.email_address = email_address;
    }
    public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public EmailAutoCompleteTextView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void setAdapterString(String[] es) {
        if (es != null && es.length > 0)
            this.emailSufixs = es;
    }


    private void init(final Context context) {
        //adapter中使用默认的emailSufixs中的数据，可以通过setAdapterString来更改
        this.setAdapter(new EmailAutoCompleteAdapter(context, R.layout.register_auto_complete_item, emailSufixs));
        //使得在输入1个字符之后便开启自动完成
        this.setThreshold(1);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = EmailAutoCompleteTextView.this.getText().toString();
                //当该文本域重新获得焦点后，重启自动完成
                if (!"".equals(text))
                    performFiltering(text, 0);
                if (text != null && isEmail(text)) {
                    RegisterPageOneActivity.is_data_allright[1] = true;
                    RegisterPageOneActivity.string_eamil_address = s.toString();
                    img_cheeck_email.setImageResource(R.drawable.cqy_face_happy);
                    setEmail_address(text);
                } else {
                    //Toast toast = Toast.makeText(context, "邮件地址格式不正确", Toast.LENGTH_SHORT);
                    // toast.show();
                    RegisterPageOneActivity.is_data_allright[1] = false;
                    img_cheeck_email.setImageResource(R.drawable.cqy_face_happy);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      /*  this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = EmailAutoCompleteTextView.this.getText().toString();
                    //当该文本域重新获得焦点后，重启自动完成
                    if (!"".equals(text))
                        performFiltering(text, 0);
                } else {
                    //当文本域丢失焦点后，检查输入email地址的格式
                    EmailAutoCompleteTextView ev = (EmailAutoCompleteTextView) v;
                    String text = ev.getText().toString();
                    //这里正则写的有点粗暴:)
                    if (text != null && isEmail(text)) {
                        RegisterPage.is_data_allright[1] = true;
                        img_cheeck_email.setImageResource(R.drawable.face_happy);
                    } else {
                        //Toast toast = Toast.makeText(context, "邮件地址格式不正确", Toast.LENGTH_SHORT);
                        // toast.show();
                        RegisterPage.is_data_allright[1] = false;
                        img_cheeck_email.setImageResource(R.drawable.face_sad);

                    }
                }
            }
        });*/
    }

    /**
     * 确认字符串是否为email格式
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    @Override
    protected void replaceText(CharSequence text) {
        //当我们在下拉框中选择一项时，android会默认使用AutoCompleteTextView中Adapter里的文本来填充文本域
        //因为这里Adapter中只是存了常用email的后缀
        //因此要重新replace逻辑，将用户输入的部分与后缀合并
        //Log.i(TAG + " replaceText", text.toString());
        String t = this.getText().toString();
        int index = t.indexOf("@");
        if (index != -1)
            t = t.substring(0, index);
        super.replaceText(t + text);
    }


    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        //该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配
        //adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现
        // Log.i(TAG + " performFiltering", text.toString() + "   " + keyCode);
        String t = text.toString();
        //因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"
        //的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串
        int index = t.indexOf("@");
        if (index == -1) {
            if (t.matches("^[a-zA-Z0-9_]+$")) {
                super.performFiltering("@", keyCode);
            } else
                this.dismissDropDown();//当用户中途输入非法字符时，关闭下拉提示框
        } else {
            super.performFiltering(t.substring(index), keyCode);
        }
    }


    private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {
        public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {
            super(context, textViewResourceId, email_s);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Log.i(TAG, "in GetView");
            View v = convertView;
            if (v == null)
                v = LayoutInflater.from(getContext()).inflate(
                        R.layout.register_auto_complete_item, null);
            TextView tv = (TextView) v.findViewById(R.id.tv);
            String t = EmailAutoCompleteTextView.this.getText().toString();
            int index = t.indexOf("@");
            if (index != -1)
                t = t.substring(0, index);
            //将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
            tv.setText(t + getItem(position));
            //Log.i(TAG, tv.getText().toString());
            return v;
        }

    }


}
