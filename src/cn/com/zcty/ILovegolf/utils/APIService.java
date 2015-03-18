package cn.com.zcty.ILovegolf.utils;

public class APIService {
	//一键注册
	  public static final String ONE_REGISTER="http://123.57.210.52/api/v1/users/sign_up_simple";

	  //球场信息，   根据球场信息的uuid来获取该球场的具体信息，打球设置类
	  public static final String COURSE_INFO="http://123.57.210.52/api/v1/venues/show?";
	  
	  //历史赛事列表
	  public static final String MATCHES_LIST="http://123.57.210.52/api/v1/matches?";
	  
	  //历史赛事计分卡信息   
	  public static final String SCORECARD_SHOW="http://123.57.210.52/api/v1/matches/show?";
	  
	  //按省份划分的球场列表
	  public static final String SEARCH_COURSE="http://123.57.210.52/api/v1/venues/sectionalized_by_province?";
	  
	  //距离最近的球场列表
	  public static final String NEAREST_COURSE="http://123.57.210.52/api/v1/venues/nearest?";
      //删除历史赛事信息
	  public static final String DELETE_MATCHES="http://123.57.210.52/api/v1/matches?";
	  //创建比赛
	  public static final String CREATE_PRACTICE_EVENTS="http://123.57.210.52/api/v1/matches/practice.json?";
	  //修改积分卡
	  public static final String MODIFYINTEGRAL = "http://123.57.210.52/api/v1/scorecards.json?";
	  //数据统计
	  public static final String DATASTATISTICS = "http://123.57.210.52/api/v1/statistics/show.json?";
}
