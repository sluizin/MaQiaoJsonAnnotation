package MaQiao.MaQiaoJson.Annotation;

import org.apache.commons.lang3.StringUtils;

import MaQiao.Constants.Constants;
import MaQiao.Constants.Constants.FieldTypeEnum;
import MaQiao.MaQiaoJson.Annotation.annotationCache.AnnoBean;
import MaQiao.MaQiaoJson.Annotation.asmMQAnnotation.MQvisit;

/**
 * 公共方法
 * @author Sunjian
 */
public final class util {

	/**
	 * 判断类型是否在允许范围内<br/>
	 * @param fte FieldTypeEnum
	 * @return int
	 */
	static final int AllowIndexOf(final FieldTypeEnum fte) {
		if (fte == null) return -1;
		for (int i = 0, len = Consts.AllowFTE.length; i < len; i++)
			if (Consts.AllowFTE[i] == fte) return i;
		return -1;
	}

	/**
	 * 把0-63位数值数组换成long类型，按位进行重组<br/>
	 * 
	 * <pre>
	 * arrayToBitLong{1,3,8,10}
	 * result:1290[10100001010]
	 * arrayToBitLong{1,4,9,10}
	 * result:1554[11000010010]
	 * </pre>
	 * @param array int[]
	 * @return long
	 */
	public static final long arrayToBitLong(final int... array) {
		long r = 0L;
		int len = array.length, v;
		while (--len >= 0)
			if ((v = array[len]) >= Consts.bitMin && v <= Consts.bitMax) r |= Consts.bitBase << v;
		return r;
	}

	/**
	 * 判断两个字符串是否相同，如有一方为null，则为false
	 * @param c String
	 * @param d String
	 * @return boolean
	 */
	static final boolean equals(final String c, final String d) {
		if (c == null || d == null) return false;
		if (System.identityHashCode(c) == System.identityHashCode(d)) return true;
		if (c.length() != d.length()) return false;
		return c.equals(d);
		//Constants.UNSAFE.getObject(c, Constants.StringArrayOffset);
	}

	/**
	 * 字符串与数组的比较
	 * @param c String
	 * @param d char[]
	 * @return boolean
	 */
	static final boolean equals(final String c, final char... d) {
		if (c == null || d == null) return false;
		if (System.identityHashCode(c) == System.identityHashCode(d)) return true;
		if (c.length() != d.length) return false;
		int len;
		if ((len = d.length) == 0) return false;
		Object obj = Constants.UNSAFE.getObject(c, Constants.StringArrayOffset);
		while (--len >= 0)
			if (d[len] != Constants.UNSAFE.getChar(obj, Constants.ArrayAddress + ((len) << 1))) return false;
		return true;
	}

	/**
	 * 想方设法得到无参数的静态方法
	 * @param annoBean AnnoBean
	 * @param mQvisit MQvisit
	 * @return Object
	 */
	public static final Object getObject(final AnnoBean annoBean, final MQvisit mQvisit) {
		try {
			staticMethodASM simpleJbean = new staticMethodASM();
			simpleJbean.setDefaultclassName(Consts.ASMStaticMethodBlank);
			simpleJbean.createBeanClass();
			//System.out.println(annoBean.getClassName());
			//System.out.println(mQvisit.Name);
			if (!simpleJbean.addFieldFromStaticMethod(mQvisit.returnFTE, StringUtils.replaceChars(annoBean.getClassName(), '.', '/'), mQvisit.Name)) {
				System.out.println("error");
				return null;
			}
			Class<?> appClass = simpleJbean.getNewClass();
			simpleJbean = null;
			/*			staticMethodInf staticmethodObj=(staticMethodInf)appClass.newInstance();
						Object obj=staticmethodObj.getObject();
						System.out.println("cc:"+(int)obj);
						return obj;*/
			return ((staticMethodInf) appClass.newInstance()).getObject();
		} catch (Exception e) {
			System.out.println("error:" + e.toString());
			return null;
		}
	}
}
