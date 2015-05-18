package cn.com.zcty.ILovegolf.model;
/**
 * ���б�
 * @author deii
 *
 */
public class QiuChangList {
	/**
	 * ��uuid
	 */
	private String uuid;
	private String holes_count;
	/**
	 * �����
	 */
	private String name;
	/**
	 * �򳡵�ַ
	 */
	private String address;
	/**
	 * ���򳡵�������λ�õľ���
	 */
	private String distance;
	
	public  QiuChangList(){}

	public QiuChangList(String uuid, String name, String address,
			String distance) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.address = address;
		this.distance = distance;
	}

	public QiuChangList(String name, String address, String distance) {
		super();
		this.name = name;
		this.address = address;
		this.distance = distance;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public boolean contains(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getHoles_count() {
		return holes_count;
	}

	public void setHoles_count(String holes_count) {
		this.holes_count = holes_count;
	}
	
	
     
}
