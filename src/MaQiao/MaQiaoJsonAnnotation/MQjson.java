package MaQiao.MaQiaoJsonAnnotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
//import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 现只支持有String,char,char[]返回值方法<br/>
 * title="abc",group=1,2,4
 * @category 求Json注解
 * @author Sunjian
 */
@Retention(RetentionPolicy.CLASS)
@Target({ FIELD, METHOD })
@Documented
public @interface MQjson {
	String M() default "";

	int[] Q() default { 0 };
}