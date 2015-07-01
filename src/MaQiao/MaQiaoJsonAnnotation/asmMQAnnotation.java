package MaQiao.MaQiaoJsonAnnotation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;

import static MaQiao.Constants.Constants.FieldTypeEnum;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import MaQiao.Constants.Constants;
import MaQiao.Constants.UNSAFEcommon;
//import org.objectweb.asm.tree.AnnotationNode;
//import org.objectweb.asm.tree.ClassNode;
//import org.objectweb.asm.tree.FieldNode;
//import org.objectweb.asm.tree.MethodNode;

/**
 * 通过asm检索类，并得到我需要的注解的属性与方法，并得到方法的返回类型，以及是否有参数 ASM-4.2
 * @serial 1.7
 * @author Sunjian
 */
public final class asmMQAnnotation{
	transient int identityHashCode = 0;
	transient String className = null;
	transient Class<?> classN = null;
	transient LinkedList<MQvisit> MQjsonList = new LinkedList<MQvisit>();

	public final LinkedList<MQvisit> getMQjsonLinkedList() {
		return MQjsonList;
	}

	public final MQvisit[] getMQjsonArray() {
		if(MQjsonList==null)return new MQvisit[0];
		final int len = MQjsonList.size();
		if(len==0)return new MQvisit[0];
		final MQvisit[] mQvisits=new MQvisit[len];
		int i=0;
		for (MQvisit j : MQjsonList)	mQvisits[i++]=j;
		return mQvisits;
	}
	public asmMQAnnotation(final Object obj) {
		init(obj.getClass());
	}

	public asmMQAnnotation(final Class<?> classN) {
		init(classN);
	}

	private void init(final Class<?> classN) {
		try {
			this.classN = classN;
			this.className = classN.getName();
			ClassReader cr = new ClassReader(this.className);
			this.identityHashCode = System.identityHashCode(classN);
			TestVisitorField visitorField = new TestVisitorField();
			visitorField.setClass(classN);
			cr.accept(visitorField, 0);
			MQjsonList = visitorField.MQjsonList;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 对类进行分解
	 * @author sunjian
	 */
	static final class TestVisitorField extends ClassVisitor {
		final LinkedList<MQvisit> MQjsonList = new LinkedList<MQvisit>();

		public TestVisitorField() {
			super(Opcodes.ASM4);
		}

		transient Class<?> classN = null;

		public final void setClass(final Class<?> classN) {
			this.classN = classN;
		}

		/**
		 * 属性
		 */
		@Override
		public FieldVisitor visitField(final int access, final String fieldname, final String desc, final String sig, final Object value) {
			/*transient 修饰符表示不参与序列化生成*/
			if ((access & Opcodes.ACC_TRANSIENT) == Opcodes.ACC_TRANSIENT) return null;
			FieldVisitor fv = new FieldVisitor(Opcodes.ASM4) {
				@Override
				public AnnotationVisitor visitAnnotation(String name, boolean b) {
					//System.out.println("access:"+access+"\tfieldname:"+fieldname);
					if (UNSAFEcommon.equals(name, Consts.MQJsonASMAnnotation)) {
						final MQvisit f = new MQvisit();
						if (desc.charAt(0) == '[') {
							f.returnFTE = FieldTypeEnum.getByASMType(desc.substring(1));
							f.returnIsArray = true;
						} else {
							f.returnFTE = FieldTypeEnum.getByASMType(desc.substring(0));
						}
						if (Consts.AllowIndexOf(f.returnFTE) == -1) return null;
						f.type = 0;
						f.Name = fieldname;
						/*static 修饰符表示静态对象*/
						if ((access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC) {
							f.isStatic = true;
							try {
								final Field field = classN.getDeclaredField(fieldname);
								f.offSet = Constants.UNSAFE.staticFieldOffset(field);
								f.staticObject = Constants.UNSAFE.staticFieldBase(field);
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							}

						} else {
							try {
								f.offSet = Constants.UNSAFE.objectFieldOffset(classN.getDeclaredField(fieldname));
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							}
						}

						AnnotationVisitor avs = new AnnotationVisitor(Opcodes.ASM4) {
							public void visit(final String name, final Object value) {
								if (UNSAFEcommon.equals(name, Consts.MQJsonAnnotationTitle)) {
									f.MQAnnotationTitle = (String) value;
									return;
								}
								if (UNSAFEcommon.equals(name, Consts.MQJsonAnnotationGroup)) {
									f.MQAnnotationGroup = (int[]) value;
									return;
								}
							}
						};
						MQjsonList.add(f);
						return avs;
					}
					return null;
				}
			};
			return fv;
		}

		/**
		 * 方法
		 */
		@Override
		public MethodVisitor visitMethod(final int access, final String methodname, final String desc, final String sig, final String[] value) {
			if (desc.charAt(1) != ')') return null;
			MethodVisitor mv = new MethodVisitor(Opcodes.ASM4) {
				@Override
				public AnnotationVisitor visitAnnotation(final String name, final boolean b) {
					if (UNSAFEcommon.equals(name, Consts.MQJsonASMAnnotation)) {
						final MQvisit f = new MQvisit();
						f.returnFTE = FieldTypeEnum.getByASMType(desc.substring((f.returnIsArray = ((desc.charAt(2) == '[') ? true : false)) ? 3 : 2));
						if (Consts.AllowIndexOf(f.returnFTE) == -1) return null;
						f.type = 1;
						f.Name = methodname;
						/*static 修饰符表示静态对象*/
						if ((access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC) {
							f.isStatic = true;
						}
						AnnotationVisitor avs = new AnnotationVisitor(Opcodes.ASM4) {
							public void visit(String name, Object value) {
								if (UNSAFEcommon.equals(name, Consts.MQJsonAnnotationTitle)) {
									f.MQAnnotationTitle = (String) value;
									return;
								}
								if (UNSAFEcommon.equals(name, Consts.MQJsonAnnotationGroup)) {
									f.MQAnnotationGroup = (int[]) value;
									return;
								}
							}
						};
						MQjsonList.add(f);
						return avs;
					}
					return null;
				}
			};
			return mv;
		}

		/**
		 * 类的注解
		 */
		@Override
		public AnnotationVisitor visitAnnotation(String name, boolean b) {
			AnnotationVisitor av = super.visitAnnotation(name, b);
			System.out.println(name + "\t" + b);
			return av;
		}
	}

	public static final class MQvisit {
		/**
		 * 类型：<br/>
		 * 0来源于属性<br/>
		 * 1来源于方法<br/>
		 */
		int type = 0;
		/**
		 * 类属性或方法的名称<br/>
		 */
		String Name = null;
		/**
		 * MQjson注解中的title String
		 */
		String MQAnnotationTitle = null;
		/**
		 * MQjson注解中的group int[]
		 */
		int[] MQAnnotationGroup = { 0 };
		/**
		 * 返回类型
		 */
		FieldTypeEnum returnFTE = null;
		/**
		 * 返回值是否为数组
		 */
		boolean returnIsArray = false;
		/**
		 * 是否有参数
		 */
		//boolean existParameter = false; //if (desc.charAt(1) != ')') f.existParameter=true;
		/**
		 * 判断是否是静态属性/方法
		 */
		transient boolean isStatic = false;
		/**
		 * 静态属性的对象
		 */
		transient Object staticObject = null;
		/**
		 * 属性的偏移量
		 */
		transient long offSet = 0L;

		public MQvisit() {

		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(250);
			builder.append("\tMQvisit [type=");
			builder.append(type);
			builder.append(", \tName=");
			builder.append(Name);
			builder.append(", \tMQAnnotationTitle=");
			builder.append(MQAnnotationTitle);
			builder.append(", \tMQAnnotationGroup=");
			builder.append(Arrays.toString(MQAnnotationGroup));
			builder.append(", \treturnFTE=");
			builder.append(returnFTE);
			builder.append(", \treturnIsArray=");
			builder.append(returnIsArray);
			builder.append(", \tisStatic=");
			builder.append(isStatic);
			builder.append(", \tstaticObject=");
			builder.append(staticObject);
			builder.append(", \toffSet=");
			builder.append(offSet);
			builder.append("]");
			return builder.toString();
		}

	}

}
