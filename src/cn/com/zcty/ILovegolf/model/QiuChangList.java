package cn.com.zcty.ILovegolf.model;
/**
 * 球场列表
 * @author deii
 *
 */
public class QiuChangList {
	/**
	 * 球场uuid
	 */
	private String uuid;
	/**
	 * 球场名称
	 */
	private String name;
	/**
	 * 球场地址
	 */
	private String address;
	/**
	 * 该球场到我所在位置的距离
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
	
	
     
}
