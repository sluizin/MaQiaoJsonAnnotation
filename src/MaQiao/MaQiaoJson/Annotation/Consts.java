package MaQiao.MaQiaoJson.Annotation;

import MaQiao.Constants.Constants;
import MaQiao.Constants.Constants.FieldTypeEnum;

/**
 * 常量池 <br/>
 * @version 1.0
 * @since 1.7
 * @author Sun.jian(孙.健) <br/>
 */
public final class Consts {

	/**
	 * 是否输出属性的数据类型信息
	 */
	static final boolean JsonAttrBoolean = false;
	/**
	 * 是否输出State(静态)属性的数据信息
	 */
	static final boolean JsonStateBoolean = true;
	/**
	 * 是否使用位进行判断
	 */
	static final boolean JsonCheckBitBoolean = true;
	/**
	 * ASM解析class时得到的注解 "LMaQiao/MaQiaoJson/Annotation/MQjson;" [MQjson所在的目录，用于在asm解析时查到此注解]
	 */
	static final String MQJsonASMAnnotation = "LMaQiao/MaQiaoJson/Annotation/MQjson;";
	/**
	 * json->Title
	 */
	static final char MQJsonAnnotationKey = 'k';
	/**
	 * json->标识数组
	 */
	static final char MQJsonAnnotationValues = 'v';
	/**
	 * json->说明性文字，不参与序列化
	 */
	static final char MQJsonAnnotationText = 't';
	/**
	 * 允许出现的属性
	 */
	static final FieldTypeEnum[] AllowFTE = { FieldTypeEnum.Boolean, FieldTypeEnum.BooleanObject, FieldTypeEnum.Byte, FieldTypeEnum.ByteObject, FieldTypeEnum.Char, FieldTypeEnum.Character,
			FieldTypeEnum.Double, FieldTypeEnum.DoubleObject, FieldTypeEnum.Float, FieldTypeEnum.FloatObject, FieldTypeEnum.Int, FieldTypeEnum.Integer, FieldTypeEnum.Long, FieldTypeEnum.LongObject,
			FieldTypeEnum.Short, FieldTypeEnum.ShortObject, FieldTypeEnum.String, FieldTypeEnum.Timestamp };
	/**
	 * 本对象的锁对象 locked 的地址偏移量
	 */
	static long lockedOffset = 0L;
	static {
		try {
			lockedOffset = Constants.UNSAFE.objectFieldOffset(annotationCache.class.getDeclaredField("locked"));/*得到锁对象的偏移量*/
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
		int value;

		/**
		 * 构造初始化
		 * @param value int
		 */
		private booleanType(final int value) {
			this.value = value;
		}
	}

	/**
	 * 用于移位时的基础单元:1L
	 */
	static final long bitBase = 1L;
	/**
	 * 用于移位判断时最小位值:0L
	 */
	static final int bitMin = 0;
	/**
	 * 用于移位判断时最大位值:63L
	 */
	static final int bitMax = 63;
	/**
	 * 静态方法输出接口
	 */
	static final String ASMStaticMethodInterface = "MaQiao/MaQiaoJson/Annotation/staticMethodInf";
	/**
	 * 静态方法的空类，用于重置
	 */
	static final String ASMStaticMethodBlank = "MaQiao.MaQiaoJson.Annotation.blank";
}
