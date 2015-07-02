package testMQAnnotation;

import java.io.IOException;
import java.util.LinkedList;

//import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation;
import MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation.MQvisit;

//import com.alibaba.fastjson.JSON;

@SuppressWarnings("unused")
public class testannotation {

	@Test
	public void test() {
		user nuser = new user();
		nuser.name = "lixiao";
		nuser.language = 102;
		nuser.Mathematics = 121;
		//nuser.year = 32;
		String string = null;
		//		ObjectMapper a = new ObjectMapper();
		//		try {
		//			string = a.writeValueAsString(nuser);
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		//System.out.println("jackson:"+string);
		//string = JSON.toJSONString(nuser);
		//System.out.println("fastjson:"+string);
		asmMQAnnotation aca = new asmMQAnnotation(user.class);
		LinkedList<MQvisit> MQjsonList = aca.getMQjsonLinkedList();
		for (int i = 0, len = MQjsonList.size(); i < len; i++) {
			System.out.println("MQjsonList[" + i + "]:" + MQjsonList.get(i).toString());
		}
		//System.out.println("id"+System.identityHashCode("title"));

	}

}
