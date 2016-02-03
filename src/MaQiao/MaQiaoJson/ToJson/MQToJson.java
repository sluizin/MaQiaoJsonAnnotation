package MaQiao.MaQiaoJson.ToJson;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import sun.misc.Unsafe;
import MaQiao.Constants.Constants;
import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset;
import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset.Bean;
import MaQiao.MaQiaoBeanFieldsOffset.MQBeanFieldsOffset.FieldsOffset;
import MaQiao.MaQiaoStringBuilder.MQSBuilder;
import static MaQiao.Constants.Constants.FieldTypeEnum;

/**
 * 对象序列化[serializers]
 * @version 1.1
 * @since 1.4
 * @author SunJian(孙健)
 */
public final class MQToJson {
	private static final Unsafe UNSAFE = Constants.UNSAFE;
	/**
	 * 是否输出属性的数据类型信息
	 */
	public static final boolean JsonAttrBoolean = false;
	/**
	 * 是否输出State属性的数据信息
	 */
	public static final boolean JsonStateBoolean = false;

	public static final void init(final Class<?> classzz) {
		Node.mqfo.get(classzz);
	}

	public static final void init(final Object obj) {
		Node.mqfo.get(obj);
	}

	/**
	 * 外接对象
	 * @param obj Object
	 * @return String
	 */
	public static final String toJsonString(final Object obj) {
		try (Node node = new Node(obj);) {
			ObjectToJson(node, obj);
			return node.sb.getString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 外接对象
	 * @param obj Object
	 * @return char[]
	 */
	public static final char[] toJsonCharArray(final Object obj) {
		try (Node node = new Node(obj);) {
			ObjectToJson(node, obj);
			return node.sb.getArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对象转成Json串，允许常用类型与自定义类型
	 * @param obj
	 */
	private static final void ObjectToJson(final Node node, final Object obj) {
		if (obj.getClass().isArray()) {
			// 数组对象
			ArrayToJson(node, obj);
		} else {
			// 非数组对象			
			final FieldTypeEnum FTE = ToFieldTypeEnum(obj.getClass());
			if (FTE != null) {
				// 本地可识别对象
				FieldToJson(node, FTE, obj);
			} else {
				// 自定义对象 (独立)
				ToJsonUser(node, obj);
			}
		}
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final void FieldToJson(final Node node, final FieldTypeEnum FTE, final Object fieldObject) {
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
				StringChange(node, (String) fieldObject);
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
	private static final void toJsonUserField(final Node node, final FieldsOffset fos, final Object obj) {
		final FieldTypeEnum FTE = fos.getfTE();
		if (fos.isArray()) {
			// 数组对象
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(fos.getFieldName());
			node.sb.append(Constants.JsonMark_0);
			node.sb.append(Constants.JsonMark_3);
			ArrayToJson(node, UNSAFE.getObject(obj, fos.getOffSet()));
		} else {
			if (FTE != null) {
				if (JsonAttrBoolean) ToJsonAttribute(node, fos.getFieldName(), FTE.value().getFieldsClass().getName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(fos.getFieldName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.JsonMark_3);
				ToFieldJsonFTE(node, fos, obj);

			} else {
				if (JsonAttrBoolean) ToJsonAttribute(node, fos.getFieldName(), FTE.value().getFieldsClass().getName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(fos.getFieldName());
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(Constants.JsonMark_3);
				ToJsonUser(node, UNSAFE.getObject(obj, fos.getOffSet()));
			}
		}
	}

	/**
	 * 自定义对象的提取
	 * @param node Node
	 * @param obj Object
	 */
	private static final void ToJsonUser(final Node node, final Object obj) {
		if (obj == null) {
			node.sb.appendnull();
			return;
		}
		final Bean bean = Node.mqfo.get(obj);
		final FieldsOffset[] FieldsOffsets = bean.getFieldsOffsets();
		int lenFOS = FieldsOffsets.length;
		if (JsonStateBoolean) lenFOS += bean.getFieldsStaticOffsets().length;
		if (lenFOS > 0) {
			node.sb.append(Constants.JsonMark_10);
			//int count = 0;
			boolean isFirst = true;
			for (int i = 0, len = FieldsOffsets.length; i < len; i++) {
				final FieldsOffset fos = FieldsOffsets[i];
				if (fos.isTransient()) continue;/*跳过transient属性*/
				if (!isFirst || (isFirst = (!isFirst))) node.sb.append(Constants.JsonMark_2);
				//if (count++ > 0) node.sb.append(Constants.JsonMark_2);
				toJsonUserField(node, fos, obj);
			}
			if (JsonStateBoolean) {
				final FieldsOffset[] FieldsStaticOffsets = bean.getFieldsStaticOffsets();
				for (int i = 0, len = FieldsStaticOffsets.length; i < len; i++) {
					final FieldsOffset fos = FieldsStaticOffsets[i];
					if (fos.isTransient()) continue;/*跳过transient属性*/
					if (!isFirst || (isFirst = (!isFirst))) node.sb.append(Constants.JsonMark_2);
					//if (count++ > 0) node.sb.append(Constants.JsonMark_2);
					toJsonUserField(node, fos, fos.getStaticFieldObject());
				}
			}
			node.sb.append(Constants.JsonMark_11);
		} else {
			node.sb.appendnull();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final void ToFieldJsonFTE(final Node node, final FieldsOffset fos, final Object obj) {
		if (obj == null) {
			node.sb.appendnull();
		} else {
			switch (fos.getfTE()) {
			case Int:
			case Integer:
				node.sb.append(UNSAFE.getInt(obj, fos.getOffSet()));
				break;
			case Boolean:
			case BooleanObject:
				node.sb.append(UNSAFE.getBoolean(obj, fos.getOffSet()));
				break;
			case Float:
			case FloatObject:
				node.sb.append(UNSAFE.getFloat(obj, fos.getOffSet()));
				break;
			case Long:
			case LongObject:
				node.sb.append(UNSAFE.getLong(obj, fos.getOffSet()));
				break;
			case Double:
			case DoubleObject:
				node.sb.append(UNSAFE.getDouble(obj, fos.getOffSet()));
				break;
			case Byte:
			case ByteObject:
				node.sb.append(UNSAFE.getByte(obj, fos.getOffSet()));
				break;
			case Short:
			case ShortObject:
				node.sb.append(UNSAFE.getShort(obj, fos.getOffSet()));
				break;
			case Timestamp:
				Timestamp timestamp;
				if ((timestamp = (java.sql.Timestamp) UNSAFE.getObject(obj, fos.getOffSet())) == null) {
					node.sb.appendnull();
					break;
				}
				node.sb.append(timestamp.getTime());
				break;
			case Char:
				node.sb.append(Constants.JsonMark_0);
				node.sb.append(UNSAFE.getChar(obj, fos.getOffSet()));
				node.sb.append(Constants.JsonMark_0);
				break;
			case String:
				Object objstring;
				if ((objstring = UNSAFE.getObject(obj, fos.getOffSet())) == null) {
					node.sb.appendnull();
					break;
				}
				node.sb.append(Constants.JsonMark_0);
				StringChange(node, (String) objstring);
				node.sb.append(Constants.JsonMark_0);
				break;
			case ListObject:
				multiToJson(node, (List) UNSAFE.getObject(obj, fos.getOffSet()));
				break;
			case SetObject:
				multiToJson(node, (Set) UNSAFE.getObject(obj, fos.getOffSet()));
				break;
			case MapObject:
			case HashMapObject:
				multiToJson(node, (Map) UNSAFE.getObject(obj, fos.getOffSet()));
				break;
			case BitSetObject:
				multiToJson(node, (BitSet) UNSAFE.getObject(obj, fos.getOffSet()));
				break;
			default://Object
				node.sb.appendnull();
				break;
			}
		}
	}

	/**
	 * Set< Object > objectSet对象转成Json
	 * @param obj
	 * @return MQSBuilderFixed
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
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	@SuppressWarnings("rawtypes")
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
	 * @param obj
	 * @return MQSBuilderFixed
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
			final FieldTypeEnum FTE = ToFieldTypeEnum(obj.get(0).getClass());
			for (int i = 0; i < len; i++) {
				if (FTE != null) {
					// 已知类型
					FieldToJson(node, FTE, obj.get(i));
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
	 * @param obj BitSet
	 * @return MQSBuilderFixed
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

	private static final void StringChange(final Node node, final String str) {
		char c;
		final Object obj = UNSAFE.getObject(str, Constants.StringArrayOffset);
		for (int i = 0, len = str.length(); i < len; i++) {
			switch (c = UNSAFE.getChar(obj, Constants.ArrayAddress + (i << 1))) {
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
	 * 按fieldTypeString(java.util.Set)字符中得到枚举<br/>
	 * 如果为空(Null)，则为用户自定义类
	 * @param FieldsClass Class<?>
	 * @return Constants.FieldTypeEnum
	 */
	private static final FieldTypeEnum ToFieldTypeEnum(final Class<?> FieldsClass) {
		return FieldTypeEnum.getByFieldsClass(FieldsClass);
	}

	/**
	 * 向类属性添加Json属性说明属性
	 * @param FieldsName String
	 * @param e String
	 * @return MQSBuilderFixed
	 */
	private static final void ToJsonAttribute(final Node node, final String FieldsName, final String e) {
		if (JsonAttrBoolean) {
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
		static final MQBeanFieldsOffset mqfo = new MQBeanFieldsOffset();
		MQSBuilder sb = new MQSBuilder();
		int deepLevel = 3;

		public Node() {

		}

		public Node(final Object obj) {
			mqfo.get(obj.getClass());
		}

		@Override
		public void close() throws Exception {
			sb.close();
		}
	}
}
