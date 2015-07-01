package testMQAnnotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MaQiao.MaQiaoJsonAnnotation.MQjson;

@SuppressWarnings("unused")
public final class user {
	@MQjson
	String name = "sunjian";

	@MQjson(Groups = { 1, 2, 4 })
	private final int year = 20;

	int Mathematics = 80;

	@MQjson
	transient int month = 11;

	@MQjson
	char[] charsplit = { 'X', 'Y', 'Z' };

	@MQjson
	static char[] charsplitk = { 'X', 'Y', 'Z' };

	int language = 75;

	@MQjson(Title = "Workday", Groups = { 1, 2, 6 })
	int workday = 30;

	@MQjson(Title = "ListString", Groups = { 1, 2 })
	public List<String> c = new ArrayList<String>();

	private final Map<String, String> cd = new HashMap<String, String>();

	private int cc = 100;

	@MQjson(Title = "BigName", Groups = { 1, 3, 5 })
	private char[] chark = { 'a', 'c', 'e' };

	public String getBigName() {
		return "Mr " + name;
	}

	public String getMil(String c, int k) {
		return "AA";
	}

	@MQjson(Title = "ScoreSort", Groups = { 1, 3, 7 })
	public final int getScoreSort() {
		return Mathematics + language;
	}

	@MQjson(Title = "ScoreAverage", Groups = { 2, 4, 7 })
	public int getScoreAverage() {
		return (Mathematics + language) / 2;
	}

	@MQjson(Title = "getNameMethod", Groups = { 2, 3, 7 })
	public final String getName() {
		return name;
	}

	@MQjson(Title = "getcharnew", Groups = { 2, 6, 7 })
	public final char[] getchar() {
		char[] c = { 'a', 'c' };
		return c;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final int getYear() {
		return year;
	}

	@MQjson(Title = "setYearMethod", Groups = { 5, 6, 7 })
	public final void setYear(final int year, final int cc, final int[] k, final String[] kc) {
		//this.year = year;
	}

	public final int getMathematics() {
		return Mathematics;
	}

	public final void setMathematics(final int mathematics) {
		this.Mathematics = mathematics;
	}

	@MQjson(Title = "getLanguageNew", Groups = { 1, 2 })
	public final static int getLanguage() {
		return 1001;
	}

	public final void setLanguage(final int language) {
		this.language = language;
	}

	@MQjson(Title = "getChark", Groups = { 2, 4, 7 })
	public final char[] getChark() {
		return chark;
	}

}
