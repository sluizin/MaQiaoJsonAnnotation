package MaQiao.MaQiaoJsonAnnotation;

import MaQiao.Constants.Constants;
import MaQiao.Constants.Constants.FieldTypeEnum;
import static MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation.MQvisit;
/**
 * 常量池 <br/>
 * @author Sun.jian(孙.健) <br/>
 */
public final class Consts {
	static final MQvisit[] defaultNull=new MQvisit[0];
	/**
	 * ASM解析class时得到的注解 "LMaQiao/MaQiaoAnnotationToJson/MQjson;"
	 */
	static final String MQJsonASMAnnotation = "LMaQiao/MaQiaoJsonAnnotation/MQjson;";
	static final char[] MQJsonAnnotationTitle = { 'T', 'i', 't', 'l', 'e' };
	//static final String MQJsonAnnotationTitle = "title";
	static final char[] MQJsonAnnotationGroup = { 'G', 'r', 'o', 'u', 'p', 's' };
	//static final String MQJsonAnnotationGroup = "group";
	public static final FieldTypeEnum[] AllowFTE = { FieldTypeEnum.Boolean, FieldTypeEnum.BooleanObject, FieldTypeEnum.Byte, FieldTypeEnum.ByteObject, FieldTypeEnum.Char, FieldTypeEnum.Double,
			FieldTypeEnum.DoubleObject, FieldTypeEnum.Float, FieldTypeEnum.FloatObject, FieldTypeEnum.Int, FieldTypeEnum.Integer, FieldTypeEnum.Long, FieldTypeEnum.LongObject, FieldTypeEnum.Short,
			FieldTypeEnum.ShortObject, FieldTypeEnum.String, FieldTypeEnum.Timestamp };

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
		/**
		 * 得到枚举中的int index值
		 * @return int
		 */
		public final int getIndex(){
			return index;
		}
		public static final booleanType getBooleanType(final int index) {
			for (booleanType c : booleanType.values())
				if (c.index == index) return c;
			return null;
		}
	}	
}
