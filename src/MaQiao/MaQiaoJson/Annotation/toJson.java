package MaQiao.MaQiaoJson.Annotation;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import MaQiao.Constants.Constants;
import static MaQiao.MaQiaoJson.Annotation.annotationCache.AnnoBean;
//import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset;
//import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset.Bean;
//import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset.FieldsOffset;
//import MaQiao.MaQiaoIdentityHashMap.MQIdentityHashMap;
import MaQiao.MaQiaoJson.Annotation.asmMQAnnotation.MQvisit;
import MaQiao.MaQiaoStringBuilder.MQSBuilder;
import static MaQiao.Constants.Constants.FieldTypeEnum;

/**
 * 对象序列化[serializers]<br/>
 * 注意：JsonCheckBitBoolean 是否使用位进行判断
 * @version 1.1
 * @since 1.4
 * @author SunJian(孙健)
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public final class toJson {

	public static final void init(final Class<?> classzz) {
		CacheStaticAnnotation.MQJAnnoCache.get(classzz);
	}

	public static final void init(final Object obj) {
		CacheStaticAnnotation.MQJAnnoCache.get(obj);
	}

	/**
	 * 外接对象 按默认{0}得到Json
	 * @param obj Object
	 * @return String
	 */
	public static final String toJsonString(final Object obj) {
		return toJsonString(obj, new int[] { 0 });
	}

	/**
	 * 外接对象 按组得到Json
	 * @param obj Object
	 * @param Q int[]
	 * @return String
	 */
	public static final String toJsonString(final Object obj, final int... Q) {
		try (Node node = new Node(obj, Q);) {
			ObjectToJson(node, obj);
			return node.sb.getString();
		} catch (Exception e) {
			e.printStackTrace();
			return new String("");
		}
	}

	/**
	 * 外接对象 按默认{0}得到Json
	 * @param obj Object
	 * @return char[]
	 */
	public static final char[] toJsonCharArray(final Object obj) {
		return toJsonCharArray(obj, new int[] { 0 });
	}

	/**
	 * 外接对象 按组得到Json
	 * @param obj Object
	 * @param Q int[]
	 * @return char[]
	 */
	public static final char[] toJsonCharArray(final Object obj, final int... Q) {
		try (Node node = new Node(obj, Q);) {
			ObjectToJson(node, obj);
			return node.sb.getArray();
		} catch (Exception e) {
			e.printStackTrace();
			return new char[0];
		}
	}

	/**
	 * 对象转成Json串，允许常用类型与自定义类型
	 * @param obj
	 */
	private static final void ObjectToJson(final Node node, final Object obj) {
		// 自定义对象 (独立)
		getJsonUser(node, obj);
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 独立的属性
	 * @param node
	 * @param FTE
	 * @param fieldObject
	 */
	private static final void getJsonMQvisitField(final Node node, final FieldTypeEnum FTE, final Object fieldObject) {
		if (fieldObject == null) {
			node.sb.appendnull();
		} else {
			switch (FTE) {
			case Int:
			case Integer:
				node.sb.append((int) fieldObject);
				break;
			case Boolean:
			case BooleanObject:
				node.sb.append((boolean) fieldObject);
				break;
			case Float:
			case FloatObject:
				node.sb.append((float) fieldObject);
				break;
			case Long:
			case LongObject:
				node.sb.append((long) fieldObject);
				break;
			case Double:
			case DoubleObject:
				node.sb.append((double) fieldObject);
				break;
			case Byte:
			case ByteObject:
				node.sb.append((byte) fieldObject);
				break;
			case Short:
			case ShortObject:
				node.sb.append((short) fieldObject);
				break;
			case Timestamp:
				Timestamp timestamp = (java.sql.Timestamp) fieldObject;
				node.sb.append(timestamp.getTime());
				break;
			case Char:
				node.sb.append(Constants.JsonMark_0);
				node.sb.append((char) fieldObject);
				node.sb.append(Constants.JsonMark_0);
				break;
			case String:
				node.sb.append(Constants.JsonMark_0);
				StringChangeInput(node, (String) fieldObject);
				node.sb.append(Constants.JsonMark_0);
				break;
			case ListObject:
				multiToJson(node, (List) fieldObject);
				break;
			case SetObject:
				multiToJson(node, (Set) fieldObject);
				break;
			case MapObject:
			case HashMapObject:
				multiToJson(node, (Map) fieldObject);
				break;
			case BitSetObject:
				multiToJson(node, (BitSet) fieldObject);
				break;
			default://Object:
				node.sb.appendnull();
				break;
			}
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */

	/**
	 * 数组转Json
	 * @param node Node
	 * @param obj Object
	 */
	private static final void ArrayToJson(final Node node, final Object obj) {
		final int len = Array.getLength(obj);
		if (len > 0) {
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				ObjectToJson(node, Array.get(obj, i));
				if (i < len - 1) node.sb.append(Constants.JsonMark_2);
			}
			node.sb.append(Constants.JsonMark_21);
		} else {
			node.sb.appendnull();
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 过期方法
	 * @param node Node
	 * @param mQvisit MQvisit
	 * @param obj Object
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static final void toJsonUserMQvisitA(final Node node, final MQvisit mQvisit, final Object obj) {
		final FieldTypeEnum FTE = mQvisit.getReturnFTE();
		if (mQvisit.isReturnIsArray()) {
			// 数组对象
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(mQvisit.getRealName());
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(Constants.JsonMark_3);
			ArrayToJson(node, Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
		} else {
			if (FTE == null) {
				if (Consts.JsonAttrBoolean) ToJsonAttribute(node, mQvisit.getRealName(), "ErrorClass");
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(mQvisit.getRealName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.JsonMark_3);
				getJsonUser(node, Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
			} else {
				if (Consts.JsonAttrBoolean) ToJsonAttribute(node, mQvisit.getRealName(), FTE.value().getFieldsClass().getName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(mQvisit.getRealName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.JsonMark_3);
				ToFieldJsonFTE(node, mQvisit, obj);

			}
		}
	}

	/**
	 * 对MQvisit进行分析，是否是数组结果集、属性/方法、
	 * @param node Node
	 * @param mQvisit MQvisit
	 */
	private static final void toJsonUserMQvisit(final Node node, final AnnoBean annoBean,final int i, final Object obj) {
		final MQvisit mQvisit = annoBean.MQvisits[i];
		if (Consts.JsonAttrBoolean) if (mQvisit.returnFTE != null) ToJsonAttribute(node, mQvisit.getRealName(), mQvisit.returnFTE.value().getJavaRsType());
		node.sb.append(Constants.JsonMark_0);
		node.sb.append(mQvisit.getRealName());
		node.sb.append(Constants.JsonMark_0);
		node.sb.append(Constants.JsonMark_3);
		Object getObj = null;
		switch (mQvisit.getType()) {
		case 0:
			/* 属性 */
			getObj = toJsonUserMQvisitField(mQvisit, obj);
			break;
		case 1:
			/* 方法 */
			getObj = toJsonUserMQvisitMethod(annoBean,mQvisit, obj);
			break;
		default:
		}
		if (getObj == null) {
			node.sb.appendnull();
			return;
		}
		//if (mQvisit.isReturnIsArray()) node.sb.append(Constants.JsonMark_20);

		if (mQvisit.isReturnIsArray()) {
			toJsonUserMQvisitArray(node, mQvisit, getObj);
		} else {
			toJsonUserMQvisitArrayNot(node, mQvisit, getObj);
		}
		//if (mQvisit.isReturnIsArray()) node.sb.append(Constants.JsonMark_21);
	}

	/**
	 * 尝试传递泛型数组
	 * @param node Node
	 * @param mQvisit MQvisit
	 * @param getObj Object
	 * @param T Class< E >
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static final <E> void toJsonUserMQvisitArraySwitch(final Node node, final MQvisit mQvisit, final Object getObj, final Class<E> T) {
		if (getObj == null) {
			node.sb.appendnull();
			return;
		}
		final E[] Array = (E[]) getObj;
		//Class<? extends Class[]>[] Arraa= (Class<? extends Class[]>[])getObj;
		int len;
		if ((len = Array.length) == 0) {
			node.sb.appendnull();
			return;
		}
		node.sb.append(Constants.JsonMark_20);
		for (int i = 0; i < len; i++) {
			if (i > 0) node.sb.append(Constants.JsonMark_2);
			if (mQvisit.getReturnFTE().value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
			node.sb.append(Array[i]);
			if (mQvisit.getReturnFTE().value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
		}
		node.sb.append(Constants.JsonMark_21);
	}

	/**
	 * MQvisit 数组
	 * @param node Node
	 * @param mQvisit MQvisit
	 * @param getObj Object
	 */
	private static final void toJsonUserMQvisitArray(final Node node, final MQvisit mQvisit, final Object getObj) {
		int len;
		final FieldTypeEnum FTE = mQvisit.getReturnFTE();
		switch (FTE) {
		case Int: {
			final int[] Array = (int[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Integer: {
			final Integer[] Array = (Integer[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Boolean: {
			final boolean[] Array = (boolean[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case BooleanObject: {
			final Boolean[] Array = (Boolean[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Float: {
			final float[] Array = (float[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case FloatObject: {
			final Float[] Array = (Float[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Long: {
			final long[] Array = (long[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case LongObject: {
			final Long[] Array = (Long[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Double: {
			final double[] Array = (double[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case DoubleObject: {
			final Double[] Array = (Double[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Byte: {
			final byte[] Array = (byte[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
		case ByteObject: {
			final Byte[] Array = (Byte[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Short: {
			final short[] Array = (short[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case ShortObject: {
			final Short[] Array = (Short[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i]);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Timestamp: {
			final java.sql.Timestamp[] Array = (java.sql.Timestamp[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				node.sb.append(Array[i].getTime());
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Char: {
			final char[] Array = (char[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
				node.sb.append(Array[i]);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case Character: {
			final Character[] Array = (Character[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
				node.sb.append(Array[i]);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		case String: {
			final String[] Array = (String[]) getObj;
			if ((len = Array.length) == 0) {
				node.sb.appendnull();
				return;
			}
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				if (i > 0) node.sb.append(Constants.JsonMark_2);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
				StringChangeInput(node, Array[i]);
				if (FTE.value().getJsonType() == 1) node.sb.append(Constants.JsonMark_0);
			}
			node.sb.append(Constants.JsonMark_21);
		}
			break;
		default:
			node.sb.appendnull();
			break;
		}
	}

	/**
	 * MQvisit 非数组
	 * @param node Node
	 * @param mQvisit MQvisit
	 * @param obj Object
	 */
	private static final void toJsonUserMQvisitArrayNot(final Node node, final MQvisit mQvisit, final Object obj) {
		switch (mQvisit.getReturnFTE()) {
		case Int:
		case Integer:
			int newObjint = (int) obj;
			node.sb.append(newObjint);
			break;
		case Boolean:
		case BooleanObject:
			node.sb.append((boolean) obj);
			break;
		case Float:
			node.sb.append((float) obj);
			break;
		case FloatObject:
			node.sb.append((float) obj);
			break;
		case Long:
		case LongObject:
			node.sb.append((long) obj);
			break;
		case Double:
		case DoubleObject:
			node.sb.append((double) obj);
			break;
		case Byte:
		case ByteObject:
			node.sb.append(Constants.JsonMark_0);
			node.sb.append((byte) obj);
			node.sb.append(Constants.JsonMark_0);
			break;
		case Short:
			node.sb.append((short) obj);
		case ShortObject:
			node.sb.append((Short) obj);
			break;
		case Timestamp:
			node.sb.append(((java.sql.Timestamp) obj).getTime());
			break;
		case Char:
			node.sb.append(Constants.JsonMark_0);
			node.sb.append((char) obj);
			node.sb.append(Constants.JsonMark_0);
			break;
		case Character:
			node.sb.append(Constants.JsonMark_0);
			node.sb.append((Character) obj);
			node.sb.append(Constants.JsonMark_0);
			break;
		case String:
			node.sb.append(Constants.JsonMark_0);
			StringChangeInput(node, (String) obj);
			node.sb.append(Constants.JsonMark_0);
			break;
		case ListObject:
			multiToJson(node, (List) obj);
			break;
		case SetObject:
			multiToJson(node, (Set) obj);
			break;
		case MapObject:
		case HashMapObject:
			multiToJson(node, (Map) obj);
			break;
		case BitSetObject:
			multiToJson(node, (BitSet) obj);
			break;
		default://Object:
			node.sb.appendnull();
			break;
		}
	}

	private static final Object toJsonUserMQvisitField(final MQvisit mQvisit, final Object obj) {
		if (mQvisit.isStatic()) {
			return Constants.getUNSAFEObject(mQvisit.getStaticObject(), mQvisit.getOffSet(), mQvisit.getReturnFTE(), mQvisit.isReturnIsArray());
		} else {
			return Constants.getUNSAFEObject(obj, mQvisit.getOffSet(), mQvisit.getReturnFTE(), mQvisit.isReturnIsArray());
			//return Constants.UNSAFE.getObject(obj, mQvisit.getOffSet());
		}
	}

	private static final Object toJsonUserMQvisitMethod(final AnnoBean annoBean,final MQvisit mQvisit, final Object obj) {
		//System.out.println("mQvisit:"+mQvisit.toString());
		if (mQvisit.isStatic()) {
			return util.getObject(annoBean,mQvisit);
		} else {
			if (mQvisit.getMethodAccessIndex() >= 0) return CacheStaticAnnotation.MQJAnnoCache.get(obj).getAccess().invoke(obj, mQvisit.getMethodAccessIndex());
		}
		return null;
	}

	/**
	 * 自定义对象的提取
	 * @param node Node
	 * @param obj Object
	 */
	private static final void getJsonUser(final Node node, final Object obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final AnnoBean annoBean = CacheStaticAnnotation.MQJAnnoCache.get(obj);
		/*		if (Consts.JsonCheckBitBoolean && (annoBean.getAnnoValuesCollect() & node.Qbit) == 0L) {
					node.sb.appendnull();
					return;
				}*/
		final MQvisit[] MQvisits = annoBean.getMQvisits();
		final int len = MQvisits.length;
		if (len > 0) {
			node.sb.append(Constants.JsonMark_10);
			boolean isFirst = true;
			for (int i = 0; i < len; i++) {
				final MQvisit mQvisit = MQvisits[i];
				if(!checkQ(mQvisit, node))continue;
				if(mQvisit.isStatic())if(!Consts.JsonStateBoolean)continue;
				//if (!Consts.JsonStateBoolean  && (mQvisit.isStatic() || !checkQ(mQvisit, node))) continue;
				if (!isFirst || (isFirst = (!isFirst))) node.sb.append(Constants.JsonMark_2);
				//if(mQvisit.isStatic())
				//System.out.println("static:"+mQvisit.Name);
				toJsonUserMQvisit(node,annoBean,i , obj);
			}
			/*
								if (JsonStateBoolean) lenFOS += bean.getFieldsStaticOffsets().length;
								if (lenFOS > 0) {
									node.sb.append(Constants.JsonMark_10);
									//int count = 0;
									boolean isFirst = true;
									for (int i = 0, len = FieldsOffsets.length; i < len; i++) {
										final FieldsOffset fos = FieldsOffsets[i];
										if (fos.isTransient()) continue;跳过transient属性
										if (!isFirst || (isFirst = (!isFirst))) node.sb.append(Constants.JsonMark_2);
										//if (count++ > 0) node.sb.append(Constants.JsonMark_2);
										toJsonUserMQvisit(node, fos, obj);
									}
									if (JsonStateBoolean) {
										final FieldsOffset[] FieldsStaticOffsets = bean.getFieldsStaticOffsets();
										for (int i = 0, len = FieldsStaticOffsets.length; i < len; i++) {
											final FieldsOffset fos = FieldsStaticOffsets[i];
											if (fos.isTransient()) continue;跳过transient属性
											if (!isFirst || (isFirst = (!isFirst))) node.sb.append(Constants.JsonMark_2);
											//if (count++ > 0) node.sb.append(Constants.JsonMark_2);
											toJsonUserMQvisit(node, fos, fos.getStaticFieldObject());
										}
						}*/

			node.sb.append(Constants.JsonMark_11);
		} else {
			node.sb.appendnull();
		}
	}

	private static final void ToFieldJsonFTE(final Node node, final MQvisit mQvisit, final Object obj) {
		if (obj == null) {
			node.sb.appendnull();
		} else {
			switch (mQvisit.getReturnFTE()) {
			case Int:
			case Integer:
				node.sb.append(Constants.UNSAFE.getInt(obj, mQvisit.getOffSet()));
				break;
			case Boolean:
			case BooleanObject:
				node.sb.append(Constants.UNSAFE.getBoolean(obj, mQvisit.getOffSet()));
				break;
			case Float:
			case FloatObject:
				node.sb.append(Constants.UNSAFE.getFloat(obj, mQvisit.getOffSet()));
				break;
			case Long:
			case LongObject:
				node.sb.append(Constants.UNSAFE.getLong(obj, mQvisit.getOffSet()));
				break;
			case Double:
			case DoubleObject:
				node.sb.append(Constants.UNSAFE.getDouble(obj, mQvisit.getOffSet()));
				break;
			case Byte:
			case ByteObject:
				node.sb.append(Constants.UNSAFE.getByte(obj, mQvisit.getOffSet()));
				break;
			case Short:
			case ShortObject:
				node.sb.append(Constants.UNSAFE.getShort(obj, mQvisit.getOffSet()));
				break;
			case Timestamp:
				Timestamp timestamp;
				if ((timestamp = (java.sql.Timestamp) Constants.UNSAFE.getObject(obj, mQvisit.getOffSet())) == null) {
					node.sb.appendnull();
					break;
				}
				node.sb.append(timestamp.getTime());
				break;
			case Char:
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.UNSAFE.getChar(obj, mQvisit.getOffSet()));
				node.sb.append(Constants.JsonMark_0);
				break;
			case String:
				Object objstring;
				if ((objstring = Constants.UNSAFE.getObject(obj, mQvisit.getOffSet())) == null) {
					node.sb.appendnull();
					break;
				}
				node.sb.append(Constants.JsonMark_0);
				StringChangeInput(node, (String) objstring);
				node.sb.append(Constants.JsonMark_0);
				break;
			case ListObject:
				multiToJson(node, (List) Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
				break;
			case SetObject:
				multiToJson(node, (Set) Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
				break;
			case MapObject:
			case HashMapObject:
				multiToJson(node, (Map) Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
				break;
			case BitSetObject:
				multiToJson(node, (BitSet) Constants.UNSAFE.getObject(obj, mQvisit.getOffSet()));
				break;
			default://Object
				node.sb.appendnull();
				break;
			}
		}
	}

	/**
	 * Set< Object > objectSet对象转成Json
	 * @param node Node
	 * @param obj Set< Object >
	 */
	private static final void multiToJson(final Node node, final Set<Object> obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final int len = obj.size();
		if (len > 0) {
			node.sb.append(Constants.JsonMark_20);
			int i = 0;
			for (Iterator<Object> it = obj.iterator(); it.hasNext();) {
				ObjectToJson(node, it.next());
				if ((i++) < len) node.sb.append(Constants.JsonMark_2);
			}
			node.sb.append(Constants.JsonMark_21);
			return;
		}
		node.sb.appendnull();
	}

	/**
	 * Map< String, Object > objectMap对象转成Json
	 * @param node Node
	 * @param obj Map< String, Object >
	 */
	private static final void multiToJson(final Node node, final Map<String, Object> obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final int len = obj.size();
		if (len > 0) {
			node.sb.append(Constants.JsonMark_20);
			int i = 0;
			Iterator<?> iterator = obj.entrySet().iterator();
			Map.Entry e = null;
			while (iterator.hasNext()) {
				e = (Map.Entry) iterator.next();
				node.sb.append(Constants.JsonMark_10);
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(e.getKey());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.JsonMark_3);
				ObjectToJson(node, e.getValue());
				node.sb.append(Constants.JsonMark_11);
				if ((i++) < (len - 1)) node.sb.append(Constants.JsonMark_2);
			}
			node.sb.append(Constants.JsonMark_21);
		} else {
			node.sb.appendnull();
		}
	}

	/**
	 * List< T > objectList对象转成Json
	 * @param obj List< T >
	 */
	private static final <T> void multiToJson(final Node node, final List<T> obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final int len = obj.size();
		if (len > 0) {
			// List<T>提出
			node.sb.append(Constants.JsonMark_20);
			final FieldTypeEnum FTE = FieldTypeEnum.getByFieldsClass(obj.get(0).getClass());
			for (int i = 0; i < len; i++) {
				if (FTE != null) {
					// 已知类型
					getJsonMQvisitField(node, FTE, obj.get(i));
				} else {
					// 自定义类型
					//ObjectToJsonUser(node, obj);
					ObjectToJson(node, obj.get(i));
				}
				if (i < len - 1) node.sb.append(Constants.JsonMark_2);
			}
			node.sb.append(Constants.JsonMark_21);

		} else {
			node.sb.appendnull();
		}
	}

	/**
	 * BitSet objectList对象转成Json
	 * @param node Node
	 * @param obj BitSet
	 */
	private static final void multiToJson(final Node node, final BitSet obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final int len = obj.length();
		if (len > 0) {
			node.sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++)
				if (obj.get(i)) {
					node.sb.append(i);
					if (i < len - 1) node.sb.append(Constants.JsonMark_2);
				}
			node.sb.append(Constants.JsonMark_21);
		} else {
			node.sb.appendnull();
		}
	}

	/**
	 * 对String进行过滤，加入队列中<br/>
	 * @param node Node
	 * @param str String
	 */
	private static final void StringChangeInput(final Node node, final String str) {
		if (str == null || str.length() == 0) {
			node.sb.appendnull();
			return;
		}
		char c;
		final Object obj = Constants.UNSAFE.getObject(str, Constants.StringArrayOffset);
		for (int i = 0, len = str.length(); i < len; i++) {
			switch (c = Constants.UNSAFE.getChar(obj, Constants.ArrayAddress + (i << 1))) {
			case Constants.JsonMark_0:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append(Constants.JsonMark_0);
				break;
			case Constants.JsonMark_51:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append('n');
				break;
			case Constants.JsonMark_52:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append('t');
				break;
			case Constants.JsonMark_54:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append('r');
				break;
			case Constants.JsonMark_4:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append(Constants.JsonMark_4);
				break;
			case Constants.JsonMark_53:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append('b');
				break;
			case Constants.JsonMark_55:
				node.sb.append(Constants.JsonMark_4);
				node.sb.append('f');
				break;
			default:
				node.sb.append(c);
				break;
			}
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */

	/**
	 * 向类属性添加Json属性说明属性
	 * @param node Node
	 * @param FieldsName String
	 * @param e String
	 */
	private static final void ToJsonAttribute(final Node node, final String FieldsName, final String e) {
		if (Consts.JsonAttrBoolean) {
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(FieldsName);
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(Constants.JsonMark_3);
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(e);
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(Constants.JsonMark_2);
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 使用AutoCloseable接口，放入try资源定义
	 * @author Sunjian
	 * @since 1.7
	 */
	public final static class Node implements AutoCloseable {
		final MQSBuilder sb = new MQSBuilder();
		int[] Q = { 0 };
		long Qbit = 0L;
		int deepLevel = 3;

		public Node() {

		}

		public Node(final Object obj, final int... Q) {
			this.Qbit = util.arrayToBitLong(this.Q = Q);
			CacheStaticAnnotation.MQJAnnoCache.get(new asmMQAnnotation(obj));
		}

		@Override
		public void close() throws Exception {
			sb.close();
		}
	}

	/**
	 * 判断输入Q(int[])是否在 MQvisit.MQAnnotationGroup中
	 * @param mQvisit MQvisit
	 * @param node Node
	 * @return boolean
	 */
	private static final boolean checkQ(final MQvisit mQvisit, final Node node) {
		if (node == null || node.Q == null || node.Q.length == 0) return false;
		/* 按位进行判断 */
		if (Consts.JsonCheckBitBoolean) if ((mQvisit.getMQAnnotationValuesLong() & node.Qbit) > 0L) return true;
		else return false;
		final int[] Group = mQvisit.getMQAnnotationQ();
		final int[] Q = node.Q;
		int lenMQ;
		if ((lenMQ = Group.length) == 0) return false;
		for (int i = 0, ii, len = Q.length; i < len; i++)
			for (ii = 0; ii < lenMQ; ii++)
				if (Q[i] == Group[ii]) return true;
		return false;
	}
}
