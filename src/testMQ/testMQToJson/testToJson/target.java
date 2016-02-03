package testMQ.testMQToJson.testToJson;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
@SuppressWarnings(value={"rawtypes","unchecked"})
public class target {
	  private String g="aaabbccddefff";
	  private static final long serialVersionUID = -5182532647273106745L;
	  private int a=5;
	  private Integer b=new Integer(5);
	  private double h=100;
	  private Byte l;
	  private float m=100;
	  private Float n=new Float(101);
	  private char o='c';
	  private Short p;
	  
	  private String[] q={"aaaa"};
	  private int[] r={100,200};
	  private target2 a2=new target2();
	  private target2[] s={a2,a2,a2};
	  private Corp a22=new Corp();
	  
	  
	  
	  private List<String> aa=new ArrayList<String>();
	  private List<target2>  List_target2=new ArrayList<target2>();
	  private HashMap<String,Object>     hashmap     =     new     HashMap<String,Object>();
	  private Map<String,Object>     MAP     =     new     HashMap<String,Object>();      

	private Set SETA=new HashSet();
	  private Set<target2> SETB=new HashSet<target2>();
	  private Set<Integer> SETC=new HashSet<Integer>();
	  
	  
	  public Corp getA22() {
		return a22;
	}


	public void setA22(Corp a22) {
		this.a22 = a22;
	}


	public Integer getB() {
		return b;
	}


	public void setB(Integer b) {
		this.b = b;
	}


	public String getG() {
		return g;
	}


	public void setG(String g) {
		this.g = g;
	}


	public double getH() {
		return h;
	}


	public void setH(double h) {
		this.h = h;
	}


	public Byte getL() {
		return l;
	}


	public void setL(Byte l) {
		this.l = l;
	}


	public Short getP() {
		return p;
	}


	public void setP(Short p) {
		this.p = p;
	}


	public String[] getQ() {
		return q;
	}


	public void setQ(String[] q) {
		this.q = q;
	}


	public int[] getR() {
		return r;
	}


	public void setR(int[] r) {
		this.r = r;
	}


	public target2[] getS() {
		return s;
	}


	public void setS(target2[] s) {
		this.s = s;
	}


	public List<target2> getList_target2() {
		return List_target2;
	}


	public void setList_target2(List<target2> list_target2) {
		List_target2 = list_target2;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((List_target2 == null) ? 0 : List_target2.hashCode());
		result = prime * result + ((MAP == null) ? 0 : MAP.hashCode());
		result = prime * result + ((SETA == null) ? 0 : SETA.hashCode());
		result = prime * result + ((SETB == null) ? 0 : SETB.hashCode());
		result = prime * result + ((SETC == null) ? 0 : SETC.hashCode());
		result = prime * result + a;
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		result = prime * result + ((aa == null) ? 0 : aa.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((g == null) ? 0 : g.hashCode());
		long temp;
		temp = Double.doubleToLongBits(h);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((hashmap == null) ? 0 : hashmap.hashCode());
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		result = prime * result + Float.floatToIntBits(m);
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		result = prime * result + o;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + Arrays.hashCode(q);
		result = prime * result + Arrays.hashCode(r);
		result = prime * result + Arrays.hashCode(s);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		target other = (target) obj;
		if (List_target2 == null) {
			if (other.List_target2 != null)
				return false;
		} else if (!List_target2.equals(other.List_target2))
			return false;
		if (MAP == null) {
			if (other.MAP != null)
				return false;
		} else if (!MAP.equals(other.MAP))
			return false;
		if (SETA == null) {
			if (other.SETA != null)
				return false;
		} else if (!SETA.equals(other.SETA))
			return false;
		if (SETB == null) {
			if (other.SETB != null)
				return false;
		} else if (!SETB.equals(other.SETB))
			return false;
		if (SETC == null) {
			if (other.SETC != null)
				return false;
		} else if (!SETC.equals(other.SETC))
			return false;
		if (a != other.a)
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		if (aa == null) {
			if (other.aa != null)
				return false;
		} else if (!aa.equals(other.aa))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (g == null) {
			if (other.g != null)
				return false;
		} else if (!g.equals(other.g))
			return false;
		if (Double.doubleToLongBits(h) != Double.doubleToLongBits(other.h))
			return false;
		if (hashmap == null) {
			if (other.hashmap != null)
				return false;
		} else if (!hashmap.equals(other.hashmap))
			return false;
		if (l == null) {
			if (other.l != null)
				return false;
		} else if (!l.equals(other.l))
			return false;
		if (Float.floatToIntBits(m) != Float.floatToIntBits(other.m))
			return false;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		if (o != other.o)
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (!Arrays.equals(q, other.q))
			return false;
		if (!Arrays.equals(r, other.r))
			return false;
		if (!Arrays.equals(s, other.s))
			return false;
		return true;
	}


	public HashMap<String, Object> getHashmap() {
		return hashmap;
	}


	public void setHashmap(HashMap<String, Object> hashmap) {
		this.hashmap = hashmap;
	}


	public Map<String, Object> getMAP() {
		return MAP;
	}


	public void setMAP(Map<String, Object> mAP) {
		MAP = mAP;
	}


	public Set getSETA() {
		return SETA;
	}


	public void setSETA(Set sETA) {
		SETA = sETA;
	}


	public Set<target2> getSETB() {
		return SETB;
	}


	public void setSETB(Set<target2> sETB) {
		SETB = sETB;
	}


	public Set<Integer> getSETC() {
		return SETC;
	}


	public void setSETC(Set<Integer> sETC) {
		SETC = sETC;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public float getM() {
		return m;
	}


	public void setM(float m) {
		this.m = m;
	}


	public Float getN() {
		return n;
	}


	public void setN(Float n) {
		this.n = n;
	}


	public char getO() {
		return o;
	}


	public void setO(char o) {
		this.o = o;
	}


	public int getA() {
		return a;
	}


	public void setA(int a) {
		this.a = a;
	}


	public target2 getA2() {
		return a2;
	}


	public void setA2(target2 a2) {
		this.a2 = a2;
	}


	public List<String> getAa() {
		return aa;
	}


	public void setAa(List<String> aa) {
		this.aa = aa;
	}


	public target(){
		  l=32;
		  aa.add("aa");
		  aa.add("bb");
		  p=100;
		  target2 a22=new target2(100,"ccc");
		  List_target2.add(a22);
		  target2 a222=new target2(200,"ddd");
		  List_target2.add(a222);
		  hashmap.put("hashmap", "hashmapnew");
		  hashmap.put("object1", a22);
		  hashmap.put("object2", a222);
		  MAP.put("MAP1",a22);
		  MAP.put("MAP2",a222);
		  SETA.add("set1");
		  SETA.add("set2");
		  SETB.add(a22);
		  SETB.add(a222);
		  SETC.add(1000);
		  SETC.add(2000);
	  }
}
