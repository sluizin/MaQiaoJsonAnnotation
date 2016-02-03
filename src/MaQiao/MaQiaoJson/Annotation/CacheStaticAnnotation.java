package MaQiao.MaQiaoJson.Annotation;

/**
 * 静态常量池
 * @version 1.0
 * @since 1.7
 * @author Sunjian
 */
public final class CacheStaticAnnotation {
	/**
	 * MQJson注解 通过ASM得到对象及类型的偏移量 -> 缓存
	 */
	public static transient final annotationCache MQJAnnoCache = new annotationCache();
}
