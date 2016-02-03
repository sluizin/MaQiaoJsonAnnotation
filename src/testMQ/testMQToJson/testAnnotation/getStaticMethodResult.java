package testMQ.testMQToJson.testAnnotation;
import java.io.File;
import java.io.FileOutputStream;

import MaQiao.MaQiaoJson.Annotation.annotationCache.AnnoBean;
import MaQiao.MaQiaoJson.Annotation.asmMQAnnotation.MQvisit;
import MaQiao.MaQiaoJson.Annotation.staticMethodASM;

/**
 * 想方设法得到无参数的静态方法
 * @author Sunjian
 *
 */
public final class getStaticMethodResult {
	
	@SuppressWarnings("unused")
	public static final Object getObject(final AnnoBean annoBean,final MQvisit mQvisit){
		try{
			staticMethodASM simpleJbean = new staticMethodASM();
			//simpleJbean.setDefaultclassName("MaQiao/MaQiaoJson/Annotation/blank");
			simpleJbean.setDefaultclassName("blank");
			simpleJbean.createBeanClass();
			System.out.println(annoBean.getClassName());
			System.out.println(mQvisit.getName());
			//simpleJbean.addFieldFromStaticMethod(annoBean.getClassName(),mQvisit.getName());

			File file = new File("F://class//Person2.class");
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(simpleJbean.getByte());
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Class<?> appClass = simpleJbean.getNewClass();
			Object object = appClass.newInstance();
			
		} catch (Exception e) {
			System.out.println("error:" + e.toString());
			return null;
		}
		return null;
	}
	
	
	
	
	
	
}
