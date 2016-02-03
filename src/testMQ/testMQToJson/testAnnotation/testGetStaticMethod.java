package testMQ.testMQToJson.testAnnotation;


import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import MaQiao.MaQiaoJson.Annotation.staticMethodASM;
import MaQiao.MaQiaoJson.Annotation.staticMethodInf;

public class testGetStaticMethod {
	public static final int staticFile(){
		return 1113;
	}
	public static final char staticFileStr(){
		return 67;
	}
	@Test
	public void test() {
		System.out.println(this.getClass().getName());
		
		
		testMQ.testMQToJson.testAnnotation.user.getLanguage();

		staticMethodASM simpleJbean = new staticMethodASM();
		simpleJbean.setDefaultclassName("MaQiao.MaQiaoJson.Annotation.blank");
		//simpleJbean.readClass(this.getClass());
		simpleJbean.createBeanClass();
		//simpleJbean.addFieldFromStaticMethod("testMQ/testMQToJson/testAnnotation/testGetStaticMethod","staticFile");
		boolean t=false;
		t=simpleJbean.addFieldFromStaticMethod(MaQiao.Constants.Constants.FieldTypeEnum.Char,"testMQ/testMQToJson/testAnnotation/testGetStaticMethod","staticFileStr");
		System.out.println("t:"+t);

		File file = new File("F://class//Person2.class");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(simpleJbean.getByte());
			fos.close();
			Class<?> appClass = simpleJbean.getNewClass();
			Object object = appClass.newInstance();
			System.out.println("hashcode:"+System.identityHashCode(this));
			System.out.println("hashcode:"+System.identityHashCode(object));
			staticMethodInf staticmethodObj=(staticMethodInf)object;
			System.out.println("AAA");
			System.out.println("cc:"+(char)staticmethodObj.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
