package MaQiao.MaQiaoJsonAnnotation;

import java.util.ArrayList;
import java.util.Arrays;
import com.esotericsoftware.reflectasm.MethodAccess;
import MaQiao.Constants.Constants;
import MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation.MQvisit;
import sun.misc.Unsafe;
import static MaQiao.MaQiaoJsonAnnotation.Consts.booleanType;

/**
 * 通过MaQiao.MaQiaoJsonAnnotation.asmMQAnnotation注解形式缓存 Class对象的各属性 类型 偏移地址记录<br/>
 * 线程安全
 * @version 1.1
 * @since 1.7
 * @author Sunjian
 */
@SuppressWarnings("unused")
public final class MQJsonAnnotationCache {
	private static final Unsafe UNSAFE = Constants.UNSAFE;
	private transient int locked = booleanType.False.index;
	transient volatile ArrayList<AnnoBean> beanList = new ArrayList<AnnoBean>();

	public MQJsonAnnotationCache() {
	}

	public MQJsonAnnotationCache(final Object obj) {
		get(obj.getClass());
	}

	public final AnnoBean get(final Object obj) {
		return get(obj.getClass());
	}

	public final AnnoBean get(final Class<?> classzz) {
		lock();
		try {
			int find;
			if ((find = indexOf(classzz)) != -1) return beanList.get(find);
			add(classzz);
			if ((find = indexOf(classzz)) != -1) return beanList.get(find);
			return null;
		} finally {
			unLock();
		}
	}

	/**
	 * 得到对象是否已经分析，如分析得到下标，没有则返回-1<br/>
	 * @param classzz Class< ? >
	 * @return int
	 */
	private final int indexOf(final Class<?> classzz) {
		for (int i = 0, identityHashCode = System.identityHashCode(classzz), len = beanList.size(); i < len; i++)
			if (beanList.get(i).identityHashCode == identityHashCode) return i;
		return -1;
	}

	private final void add(final Class<?> classzz) {
		for (int i = 0, identityHashCode = System.identityHashCode(classzz); i < beanList.size(); i++)
			if (beanList.get(i).classzz.equals(classzz) && beanList.get(i).identityHashCode != identityHashCode) beanList.remove(i--);
		final AnnoBean e = new AnnoBean(classzz);
		//e.MQvisitLinkedLists = (new asmMQAnnotation(classzz)).getMQjsonLinkedList();
		asmMQAnnotation asmM = new asmMQAnnotation(classzz);
		e.MQvisits = asmM.getMQjsonArray();
		e.annoValuesCollect = asmM.annoValuesCollect;
		for (int i = 0, len = e.MQvisits.length; i < len; i++) {
			if (e.MQvisits[i].type == 1) {
				e.access = MethodAccess.get(classzz);
				break;
			}
		}
		/* 按地址偏移量进行排序 */
		e.MQvisits = FieldsSort(e.MQvisits);
		beanList.add(e);
		asmM = null;

	}

	/**
	 * 在检索Fields时，发现外部对象，则添加这个对象信息
	 * @param classzz Class
	 */
	private final void insert(final Class<?> classzz) {
		for (int i = 0, identityHashCode = System.identityHashCode(classzz), len = beanList.size(); i < len; i++)
			if (beanList.get(i).identityHashCode == identityHashCode) return;
		add(classzz);
	}

	/**
	 * 在检索Fields时，发现外部对象数组，则添加这个对象信息
	 * @param classString String
	 */
	private final void insert(String classString) {
		if (classString == null) return;
		if (classString.indexOf('/') > 0) classString = classString.replace('/', '.');
		for (int i = 0, len = beanList.size(); i < len; i++)
			if (beanList.get(i).className.equals(classString)) return;
		try {
			add(Class.forName(classString));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final String toString() {
		if (beanList == null) return "";
		return "MQBeanFieldsOffset [beanList=" + beanList + "]";
	}

	public static final class AnnoBean {
		transient int identityHashCode = 0;
		transient Class<?> classzz = null;
		transient String className = null;
		transient MethodAccess access = null;
		/**
		 * 复杂Class类
		 */
		transient boolean Complex = false;
		/**
		 * 注解汇总
		 */
		transient long annoValuesCollect = 0L;
		transient MQvisit[] MQvisits = new MQvisit[0];

		public final int getIdentityHashCode() {
			return identityHashCode;
		}

		public final Class<?> getClasszz() {
			return classzz;
		}

		public final String getClassName() {
			return className;
		}

		public final boolean isComplex() {
			return Complex;
		}

		public final MQvisit[] getMQvisits() {
			return MQvisits;
		}

		public final MethodAccess getAccess() {
			return access;
		}

		public AnnoBean() {

		}

		public final long getAnnoValuesCollect() {
			return annoValuesCollect;
		}

		public AnnoBean(final Class<?> classzz) {
			this.identityHashCode = System.identityHashCode(classzz);
			this.classzz = classzz;
			this.className = classzz.getName();
		}

		@Override
		public String toString() {
			System.out.println("----------------------------属性 [" + className + "]------------------------------");
			for (int i = 0; i < MQvisits.length; i++) {
				System.out.println(MQvisits[i].toString());
				//if(fieldsOffsets[i].isStatic)System.out.println("----------------------------------------------------------");
			}
			System.out.println("---------------------------------------------------------------------------");
			StringBuilder builder = new StringBuilder();
			builder.append("Bean [identityHashCode=");
			builder.append(identityHashCode);
			builder.append(", classzz=");
			builder.append(classzz);
			builder.append(", className=");
			builder.append(className);
			builder.append(", access=");
			builder.append(access);
			builder.append(", Complex=");
			builder.append(Complex);
			builder.append(", MQvisits=");
			builder.append(Arrays.toString(MQvisits));
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * 锁定对象
	 */
	private final void lock() {
		lockedOffsetCAS(booleanType.False, booleanType.True);
	}

	/**
	 * 释放对象锁
	 */
	private final void unLock() {
		lockedOffsetCAS(booleanType.True, booleanType.False);
	}

	/**
	 * volatile int locked 主锁的CAS，循环设置锁<br/>
	 * @param from booleanType
	 * @param to booleanType
	 */
	private final void lockedOffsetCAS(final booleanType from, final booleanType to) {
		while (!UNSAFE.compareAndSwapInt(this, Consts.lockedOffset, from.index, to.index)) {
		}
	}

	/**
	 * 对MQvisit数组进行排序(按偏移量进行从小到大)
	 * @param fieldsOffsets MQvisit[]
	 * @return MQvisit[]
	 */
	private final static MQvisit[] FieldsSort(final MQvisit[] fieldsOffsets) {
		int len;
		if ((len = fieldsOffsets.length) <= 1) return fieldsOffsets;
		MQvisit it;
		for (int i = 0, ii; i < len - 1; i++)
			for (ii = i + 1; ii < len; ii++)
				if (fieldsOffsets[i].offSet > fieldsOffsets[ii].offSet) {
					it = fieldsOffsets[ii];
					fieldsOffsets[ii] = fieldsOffsets[i];
					fieldsOffsets[i] = it;
				}
		return fieldsOffsets;
	}
}