package MaQiao.MaQiaoJsonAnnotation;

import MaQiao.Constants.Constants;
import MaQiao.Constants.Constants.FieldTypeEnum;
import static MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation.MQvisit;

/**
 * 常量池 <br/>
 * @author Sun.jian(孙.健) <br/>
 */
public final class Consts {
	static final int[] DefaultIntNull = { 0 };
	static final MQvisit[] defaultNull = new MQvisit[0];
	/**
	 * ASM解析class时得到的注解 "LMaQiao/MaQiaoAnnotationToJson/MQjson;"
	 */
	static final String MQJsonASMAnnotation = "LMaQiao/MaQiaoJsonAnnotation/MQjson;";
	//static final char[] MQJsonAnnotationTitle = { 'T', 'i', 't', 'l', 'e' };
	//static final String MQJsonAnnotationTitle = "key";
	static final char MQJsonAnnotationKey = 'k';
	//static final char[] MQJsonAnnotationGroup = { 'G', 'r', 'o', 'u', 'p', 's' };
	//static final String MQJsonAnnotationGroup = "values";
	static final char MQJsonAnnotationValues = 'v';
	static final char MQJsonAnnotationText = 't';
	public static final FieldTypeEnum[] AllowFTE = { FieldTypeEnum.Boolean, FieldTypeEnum.BooleanObject, FieldTypeEnum.Byte, FieldTypeEnum.ByteObject, FieldTypeEnum.Char, FieldTypeEnum.Character,
			FieldTypeEnum.Double, FieldTypeEnum.DoubleObject, FieldTypeEnum.Float, FieldTypeEnum.FloatObject, FieldTypeEnum.Int, FieldTypeEnum.Integer, FieldTypeEnum.Long, FieldTypeEnum.LongObject,
			FieldTypeEnum.Short, FieldTypeEnum.ShortObject, FieldTypeEnum.String, FieldTypeEnum.Timestamp };

	/**
	 * 判断类型是否在允许范围内<br/>
	 * @param fte FieldTypeEnum
	 * @return int
	 */
	public static final int AllowIndexOf(final FieldTypeEnum fte) {
		if (fte == null) return -1;
		for (int i = 0, len = AllowFTE.length; i < len; i++)
			if (AllowFTE[i] == fte) return i;
		return -1;
	}

	/**
	 * 本对象的锁对象 locked 的地址偏移量
	 */
	static long lockedOffset = 0L;
	static {
		try {
			lockedOffset = Constants.UNSAFE.objectFieldOffset(MQJsonAnnotationCache.class.getDeclaredField("locked"));/*得到锁对象的偏移量*/
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * boolean的状态:<br/>
	 * False(0):假<br/>
	 * True(1):真<br/>
	 * @author Sunjian
	 */
	static enum booleanType {
		/**
		 * 假(0)
		 */
		False(0),
		/**
		 * 真(1)
		 */
		True(1);
		/**
		 * False:假(0)<br/>
		 * True:真(1)<br/>
		 */
		int index;

		/**
		 * 构造初始化
		 * @param index int
		 */
		private booleanType(final int index) {
			this.index = index;
		}
	}

	/**
	 * 用于移位时的基础单元:1L
	 */
	private static final long bitBase = 1L;
	/**
	 * 用于移位判断时最小位值:0L
	 */
	private static final int bitMin = 0;
	/**
	 * 用于移位判断时最大位值:63L
	 */
	private static final int bitMax = 63;

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
			if ((v = array[len]) >= bitMin && v <= bitMax) r |= bitBase << v;
		//for (int i = 0, v, len = array.length; i < len; i++)
		//if ((v = array[i]) >= bitMin && v <= bitMax) r |= (bitBase << v);
		return r;
	}
}
