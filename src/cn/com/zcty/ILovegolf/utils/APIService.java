package cn.com.zcty.ILovegolf.utils;

public class APIService {
	//一键注册
	  public static final String ONE_REGISTER="http://123.57.210.52/api/v1/users/sign_up_simple";

	  //球场信息，   根据球场信息的uuid来获取该球场的具体信息，打球设置类
	  public static final String COURSE_INFO="http://123.57.210.52/api/v1/venues/show?";
	  
	  //历史赛事列表
	  public static final String MATCHES_LIST="http://123.57.210.52/api/v1/matches/history.json?";
	  
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
	  
	  //简单积分统计
	  public static final String DATASTATISTICS = "http://123.57.210.52/api/v1/statistics/simple.json?";
	  //练习赛事信息
	  public static final String LIANXISAISHI = "http://123.57.210.52/api/v1/matches/practice/show.json?";
	  
	  //删除练习赛事
	  public static final String DELET = "http://123.57.210.52/api/v1/matches/practice.json?";
	  //用户注册
	  public static final String USERREGISTER = "http://123.57.210.52/api/v1/users/sign_up.json?";
      //发送验证码
	 public static final String  YANZHENGMA = "http://123.57.210.52/api/v1/verification_code/sign_up.json?";

	 
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
	  public static final String MAJORCOUNT = "http://123.57.210.52/api/v1/statistics/professional.json?";
	 
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
	 public static final String COMPETITIONINFORMATION = "http://123.57.210.52/api/v1/matches/show.json?";
	 
	 //比赛信息摘要
	 public static final String MATCHINFATION = "http://123.57.210.52/api/v1/matches/summary.json?";
	 
	 //加入比赛
	 public static final String COMPETITIONCREAT = "http://123.57.210.52/api/v1/matches/participate.json?";
	 
	 //历史竞技赛列表
	 public static final String COMPETITIONSCORD = "http://123.57.210.52/api/v1/matches/tournament.json?";
	 
	 //实验
	 public static final String SHIYAN = "http://123.57.210.52/api/v1/scorecards/professional?";
	 //用户注销
	 public static final String SIGNOUT="http://123.57.210.52/api/v1/users/sign_out.json?";

	 //球会信息
	 public static final String DIAMONDINFORMATION = "http://123.57.210.52/api/v1/venues/show.json?";

	 //创建比赛
	 public static final String CREADMATCHES = "http://123.57.210.52/api/v1/matches.json?";

	 //比赛信息
	 public static final String MATCHINFORMATION = "http://123.57.210.52/api/v1/matches/show.json?";

	 //排行榜
	 public static final String RANKING = "http://123.57.210.52/api/v1/leaderboards.json?";

	 //更新头像。昵称。性别
	 public static final String GENGGXIN = "http://123.57.210.52/api/v1/users/update_portrait_and_nickname_and_gender.json?";

	 //获得比赛口令
	 public static final String PASSWORD = "http://123.57.210.52/api/v1/matches/password.json?";

	 //验证比赛口令
	 public static final String CHECKINGPASSWORD = "http://123.57.210.52/api/v1/matches/verify.json?";
	 //参赛者信息
	 public static final String RANKINGINFORMATION = "http://123.57.210.52/api/v1/players/show.json?";
}
