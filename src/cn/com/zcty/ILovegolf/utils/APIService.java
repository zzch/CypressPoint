package cn.com.zcty.ILovegolf.utils;

public class APIService {
	/**
	 * 测试服务器地址
	 */
	  public static String hostName = "http://123.57.210.52/api";
	/**
	 * 正式服务器地址
	 */
	  //public static String hostName = "http://ilovegolfclub.com/api";
	//一键注册
	  public static final String ONE_REGISTER=hostName+"/v1/users/sign_up_simple";

	  //球场信息，   根据球场信息的uuid来获取该球场的具体信息，打球设置类
	  public static final String COURSE_INFO=hostName+"/v1/venues/show?";
	  
	  //历史赛事列表
	  public static final String MATCHES_LIST=hostName+"/v1/matches/history.json?";
	  
	  //历史赛事计分卡信息   
	  public static final String SCORECARD_SHOW=hostName+"/v1/matches/practice/show?";
	  //按省份划分的球场列表
	  public static final String SEARCH_COURSE=hostName+"/v1/venues/sectionalized_by_province?"; 
	  //附近的球场列表
	  public static final String NEAREST_COURSE=hostName+"/v1/venues/nearby?";
	  //最近的球场信息
	  public static final String NEAREST_NEAREST=hostName+"/v1/venues/nearest?";
	  //删除历史赛事信息
	  public static final String DELETE_MATCHES=hostName+"/v1/matches/practice?";
	  //创建比赛
	  public static final String CREATE_PRACTICE_EVENTS=hostName+"/v1/matches/practice.json?";
	  //修改简单积分卡
	  public static final String MODIFYINTEGRAL = hostName+"/v1/scorecards/simple.json?";
	  
	  //简单积分统计
	  public static final String DATASTATISTICS = hostName+"/v1/statistics/simple.json?";
	  //练习赛事信息
	  public static final String LIANXISAISHI = hostName+"/v1/matches/practice/show.json?";
	  
	  //删除练习赛事
	  public static final String DELET = hostName+"/v1/matches/practice.json?";
	  //用户注册
	  public static final String USERREGISTER = hostName+"/v1/users/sign_up.json?";
      //发送验证码
	 public static final String  YANZHENGMA = hostName+"/v1/verification_code/sign_up.json?";
	 //重置密码
	 public static final String RESTPSDYANZHENGMA = hostName+"/v1/verification_code/reset_password.json?";
	 
	  //击球记录
	  public static final String JILU = hostName+"/v1/strokes.json?";
	  
	  //更新用户头像
	  public static final String HEAD = hostName+"/v1/users/update_portrait.json?";

	  //用户头像
	  public static final String TOUXIANG = hostName+"/v1/users/portrait.json?";

	  //更新出生日期
	  public static final String BRITHDAY = hostName+"/v1/users/update_birthday.json?";
	  
	  //更新签名
	  public static final String SIGNATURE = hostName+"/v1/users/update_description.json?";
	  
	  //更新性别
	  public static final String SEX = hostName+"/v1/users/update_gender.json?";
	  
	  //更新昵称
	  public static final String UPNAME = hostName+"/v1/users/update_nickname.json?";
	  
	  //用户资料
	  public static final String INFORMATION = hostName+"/v1/users/details.json?";
	  
	  //用户头像
	  public static final String TITLE = hostName+"/v1/users/portrait.json?";

      //用户登录
	  public static final String USERLOGIN= hostName+"/v1/users/sign_in.json?";
	  
	  //专业积分统计
	  public static final String MAJORCOUNT = hostName+"/v1/statistics/professional.json?";
	 
	  //统计
	  public static final String COUNTS = hostName+"/v1/statistics.json?";

	  //已访问球场列表
	  public static final String VISTTED = hostName+"/v1/venues/visited.json?";
	 
	  //个性统计
	  public static final String GEXINGCOUNT = hostName+"/v1/statistics/customize.json?";

	  //创建竞技赛
	  public static final String COMPETITION = hostName+"/v1/matches/tournament.json?";

	  //竞技赛列表
	  public static final String COMPETITIONCHOOSEPITCH = hostName+"/v1/venues/matches/tournament.json?";
	 
	 //竞技赛事信息
	 public static final String COMPETITIONINFORMATION = hostName+"/v1/matches/show.json?";
	 
	 //比赛信息摘要
	 public static final String MATCHINFATION = hostName+"/v1/matches/summary.json?";
	 
	 //加入比赛
	 public static final String COMPETITIONCREAT = hostName+"/v1/matches/participate.json?";
	 
	 //历史竞技赛列表
	 public static final String COMPETITIONSCORD = hostName+"/v1/matches/tournament.json?";
	 
	 //实验
	 public static final String SHIYAN = hostName+"/v1/scorecards/professional?";
	 //用户注销
	 public static final String SIGNOUT=hostName+"/v1/users/sign_out.json?";

	 //球会信息
	 public static final String DIAMONDINFORMATION = hostName+"/v1/venues/show.json?";

	 //创建比赛
	 public static final String CREADMATCHES = hostName+"/v1/matches.json?";

	 //比赛信息
	 public static final String MATCHINFORMATION = hostName+"/v1/matches/show.json?";

	 //排行榜
	 public static final String RANKING = hostName+"/v1/leaderboards.json?";

	 //更新头像。昵称。性别
	 public static final String GENGGXIN = hostName+"/v1/users/update_portrait_and_nickname_and_gender.json?";

	 //获得比赛口令
	 public static final String PASSWORD = hostName+"/v1/matches/password.json?";

	 //验证比赛口令
	 public static final String CHECKINGPASSWORD = hostName+"/v1/matches/verify.json?";
	 //参赛者信息
	 public static final String RANKINGINFORMATION = hostName+"/v1/players/show.json?";

	 //获取用户升级验证码
	 public static final String VERIFYPHONE = hostName+"/v1/verification_code/upgrade.json?";
	 //用户升级
	 public static final String UPGRADE = hostName+"/v1/users/upgrade.json?";
	 //更换密码
	 public static final String UPPASSWORD = hostName+"/v1/users/update_password.json?";

	 //意见反馈
	 public static final String FEEDBACK = hostName+"/v1/feedbacks.json?";
	 
	 //忘记密码
	 public static final String RESTPASSWORD = hostName+"/v1/users/reset_password.json?";
}
