package testMQ.testMQToJson.testToJson;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import net.sf.json.*;
import MaQiao.MaQiaoJson.ToJson.MQToJson;
import MaQiao.MaQiaoJson.ToJson.MQToJsonFixed;

@SuppressWarnings("unused")
public class testCorp {


	// private static Connection dbConn = null;



	@Test
	public void test4() throws JsonProcessingException {
		Monitoring.begin();
		List<Corp> list = new ArrayList<Corp>();
		for (int i = 0; i < 1000; i++) {
			//list.add(fullObject(Corp.class));
			list.add(new Corp());
		}
		Monitoring.end("生成数据");
		int ssize=12;		
		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		MQjsonOffset(list);
		Monitoring.end("Offset");
		}

		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		jackson(list);
		Monitoring.end("Jackson");
		}


		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		fastjson(list);
		Monitoring.end("fastjson");
		}
		
		String c="abc";
		System.out.println("Back:"+System.identityHashCode(c));
		aa(c);
		kk k=new kk();
		System.out.println("kBack:"+System.identityHashCode(k));
		System.out.println("aaalast:"+System.identityHashCode(k.cc));
		bb(k);
		System.out.println("klast:"+System.identityHashCode(k));
		System.out.println("aaalast:"+System.identityHashCode(k.cc));
	}
	static void bb(final kk k){
		System.out.println("kafter:"+System.identityHashCode(k));
		k.cc="ccc";
	}
	static class kk{
		String cc="tt";
	}
	static void aa(final String str){
		System.out.println("after:"+System.identityHashCode(str));
	}

	public static void MQjsonOffset(List<Corp> list) {
		String a = null;
		int len=list.size();
		//MQToJsonOffset.init(Corp.class);
		//try (MQSBuilderFixed sb = new MQSBuilderFixed(5000);) {
			for (Corp corp : list) {
				//sb.clear();
				//sb.append((MQToJsonFixed.Object2Json(corp)),true);
				//MQToJsonOffset.Object2Json(corp).free();
				//if((len--)==1){
					//a=MQToJsonOffset.toJson(corp).toString();
					//break;
				//}else{
					a=MQToJson.toJsonString(corp);					
				//}
			}
			//a = sb.toString();
			//}
		//System.out.println(a);
	}
	public static void MQjsonFixed(List<Corp> list) {
		String a = null;
		//try (MQSBuilderFixed sb = new MQSBuilderFixed(5000);) {
			for (Corp corp : list) {
				//sb.clear();
				//sb.append((MQToJsonFixed.Object2Json(corp)),true);
				MQToJsonFixed.Object2Json(corp).free();
			}
			//a = sb.toString();
			//}
		//System.out.println(a);
	}


	public static void libjson(List<Corp> list) {
		String string = null;
		for (Corp corp : list) {
			string = JSONObject.fromObject(corp).toString();
		}
		//System.out.println(string);
	}

	public static void fastjson(List<Corp> list) {
		String string = null;
		for (Corp corp : list) {
			string = JSON.toJSONString(corp);
		}
		//System.out.println(string);
	}

	public static void jackson(List<Corp> list) throws JsonProcessingException {
		ObjectMapper a = new ObjectMapper();
		String string = null;
		for (Corp corp : list) {
			try {
				string = a.writeValueAsString(corp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(string);
	}

	/**
	 * 填充一个对象（一般用于测试）
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T fullObject(Class<T> cl) {
		T t = null;
		try {
			t = cl.newInstance();
			Method methods[] = cl.getMethods();
			for (Method method : methods) {
				// 如果是set方法,进行随机数据的填充
				if (method.getName().indexOf("set") == 0) {
					Class param = method.getParameterTypes()[0];
					if (param.equals(String.class)) {
						method.invoke(t, "aabbcc");
					} else if (param.equals(Short.class)) {
						method.invoke(t, (short) new Random().nextInt(5));
					} else if (param.equals(Float.class)) {
						method.invoke(t, new Random().nextFloat());
					} else if (param.equals(Double.class)) {
						method.invoke(t, new Random().nextDouble());
					} else if (param.equals(Integer.class)) {
						method.invoke(t, new Random().nextInt(10));
					} else if (param.equals(Long.class)) {
						method.invoke(t, new Random().nextLong());
					} else if (param.equals(Date.class)) {
						method.invoke(t, new Date());
					} else if (param.equals(Timestamp.class)) {
						method.invoke(t, new Timestamp(System.currentTimeMillis()));
					} else if (param.equals(java.sql.Date.class)) {
						method.invoke(t, new java.sql.Date(System.currentTimeMillis()));
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}
}
