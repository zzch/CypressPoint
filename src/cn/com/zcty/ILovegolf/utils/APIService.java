package cn.com.zcty.ILovegolf.utils;

public class APIService {
	//һ��ע��
	  public static final String ONE_REGISTER="http://augusta.aforeti.me/api/v1/users/sign_up_simple";

	  //����Ϣ     ��������Ϣ��uuid����ȡ���򳡵ľ�����Ϣ   ����������
	  public static final String COURSE_INFO="http://augusta.aforeti.me/api/v1/courses/show?";
	  
	  //��ʷ�����б�
	  public static final String MATCHES_LIST="http://augusta.aforeti.me/api/v1/matches?";
	  
	  //��ʷ���¼Ƿֿ���Ϣ   
	  public static final String SCORECARD_SHOW="http://augusta.aforeti.me/api/v1/matches/show?";
	  
	  //��ʡ�ݻ��ֵ����б�
	  public static final String SEARCH_COURSE="http://augusta.aforeti.me/api/v1/courses/sectionalized_by_province?";
	  
	  //������������б�
	  public static final String NEAREST_COURSE="http://augusta.aforeti.me/api/v1/courses/nearest?";
      //ɾ����ʷ������Ϣ
	  public static final String DELETE_MATCHES="http://augusta.aforeti.me/api/v1/matches?";
}
