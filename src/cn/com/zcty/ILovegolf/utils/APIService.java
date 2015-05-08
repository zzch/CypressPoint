package cn.com.zcty.ILovegolf.utils;

public class APIService {
	//一键注册
	  public static final String ONE_REGISTER="http://123.57.210.52/api/v1/users/sign_up_simple";

	  //球场信息，   根据球场信息的uuid来获取该球场的具体信息，打球设置类
	  public static final String COURSE_INFO="http://123.57.210.52/api/v1/venues/show?";
	  
	  //历史赛事列表
	  public static final String MATCHES_LIST="http://123.57.210.52/api/v1/matches/practice?";
	  
	  //历史赛事计分卡信息   
	  public static final String SCORECARD_SHOW="http://123.57.210.52/api/v1/matches/practice/show?";
	  //按省份划分的球场列表
	  public static final String SEARCH_COURSE="http://123.57.210.52/api/v1/venues/sectionalized_by_province?"; 
	  //附近的球场列表
	  public static final String NEAREST_COURSE="http://123.57.210.52/api/v1/venues/nearby?";
	  //最近的球场信息
	  public static final String NEAREST_NEAREST="http://123.57.210.52/api/v1/venues/nearest?";
	  //删除历史赛事信息
	  public static final String DELETE_MATCHES="http://123.57.210.52/api/v1/matches/practice?";
	  //创建比赛
	  public static final String CREATE_PRACTICE_EVENTS="http://123.57.210.52/api/v1/matches/practice.json?";
	  //修改简单积分卡
	  public static final String MODIFYINTEGRAL = "http://123.57.210.52/api/v1/scorecards/simple.json?";
	  
	  //数据统计
	  public static final String DATASTATISTICS = "http://123.57.210.52/api/v1/matches/practice/statistics/simple?";
	  //练习赛事信息
	  public static final String LIANXISAISHI = "http://123.57.210.52/api/v1/matches/practice/show.json?";
	  
	  //删除练习赛事
	  public static final String DELET = "http://123.57.210.52/api/v1/matches/practice.json?";
	  //用户注册
	  public static final String USERREGISTER = "http://123.57.210.52/api/v1/users/sign_up.json?";
      //发送验证码
	 public static final String  YANZHENGMA = "http://123.57.210.52/api/v1/verification_code/send.json?";

	 
	  //击球记录
	  public static final String JILU = "http://123.57.210.52/api/v1/strokes.json?";
	  
	  //更新用户头像
	  public static final String HEAD = "http://123.57.210.52/api/v1/users/update_portrait.json?";

	  //用户头像
	  public static final String TOUXIANG = "http://123.57.210.52/api/v1/users/portrait.json?";

	  //更新出生日期
	  public static final String BRITHDAY = "http://123.57.210.52/api/v1/users/update_birthday.json?";
	  
	  //更新签名
	  public static final String SIGNATURE = "http://123.57.210.52/api/v1/users/update_description.json?";
	  
	  //更新性别
	  public static final String SEX = "http://123.57.210.52/api/v1/users/update_gender.json?";
	  
	  //更新昵称
	  public static final String UPNAME = "http://123.57.210.52/api/v1/users/update_nickname.json?";
	  
	  //用户资料
	  public static final String INFORMATION = "http://123.57.210.52/api/v1/users/details.json?";
	  
	  //用户头像
	  public static final String TITLE = "http://123.57.210.52/api/v1/users/portrait.json?";

      //用户登录
	  public static final String USERLOGIN= "http://123.57.210.52/api/v1/users/sign_in.json?";
	  
	  //专业积分统计
	  public static final String MAJORCOUNT = "http://123.57.210.52/api/v1/matches/practice/statistics/professional.json?";
	 
	  //统计
	  public static final String COUNTS = "http://123.57.210.52/api/v1/statistics.json?";

	  //已访问球场列表
	  public static final String VISTTED = "http://123.57.210.52/api/v1/venues/visited.json?";
	 
	  //个性统计
	  public static final String GEXINGCOUNT = "http://123.57.210.52/api/v1/statistics/customize.json?";

	  //创建竞技赛
	  public static final String COMPETITION = "http://123.57.210.52/api/v1/matches/tournament.json?";

	  //竞技赛列表
	  public static final String COMPETITIONCHOOSEPITCH = "http://123.57.210.52/api/v1/venues/matches/tournament.json?";
	 
	 //竞技赛事信息
	 public static final String COMPETITIONINFORMATION = "http://123.57.210.52/api/v1/matches/tournament/show.json?";

	 //加入竞技赛
	 public static final String COMPETITIONCREAT = "http://123.57.210.52/api/v1/matches/tournament/participate.json?";

	 //历史竞技赛列表
	 public static final String COMPETITIONSCORD = "http://123.57.210.52/api/v1/matches/tournament.json?";
	 
	 //实验
	 public static final String SHIYAN = "http://123.57.210.52/api/v1/scorecards/professional?";
	 //用户注销
	 public static final String SIGNOUT="http://123.57.210.52/api/v1/users/sign_out.json?";

	 //球会信息
	 public static final String DIAMONDINFORMATION = "http://123.57.210.52/api/v1/venues/show.json?";
}
