package com.centerm.selforder.utils;

import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 * 
 * @author wanliang527
 * @date 2014-06-04
 * 
 */
public class StringUtils {

	public StringUtils() {

	}

	/**
	 * 判断一个字符串是否为空字符串
	 *
	 * @param str 字符串内容
	 * @return
	 */
	public static boolean isStrNull(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 判断一个字符串是否含有空字符。字符串为Null的时候返回false
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainSpace(String str) {
		if (isStrNull(str))
			return true;
		Pattern pattern = Pattern.compile("[\\s]+");
		Matcher matcher = pattern.matcher(str);
		boolean flag = false;
		while (matcher.find()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断密码是否合法。
	 *
	 * @param passwdStr
	 * @return
	 */
	public static boolean isPasswdValid(String passwdStr) {
		if (!checkPasswdStr(passwdStr).equals("OK"))
			return false;
		return true;
	}

	/**
	 * 检测密码输入是否合法。如果合法，返回"OK",否则返回不合法原因.
	 * 判断规则是密码不为空，并且是大于等于6位小于20位的字符组合，不能包含空格和中文。
	 *
	 * @param passwdStr
	 * @return
	 */
	public static String checkPasswdStr(String passwdStr) {
		if (isStrNull(passwdStr)) {
			return "密码不能为空";
		}
		if (isContainSpace(passwdStr)) {
			return "密码中不能包含空字符";
		}
		if (isContainChinese(passwdStr)) {
			return "密码中不能含有中文字符";
		}
		if (!isLengthValid(6, 20, passwdStr)) {
			return "密码必须大于6位小于20位";
		}

		return "OK";
	}
	/**
	 * 判断密码是否为纯数字
	 *
	 * @param passwdStr
	 * @return
	 */
	public static boolean checkPasswdStrIsNum(String passwdStr) {

		boolean is_num = true;
		for(int i = 0;i < passwdStr.length(); i++)
		{
			if(!(passwdStr.charAt(i) >= '0' && passwdStr.charAt(i) <= '9'))
			{
				is_num = false;
				return is_num;
			}
		}
		return is_num;
	}

	/**
	 * 判断一个字符串是否含有中文。
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		if (str.getBytes().length == str.length()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断一个Email地址是否合法。合法返回true，否则返回false。
	 *
	 * @param email 邮件地址
	 * @return
	 */
	public static boolean isEmailValid(String email) {
		String pattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 检测一个字符串长度是否合法。如果字符串为空，返回false。
	 *
	 * @param min 最小长度，不限制的话，传入-1
	 * @param max 最大长度，不限制的话，传入-1
	 * @param str
	 * @return
	 */
	public static boolean isLengthValid(int min, int max, String str) {
		if (min < -1 || max < -1 || isStrNull(str))
			return false;
		int length = str.length();
		if (min == -1 && max == -1)
			return true;
		if (min == -1 && max > 0) {
			if (length <= max)
				return true;
		}
		if (max == -1) {
			if (length >= min)
				return true;
		}
		if (length >= min && length <= max)
			return true;
		return false;
	}

	/**
	 * 判断手机号是否合法。判断规则是，开头是13、14、15或18,一共11位的纯数字组合
	 *
	 * @param num
	 * @return
	 */
	public static boolean isMobileNumValid(String num) {
		String pat = "^(13|14|15|18)\\d{9}$";
		if (isStrNull(num))
			return false;
		if (num.matches(pat))
			return true;
		return false;

	}

	/**
	 * 将一个汉字转换成拼音，例如 “林” 转换之后为 “lin”
	 *
	 * @param input
	 * @return 返回字符串数组，用于处理多音字的情况。如果输入的字符不是汉字，则返回空；如果输入的汉字是多音字，则返回的数组大小将大于1；
	 */
	public static String[] char2PinyinArray(char input) {
		String[] strs = null;
		// HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		// format.setVCharType(HanyuPinyinVCharType.WITH_V);
		// format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// try {
		// strs = PinyinHelper.toHanyuPinyinStringArray(input, format);
		// } catch (BadHanyuPinyinOutputFormatCombination e) {
		// e.printStackTrace();
		// }
		return strs;
	}

	/**
	 * 将一个汉字转换成拼音。
	 *
	 * @param input
	 * @return 返回字符串。如果输入的汉字是多音字，那就返回所有读音拼接而成的字符串，中间以“_”相隔.
	 */
	public static String char2Pinyin(char input) {
		String[] strs = char2PinyinArray(input);
		String str = null;
		if (strs != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < strs.length; i++) {
				sb.append(strs[i]);
				if (i != strs.length - 1)
					sb.append("_");
			}
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 将一个字符串转换为拼音的形式，遇到非汉字的字符，将原封不动，不会进行转换。
	 *
	 * @param input
	 * @return
	 */
	public static String string2Pinyin(String input) {
		if (input == null)
			return null;
		int len = input.length();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String[] strs = char2PinyinArray(input.charAt(i));
			if (strs == null) {
				sBuilder.append(input.charAt(i));
			} else {
				sBuilder.append(strs[0]);
			}
		}
		return sBuilder.toString();
	}

//	public static boolean isMatch(String text) {
//		String pattern = "\\d{4}[]\\d{1,2}-\\d{1,2}";
//		return text.matches(pattern);
//	}

	/**
	 * 判断密码强度，根据输入字符种类的强度来判断。
	 *
	 * @param pwd
	 * @return 1 弱 2中 3强
	 */
	public static int CheckSecurity(String pwd) {

		int[] strength = new int[3];
		int count = 0;
		for (int i = 0; i < pwd.length(); i++) {
			char c = pwd.charAt(i);
			if (c >= '0' && c <= '9') {
				//如果包涵数字
				strength[0]++;
			} else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
				//如果包涵字母
				strength[1]++;
			} else {
				//如果包涵特殊字符
				strength[2]++;
			}
		}
       for(int i = 0; i < 3; i++)
	   {
		   if(strength[i] > 0)
			   count++;
	   }
		return count;

	}
}
