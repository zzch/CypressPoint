package cn.com.zcty.ILovegolf.utils;

public class APIService {
	//一键注册
	  public static final String ONE_REGISTER="http://augusta.aforeti.me/api/v1/users/sign_up_simple";

	  //球场信息     根据球场信息的uuid来获取该球场的具体信息   打球设置类
	  public static final String COURSE_INFO="http://augusta.aforeti.me/api/v1/courses/show?";
	  
	  //历史赛事列表
	  public static final String MATCHES_LIST="http://augusta.aforeti.me/api/v1/matches?";
	  
	  //历史赛事记分卡信息   
	  public static final String SCORECARD_SHOW="http://augusta.aforeti.me/api/v1/matches/show?";
	  
	  //按省份划分的球场列表
	  public static final String SEARCH_COURSE="http://augusta.aforeti.me/api/v1/courses/sectionalized_by_province?";
	  
	  //距离最近的球场列表
	  public static final String NEAREST_COURSE="http://augusta.aforeti.me/api/v1/courses/nearest?";
      //删除历史赛事信息
	  public static final String DELETE_MATCHES="http://augusta.aforeti.me/api/v1/matches?";
}
