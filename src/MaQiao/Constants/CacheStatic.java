package MaQiao.Constants;

//import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset;
import MaQiao.MaQiaoJsonAnnotation.MQJsonAnnotationCache;

public final class CacheStatic {
	/**
	 * MQJson注解 通过ASM得到对象及类型的偏移量 -> 缓存
	 */
	public static final MQJsonAnnotationCache MQJAnnoCache = new MQJsonAnnotationCache();
	/**
	 * MQBeanFieldsOffset 通过反射得到对象及类型的偏移量 -> 缓存
	 */
	//public static final MQBeanFieldsOffset MQBFOffset = new MQBeanFieldsOffset();
}
