package cn.com.zcty.ILovegolf.activity.exercise.city;

import java.util.Comparator;

import cn.com.zcty.ILovegolf.model.SortModel;


/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getName().equals("@")
				|| o2.getName().equals("#")) {
			return -1;
		} else if (o1.getName().equals("#")
				|| o2.getName().equals("@")) {
			return 1;
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}

}
