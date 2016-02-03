package testMQ.testMQToJson.testAnnotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import MaQiao.MaQiaoJson.Annotation.toJson;
import MaQiao.MaQiaoJson.ToJson.MQToJson;
import testMQ.testMQToJson.testToJson.Monitoring;

@SuppressWarnings("unused")
public class testTojson {

	@Test
	public void test() throws JsonProcessingException {
		String strspl="------------------------------------------------------------------------------";
		Monitoring.begin();
		List<user> list = new ArrayList<user>();
		for (int i = 0; i < 1; i++) {
			//list.add(fullObject(Corp.class));
			list.add(new user());
		}
		Monitoring.end("生成数据");
		System.out.println(strspl);
		int ssize=1;		
		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		MQjsonOffset(list);
		Monitoring.end("Offset");
		}
		System.out.println(strspl);
		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		jackson(list);
		Monitoring.end("Jackson");
		}
		System.out.println(strspl);


		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		fastjson(list);
		Monitoring.end("fastjson");
		}
		System.out.println(strspl);

		for(int i=0;i<ssize;i++){
		Monitoring.begin();
		MQAnnotationToJson(list);
		Monitoring.end("MQAnnotationToJson");
		}
		System.out.println(strspl);
		
		
	}
	public static void MQAnnotationToJson(List<user> list) {
		String a = null;
		int len=list.size();
			for (user corp : list) {
					a=toJson.toJsonString(corp,2);
			}
			System.out.println(a);
	}
	public static void MQjsonOffset(List<user> list) {
		String a = null;
		int len=list.size();
			for (user corp : list) {
					a=MQToJson.toJsonString(corp);
			}
	}


	public static void libjson(List<user> list) {
		String string = null;
		for (user corp : list) {
			string = JSONObject.fromObject(corp).toString();
		}
		//System.out.println(string);
	}

	public static void fastjson(List<user> list) {
		String string = null;
		for (user corp : list) {
			string = JSON.toJSONString(corp);
		}
		//System.out.println(string);
	}

	public static void jackson(List<user> list) throws JsonProcessingException {
		ObjectMapper a = new ObjectMapper();
		String string = null;
		for (user corp : list) {
			try {
				string = a.writeValueAsString(corp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(string);
	}
}
