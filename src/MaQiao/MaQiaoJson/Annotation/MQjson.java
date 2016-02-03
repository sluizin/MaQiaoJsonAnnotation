package MaQiao.MaQiaoJson.Annotation;

//import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 现只支持有String,char,char[]返回值方法(FieldTypeEnum[] AllowFTE = { <br/>FieldTypeEnum.Boolean, FieldTypeEnum.BooleanObject, FieldTypeEnum.Byte, FieldTypeEnum.ByteObject, FieldTypeEnum.Char, FieldTypeEnum.Character,
 * FieldTypeEnum.Double, FieldTypeEnum.DoubleObject, FieldTypeEnum.Float, FieldTypeEnum.FloatObject, FieldTypeEnum.Int, FieldTypeEnum.Integer, FieldTypeEnum.Long, FieldTypeEnum.LongObject, FieldTypeEnum.Short,
 * FieldTypeEnum.ShortObject, FieldTypeEnum.String, FieldTypeEnum.Timestamp })<br/>
 * 暂不支持static方法与属性，但已保留识别<br/>
 * 自动过滤含 transient 属性的属性 <br/>
 * Bit方法时，以0-63数值进行分组。<br/>
 * 以下为有效设置方法
 * 
 * <pre>
 * MQjson
 * transient int month = 11;
 * 
 * MQjson(v = { 1, 2, 4 })
 * private final int year = 20;
 * 
 * MQjson(k = "Workday", v = { 1, 2, 6 })
 * int workday = 30;
 * 
 * MQjson(k = "Workday", v = { 1, 2, 6 },t="工作日期")
 * int workday = 30;
 * 
 * MQjson(k = "ScoreAverage", v = { 2, 4, 7 })
 * public int getScoreAverage() {
 * 	return (Mathematics + language) / 2;
 * }
 * MQjson(k = "getLanguageNew", v = { 1, 2 })
 * public final static int getLanguage() {
 * 	return 1001;
 * }
 * </pre>
 * 
 * <br/>
 * @category 求Json注解
 * @since 1.7
 * @version 1.2
 * @author Sunjian
 */
@Retention(RetentionPolicy.CLASS)
@Target({ FIELD, METHOD })
@Documented
public @interface MQjson {
	/**
	 * key 主键。如为空，则为属性名称或方法名
	 * @return String
	 */
	String k() default "";

	/**
	 * values 组号，以区分输出序列的方向
	 * @return int[]
	 */
	int[] v() default { 0 };

	/**
	 * 说明性文字，不参与序列化
	 * @return String
	 */
	String t() default "";
}