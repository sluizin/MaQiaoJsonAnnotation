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

import com.esotericsoftware.reflectasm.MethodAccess;

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
public final class asmMQAnnotation {
	transient int identityHashCode = 0;
	transient String className = null;
	transient Class<?> classN = null;
	transient MethodAccess access = null;
	transient LinkedList<MQvisit> MQjsonList = new LinkedList<MQvisit>();

	public final LinkedList<MQvisit> getMQjsonLinkedList() {
		return MQjsonList;
	}

	public final MQvisit[] getMQjsonArray() {
		if (MQjsonList == null) return new MQvisit[0];
		final int len = MQjsonList.size();
		if (len == 0) return new MQvisit[0];
		final MQvisit[] mQvisits = new MQvisit[len];
		int i = 0;
		for (MQvisit j : MQjsonList)
			mQvisits[i++] = j;
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
			access = MethodAccess.get(classN);
			ClassReader cr = new ClassReader(this.className);
			this.identityHashCode = System.identityHashCode(classN);
			TestVisitorField visitorField = new TestVisitorField();
			visitorField.classN = classN;
			cr.accept(visitorField, 0);
			MQjsonList = visitorField.MQjsonList;
			for (MQvisit e : MQjsonList)
				//System.out.println("e.Name:" + e.Name);
				/* 方法、非static access=public 才能得到 MethodAccessIndex */
				if (e.type == 1 && !e.isStatic && (e.access & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC) e.MethodAccessIndex = access.getIndex(e.Name);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public final MethodAccess getAccess() {
		return access;
	}

	/**
	 * 对类进行分解
	 * @author sunjian
	 */
	static final class TestVisitorField extends ClassVisitor {
		transient Class<?> classN = null;
		/**
		 * 使用链表保存结果集
		 */
		final LinkedList<MQvisit> MQjsonList = new LinkedList<MQvisit>();

		public TestVisitorField() {
			super(Opcodes.ASM4);
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
						f.access = access;
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
								final Field field = classN.getDeclaredField(fieldname);
								f.offSet = Constants.UNSAFE.objectFieldOffset(field);
								//f.staticObject = Constants.UNSAFE.(field);
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							}
						}
						AnnotationVisitor avs = new AnnotationVisitor(Opcodes.ASM4) {
							public void visit(final String name, final Object value) {
								switch (name.charAt(0)) {
								case Consts.MQJsonAnnotationKey:
									f.MQAnnotationKey = (String) value;
									return;
								case Consts.MQJsonAnnotationValues:
									f.MQAnnotationValues = (int[]) value;
									return;
								case Consts.MQJsonAnnotationText:
									f.MQAnnotationText = (String) value;
									return;
								default:
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
						f.access = access;
						f.type = 1;
						f.Name = methodname;
						/*static 修饰符表示静态对象*/
						if ((access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC) {
							f.isStatic = true;
						}
						AnnotationVisitor avs = new AnnotationVisitor(Opcodes.ASM4) {
							public void visit(String name, Object value) {
								switch (name.charAt(0)) {
								case Consts.MQJsonAnnotationKey:
									f.MQAnnotationKey = (String) value;
									return;
								case Consts.MQJsonAnnotationValues:
									f.MQAnnotationValues = (int[]) value;
									return;
								case Consts.MQJsonAnnotationText:
									f.MQAnnotationText = (String) value;
									return;
								default:
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
		 * ASM的属性或方法的 修饰符
		 */
		int access = 0;
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
		String MQAnnotationKey = null;
		/**
		 * MQjson注解中的group int[]
		 */
		int[] MQAnnotationValues = { 0 };
		/**
		 * 说明性文字
		 */
		String MQAnnotationText = "";
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
		/**
		 * MethodAccessIndex ReflectASM 方法和字段的索引
		 */
		transient int MethodAccessIndex = -1;

		public MQvisit() {

		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MQvisit [access=");
			builder.append(access);
			builder.append(", type=");
			builder.append(type);
			builder.append(", Name=");
			builder.append(Name);
			builder.append(", MQAnnotationKey=");
			builder.append(MQAnnotationKey);
			builder.append(", MQAnnotationValues=");
			builder.append(Arrays.toString(MQAnnotationValues));
			builder.append(", MQAnnotationText=");
			builder.append(MQAnnotationText);
			builder.append(", returnFTE=");
			builder.append(returnFTE);
			builder.append(", returnIsArray=");
			builder.append(returnIsArray);
			builder.append(", isStatic=");
			builder.append(isStatic);
			builder.append(", staticObject=");
			builder.append(staticObject);
			builder.append(", offSet=");
			builder.append(offSet);
			builder.append(", MethodAccessIndex=");
			builder.append(MethodAccessIndex);
			builder.append("]");
			return builder.toString();
		}

		public final int getAccess() {
			return access;
		}

		public final int getType() {
			return type;
		}

		public final String getName() {
			return Name;
		}

		public final String getMQAnnotationM() {
			return MQAnnotationKey;
		}

		/**
		 * 得到真正的json的Title，如果未用注解指定Title，则直接用属性名或方法名
		 * @return String
		 */
		public final String getRealName() {
			if (MQAnnotationKey == null || MQAnnotationKey.length() == 0) return Name;
			return MQAnnotationKey;
		}

		public final int[] getMQAnnotationQ() {
			return MQAnnotationValues;
		}

		public final FieldTypeEnum getReturnFTE() {
			return returnFTE;
		}

		public final boolean isReturnIsArray() {
			return returnIsArray;
		}

		public final boolean isStatic() {
			return isStatic;
		}

		public final Object getStaticObject() {
			return staticObject;
		}

		public final long getOffSet() {
			return offSet;
		}

		public final int getMethodAccessIndex() {
			return MethodAccessIndex;
		}

	}

}
