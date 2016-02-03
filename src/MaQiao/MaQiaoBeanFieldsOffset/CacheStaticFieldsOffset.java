package MaQiao.MaQiaoBeanFieldsOffset;

import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset;

/**
 * 静态常量池
 * @version 1.0
 * @since 1.7
 * @author Sunjian
 */
public final class CacheStaticFieldsOffset {
	/**
	 * MQBeanFieldsOffset 通过反射得到对象及类型的偏移量 -> 缓存
	 */
	public static transient final MQBeanFieldsOffset MQBFOffset = new MQBeanFieldsOffset();
}
