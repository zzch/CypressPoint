package cn.com.zcty.ILovegolf.model;

public class MajorScoreJiQiu {
	private String distance_from_hole;
	private String point_of_fall;
	private String penalties;
	private String club;
	public String getDistance_from_hole() {
		return distance_from_hole;
	}
	public void setDistance_from_hole(String distance_from_hole) {
		this.distance_from_hole = distance_from_hole;
	}
	public String getPoint_of_fall() {
		return point_of_fall;
	}
	public void setPoint_of_fall(String point_of_fall) {
		this.point_of_fall = point_of_fall;
	}
	public String getPenalties() {
		return penalties;
	}
	public void setPenalties(String penalties) {
		this.penalties = penalties;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	@Override
	public String toString() {
		return "distance_from_hole=" + distance_from_hole
				+ ", point_of_fall=" + point_of_fall + ", penalties="
				+ penalties + ", club=" + club + "";
	}
	
}
