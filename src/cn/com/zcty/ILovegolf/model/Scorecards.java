package cn.com.zcty.ILovegolf.model;

public class Scorecards {
	/**
	 * ÿһ���򶴵�uuid
	 */
	private String uuid;
	/**
	 * ���б�1~18
	 */
	private int number;
	/**
	 * ��׼��
	 */
	private int par;
	/**
	 * ����T̨
	 */
	private String tee_box_color;
	/**
	 * ����T̨���򶴵ľ���
	 */
	private int distance_from_hole_to_tee_box;
	/**
	 * �ɼ�
	 */
	private String score;
	/**
	 * �Ƹ�
	 */
	private String putts;
	/**
	 * ����
	 */
	private String penalties;
	/**
	 * �������
	 */
    private String driving_distance;
    /**
     * ������з���
     */
    private String direction;
    
    public Scorecards(){}

	public Scorecards(String uuid, int number, int par, String tee_box_color,
			int distance_from_hole_to_tee_box, String score, String putts,
			String penalties, String driving_distance, String direction) {
		super();
		this.uuid = uuid;
		this.number = number;
		this.par = par;
		this.tee_box_color = tee_box_color;
		this.distance_from_hole_to_tee_box = distance_from_hole_to_tee_box;
		this.score = score;
		this.putts = putts;
		this.penalties = penalties;
		this.driving_distance = driving_distance;
		this.direction = direction;
	}

	public Scorecards(int number, int par, String tee_box_color,
			int distance_from_hole_to_tee_box, String score, String putts,
			String penalties, String driving_distance, String direction) {
		super();
		this.number = number;
		this.par = par;
		this.tee_box_color = tee_box_color;
		this.distance_from_hole_to_tee_box = distance_from_hole_to_tee_box;
		this.score = score;
		this.putts = putts;
		this.penalties = penalties;
		this.driving_distance = driving_distance;
		this.direction = direction;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPar() {
		return par;
	}

	public void setPar(int par) {
		this.par = par;
	}

	public String getTee_box_color() {
		return tee_box_color;
	}

	public void setTee_box_color(String tee_box_color) {
		this.tee_box_color = tee_box_color;
	}

	public int getDistance_from_hole_to_tee_box() {
		return distance_from_hole_to_tee_box;
	}

	public void setDistance_from_hole_to_tee_box(int distance_from_hole_to_tee_box) {
		this.distance_from_hole_to_tee_box = distance_from_hole_to_tee_box;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPutts() {
		return putts;
	}

	public void setPutts(String putts) {
		this.putts = putts;
	}

	public String getPenalties() {
		return penalties;
	}

	public void setPenalties(String penalties) {
		this.penalties = penalties;
	}

	public String getDriving_distance() {
		return driving_distance;
	}

	public void setDriving_distance(String driving_distance) {
		this.driving_distance = driving_distance;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	
}
