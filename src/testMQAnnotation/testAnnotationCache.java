package testMQAnnotation;
import org.junit.Test;

import MaQiao.MaQiaoJsonAnnotation.MQJsonAnnotationCache;

@SuppressWarnings("unused")
public class testAnnotationCache {
	static MQJsonAnnotationCache cache=new MQJsonAnnotationCache();
	@Test
	public void test() {
		long starttime,endtime;
		user corp=new user();

		starttime=System.nanoTime();
		MQJsonAnnotationCache.Bean a=cache.get(user.class);
		endtime=System.nanoTime();
		System.out.println("1Time\t:"+(endtime-starttime));
		
		
		starttime=System.nanoTime();
		MQJsonAnnotationCache.Bean b=cache.get(corp.getClass());
		endtime=System.nanoTime();
		System.out.println("2Time\t:"+(endtime-starttime));

		starttime=System.nanoTime();
		MQJsonAnnotationCache.Bean c=cache.get(corp.getClass());
		endtime=System.nanoTime();
		System.out.println("2Time\t:"+(endtime-starttime));
		
		starttime=System.nanoTime();
		MQJsonAnnotationCache.Bean d=cache.get(corp.getClass());
		endtime=System.nanoTime();
		System.out.println("2Time\t:"+(endtime-starttime));
		
		
		//System.out.println(cache.toString());
	}

}
