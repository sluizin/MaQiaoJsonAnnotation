package testMQAnnotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MaQiao.MaQiaoJsonAnnotation.MQjson;

@SuppressWarnings("unused")
public final class user {
	@MQjson(v = { 9 })
	int[] ccdd=new int[]{1,2,3};
	@MQjson
	String name = "sunjian";

	@MQjson(v = { 1, 2, 4 })
	private final int year = 20;

	int Mathematics = 80;

	@MQjson
	transient int month = 11;
	@MQjson(k="static",v={0,1})
	static int kyk=100;
	@MQjson
	char[] charsplit = { 'X', 'Y', 'Z' };
	@MQjson
	Character[] charsplita={new Character('a'), new Character('b')};

	@MQjson
	static char[] charsplitk = { 'X', 'Y', 'Z' };
	
	int language = 75;

	@MQjson(k = "Workday", v = { 1, 2, 6 })
	int workday = 30;
	

	@MQjson(k = "ListString", v = { 1, 2 })
	public List<String> c = new ArrayList<String>();

	private final Map<String, String> cd = new HashMap<String, String>();

	private int cc = 100;

	@MQjson(k = "BigName", v = { 1, 3, 5 })
	private char[] chark = { 'a', 'c', 'e' };

	public final String getNamea(){
		return "MR "+this.name+"..";
	}
	public String getBigName() {
		return "Mr " + name;
	}

	public String getMil(String c, int k) {
		return "AA";
	}

	@MQjson(k = "ScoreSort", v = {0, 1, 3, 7 })
	public final int getScoreSort() {
		return (Mathematics + language)*1000;
	}

	@MQjson(k = "ScoreAverage", v = { 2, 4, 7 })
	public int getScoreAverage() {
		return (Mathematics + language) / 2;
	}

	@MQjson(k = "getNameMethod", v = {0, 2, 3, 7 })
	public final String getName() {
		return "MR "+this.name;
	}

	@MQjson(k = "getcharnew", v = {0, 2, 6, 7 })
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

	@MQjson(k = "setYearMethod", v = { 5, 6, 7 })
	public final void setYear(final int year, final int cc, final int[] k, final String[] kc) {
		//this.year = year;
	}

	public final int getMathematics() {
		return Mathematics;
	}

	public final void setMathematics(final int mathematics) {
		this.Mathematics = mathematics;
	}

	@MQjson(k = "getLanguageNew", v = { 1, 2 },t="静态化方法")
	public final static int getLanguage() {
		return 1001;
	}

	@MQjson(k = "getLanguageNew3", v = { 1, 3 })
	public final  String getLanguage3() {
		return "1002";
	}
	public final void setLanguage(final int language) {
		this.language = language;
	}

	@MQjson(k = "getChark", v = { 2, 4, 7 })
	public final char[] getChark() {
		return chark;
	}

}
