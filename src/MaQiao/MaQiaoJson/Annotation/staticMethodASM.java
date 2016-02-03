package MaQiao.MaQiaoJson.Annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import MaQiao.Constants.Constants.FieldTypeEnum;

/**
 * 通过ASM得到静态方法的结果值<br/>
 * 通过asm建立空的类[有输出接口]，把静态方法通过对象return，通过接口实例化，得到结果<br/>
 * @author Sunjian
 * @since jdk1.7
 * @version 1.0
 */
public class staticMethodASM extends ClassLoader implements Opcodes {
	private String defaultclassName;/* = "sunj/project/newobject/BasicClass"; */
	private String[] implementsList = { "java/io/Serializable", Consts.ASMStaticMethodInterface };
	private long serialVersionUID = -5182532647273106745L;
	private final ClassWriter cw = new ClassWriter(0);
	private MethodVisitor mv;

	public staticMethodASM() {
	}

	/**
	 * 建立Bean，并构造方法
	 */
	public final void createBeanClass() {
		cw.visit(V1_7, ACC_PUBLIC, defaultclassName, null, "java/lang/Object", implementsList);
		// creates a MethodWriter for the (implicit) constructor
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		cw.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "serialVersionUID", "J", null, serialVersionUID).visitEnd();
	}

	/**
	 * 添加getObject()方法得到静态方法值 fromClassName: testMQ.testMQToJson.testAnnotation.testGetStaticMethod fromStaticMethodName staticFile
	 * @param fromClassName String<br/>
	 * @param fromStaticMethodName
	 */
	public final boolean addFieldFromStaticMethod(final FieldTypeEnum FTE, final String fromClassName, final String fromStaticMethodName) {
		mv = cw.visitMethod(ACC_PUBLIC, "getObject", "()Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		if (!addgetObjectMv(mv, FTE, fromClassName, fromStaticMethodName)) { return false; }
		//String fromClassName2= StringUtils.replaceChars(fromClassName, '/', '.');
		/*		try {
					final staticMethodASM loader = new staticMethodASM();
					loader.loadClass(fromClassName2, false);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}*/
		//mv.visitFieldInsn(GETSTATIC, fromClassName, fromStaticMethodName, "Ljava/lang/Object;");
		//mv.visitFieldInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()I");
		//mv.visitFieldInsn(INVOKESTATIC, "java/lang/Integer.valueOf", "(I)Ljava/lang/Integer;", "()I");
		//mv.visitFieldInsn(GETSTATIC, fromClassName, fromStaticMethodName+"()", "Ljava/lang/Object;");
		//mv.visitFieldInsn(INVOKESTATIC, fromClassName, "java/lang/Integer.valueOf", "(I)Ljava/lang/Integer;");
		//mv.visitMethodInsn(GETSTATIC, fromClassName2, fromStaticMethodName, "Ljava/lang/Object;");

		//mv.visitFieldInsn(GETSTATIC, fromClassName2, fromClassName+"."+fromStaticMethodName+"()", "Ljava/lang/Object;");
		//mv.visitInsn(ARETURN);

		//mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()I");
		//mv.visitMethodInsn(INVOKESTATIC,"java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");

		//mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/String;");

		//mv.visitInsn(Opcodes.LADD);

		//mv.visitInsn(loadAndReturnOf[1]);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(2, 1);
		mv.visitEnd();
		return true;
	}


	public final byte[] getByte() {
		return cw.toByteArray();
	}

	public final Class<?> getNewClass() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, InstantiationException {
		// 自定义加载器
		final staticMethodASM loader = new staticMethodASM();
		final Class<?> appClass = loader.defineClass(null, getByte(), 0, getByte().length);
		return appClass;
	}

	private final void changeClassName(final String NewdefaultclassName) {
		this.defaultclassName = StringUtils.replaceChars(NewdefaultclassName, '.', '/');
	}

	public final void setDefaultclassName(final String defaultclassName) {
		changeClassName(defaultclassName);
	}

	/**
	 * 设置ASM
	 * @param mv MethodVisitor
	 * @param FTE FieldTypeEnum
	 * @param fromClassName String
	 * @param fromStaticMethodName String
	 * @return boolean
	 */
	private static final boolean addgetObjectMv(final MethodVisitor mv, final FieldTypeEnum FTE, final String fromClassName, final String fromStaticMethodName) {
		if (FTE == null) return false;
		addgetObjectMvBase(mv, FTE, fromClassName, fromStaticMethodName);
		switch (FTE) {
		case Int:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			break;
		case Boolean:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
			break;
		case Long:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
			break;
		case Float:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
			break;
		case Double:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
			break;
		case Byte:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
			break;
		case Char:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
			break;
		case Short:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
			break;
		default://other:
		}
		return true;

	}

	/**
	 * 设置 mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()S");
	 * @param mv MethodVisitor
	 * @param FTE FieldTypeEnum
	 * @param fromClassName String
	 * @param fromStaticMethodName String
	 */
	private static final void addgetObjectMvBase(final MethodVisitor mv, final FieldTypeEnum FTE, final String fromClassName, final String fromStaticMethodName) {
		if (FTE != null) mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()" + FTE.value().getASMType());
	}

	@Deprecated
	public static final void getMv(final MethodVisitor mv, final FieldTypeEnum FTE, final String fromClassName, final String fromStaticMethodName) {
		switch (FTE) {
		case Integer:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Integer;");
			break;
		case BooleanObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Boolean;");
			break;
		case FloatObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Float;");
			break;
		case LongObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Long;");
			break;
		case DoubleObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Double;");
			break;
		case ByteObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Byte;");
			break;
		case ShortObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Short;");
			break;
		case Timestamp:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/sql/Timestamp;");
			break;
		case Character:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/Character;");
			break;
		case String:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/lang/String;");
			break;
		case ListObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/util/List;");
			break;
		case SetObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/util/List;");
			break;
		case MapObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/util/Map;");
			break;
		case HashMapObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/util/HashMap;");
			break;
		case BitSetObject:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Ljava/util/BitSet;");
			break;
		case Int:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()I");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			break;
		case Boolean:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()Z");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
			break;
		case Long:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()J");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
			break;
		case Float:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()F");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
			break;
		case Double:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()D");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
			break;
		case Byte:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()B");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
			break;
		case Char:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()C");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
			break;
		case Short:
			mv.visitMethodInsn(INVOKESTATIC, fromClassName, fromStaticMethodName, "()S");
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
			break;
		default://Object:
			break;
		}

	}
}
