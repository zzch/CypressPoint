package cn.com.zcty.ILovegolf.model;

public class Scorecards {
	/**
	 * ÿһ���򶴵�uuid
	 */
	private String uuid;
	/**
	 * ���б�1~18
	 */
	private String number;
	/**
	 * ��׼��
	 */
	private String par;
	/**
	 * ����T̨
	 */
	private String tee_box_color;
	/**
	 * ����T̨���򶴵ľ���
	 */
	private String distance_from_hole_to_tee_box;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}

	public String getTee_box_color() {
		return tee_box_color;
	}

	public void setTee_box_color(String tee_box_color) {
		this.tee_box_color = tee_box_color;
	}

	public String getDistance_from_hole_to_tee_box() {
		return distance_from_hole_to_tee_box;
	}

	public void setDistance_from_hole_to_tee_box(
			String distance_from_hole_to_tee_box) {
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

	@Override
	public String toString() {
		return "Scorecards [uuid=" + uuid + ", number=" + number + ", par="
				+ par + ", tee_box_color=" + tee_box_color
				+ ", distance_from_hole_to_tee_box="
				+ distance_from_hole_to_tee_box + ", score=" + score
				+ ", putts=" + putts + ", penalties=" + penalties
				+ ", driving_distance=" + driving_distance + ", direction="
				+ direction + "]";
	}



	
}
