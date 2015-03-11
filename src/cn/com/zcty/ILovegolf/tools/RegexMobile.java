package cn.com.zcty.ILovegolf.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMobile {
	
	/**
	 * 验证手机号是否合f法
	 * @param mobileNumber 要验证的手机号
	 * @return   true代表验证成功,false代表验证失败
	 */
	public static boolean VildateMobile(String mobileNumber)
	{
		String regex="^1[3458]\\d{9}$";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher= pattern.matcher(mobileNumber);
		return matcher.matches();
		
	}
	/**
	 * 验证省份证号是否合法
	 * @param personal_card �?��验证的省份正号码
	 * @return  true代表验证成功,false代表验证失败
	 */
	public static boolean VildatePersonCard(String personal_card){
		String regex="^d{18}$";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(personal_card);
		return matcher.matches();
	}
	
	public static boolean VildateBankNum(String bank_num){
		String regex="^d{19}$";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(bank_num);
		return matcher.matches();
	}
	
	public static void main(String[] args)
	{
		System.out.println(VildateMobile("13141403201"));
		System.out.println(VildatePersonCard("131414032013333332"));
	}

}
