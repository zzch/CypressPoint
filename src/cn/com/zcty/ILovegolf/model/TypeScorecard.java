package cn.com.zcty.ILovegolf.model;

import java.util.List;

public class TypeScorecard {
	/**
	 * ��������
	 */
	private String type;
	/**
	 * �ɼ�
	 */
	private List<Scorecards> scorecards;
	
	public TypeScorecard(){}

	public TypeScorecard(String type, List<Scorecards> scorecards) {
		super();
		this.type = type;
		this.scorecards = scorecards;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Scorecards> getScorecards() {
		return scorecards;
	}

	public void setScorecards(List<Scorecards> scorecards) {
		this.scorecards = scorecards;
	}

    
}
