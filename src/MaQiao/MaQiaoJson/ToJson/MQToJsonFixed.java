package MaQiao.MaQiaoJson.ToJson;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.security.AccessController;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
//import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;
import MaQiao.Constants.Constants;
import MaQiao.MaQiaoStringBuilder.MQSBuilder;
import static MaQiao.Constants.Constants.FieldTypeEnum;

/**
 * 对象生成Json串<br/>
 * 允许常用属性类型，自定义属性类型
 * @author SunJian(孙健)
 */
public final class MQToJsonFixed {
	public static final boolean JsonAttrBoolean = false;

	/**
	 * 外接对象
	 * @param object
	 * @return MQSBuilderFixed
	 */
	public static final MQSBuilder Object2Json(final Object object) {
		MQSBuilder sb = new MQSBuilder(7000);
		ObjectToJson(sb, object);
		//System.out.println(sb.Length()+"/"+sb.capacity());
		return sb;
	}

	/**
	 * 对象转成Json串，允许常用类型与自定义类型
	 * @param object
	 */
	private static final void ObjectToJson(final MQSBuilder sb, final Object object) {
		if (object.getClass().isArray()) {
			// 数组对象
			ArrayToJson(sb, object);
		} else {
			// 非数组对象
			final FieldTypeEnum FTE = ToFieldTypeEnum(object.getClass().getName());
			if (FTE != null) {
				// 本地可识别对象
				FieldToJson(sb, FTE, object);
			} else {
				// 自定义对象 (独立)
				ToJsonUser(sb, object);
			}
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	private static final void FieldToJson(final MQSBuilder sb, final FieldTypeEnum FTE, final Object fieldObject) {
		FieldToJson(sb, null, FTE, fieldObject);
	}

	@SuppressWarnings("unchecked")
	private static final <T> void FieldToJson(final MQSBuilder sb, final String FieldName, final FieldTypeEnum FTE, final Object fieldObject) {
		if (FieldName != null) {
			sb.append(Constants.JsonMark_10);
			if (JsonAttrBoolean) ToJsonAttribute(sb, FieldName, FTE.value().getFieldsClass().getName());
			sb.append(Constants.JsonMark_0);
			sb.append(FieldName);
			sb.append(Constants.JsonMark_0);
			sb.append(Constants.JsonMark_3);
		}
		// 常用对象
		switch (FTE.value().getJsonType()) {
		case 0:
			// 数值、真假
			if (FTE == FieldTypeEnum.Timestamp) {
				if (fieldObject == null) {
					sb.appendnull();
				} else {
					sb.append(((java.sql.Timestamp) fieldObject).getTime());
				}
			} else {
				sb.append(fieldObject);
			}
			break;
		case 1:
			// 字符串、日期串,字符(char)
			if (fieldObject == null) {
				sb.appendnull();
			} else {
				sb.append(Constants.JsonMark_0);
				//System.out.println("switch:" + fieldObject.toString());
				switch (FTE) {
				case String:
					sb.append(fieldObject.toString());
					break;
				case Char:
					sb.append((char)fieldObject);
					break;
				default: {
					final char[] ch = fieldObject.toString().toCharArray();
					for (int ii = 0; ii < ch.length; ii++) {
						switch (ch[ii]) {
						case Constants.JsonMark_0:
							sb.append(Constants.JsonMark_4);
							sb.append(Constants.JsonMark_0);
							break;
						case Constants.JsonMark_51:
							sb.append(Constants.JsonMark_4);
							sb.append('n');
							break;
						case Constants.JsonMark_52:
							sb.append(Constants.JsonMark_4);
							sb.append('t');
							break;
						case Constants.JsonMark_54:
							sb.append(Constants.JsonMark_4);
							sb.append('r');
							break;
						case Constants.JsonMark_4:
							sb.append(Constants.JsonMark_4);
							sb.append(Constants.JsonMark_4);
							break;
						case Constants.JsonMark_53:
							sb.append(Constants.JsonMark_4);
							sb.append('b');
							break;
						case Constants.JsonMark_55:
							sb.append(Constants.JsonMark_4);
							sb.append('f');
							break;
						default:
							sb.append(ch[ii]);
							break;
						}
					}
				}
				}
				sb.append(Constants.JsonMark_0);
			}
			break;
		case 2:
			switch (FTE) {
			case ListObject:
				List<T> readlist = (List<T>) fieldObject;
				ObjectToJson(sb, readlist);
				break;
			case SetObject:
				Set<Object> readSet = (Set<Object>) fieldObject;
				ObjectToJson(sb, readSet);
				break;
			case MapObject:
			case HashMapObject:
				Map<String, Object> readHashMap = (Map<String, Object>) fieldObject;
				ObjectToJson(sb, readHashMap);
				break;
			default:// BitSetObject:
				BitSet BitSet = (BitSet) fieldObject;
				ObjectToJson(sb, BitSet);
				break;
			}
			break;
		}
		if (FieldName != null) {
			sb.append(Constants.JsonMark_11);
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */

	/**
	 * 数组转Json
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	private static final void ArrayToJson(final MQSBuilder sb, final Object obj) {
		final int len = Array.getLength(obj);
		if (len > 0) {
			sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++) {
				ObjectToJson(sb, Array.get(obj, i));
				if (i < len - 1) {
					sb.append(Constants.JsonMark_2);
				}
			}
			sb.append(Constants.JsonMark_21);
		} else {
			sb.appendnull();
		}
	}

	/**
	 * Set< Object > objectSet对象转成Json
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	private static final void ObjectToJson(final MQSBuilder sb, final Set<Object> obj) {
		final int len = obj.size();
		if (len > 0) {
			sb.append(Constants.JsonMark_20);
			int i = 0;
			for (Iterator<Object> it = obj.iterator(); it.hasNext();) {
				ObjectToJson(sb, it.next());
				if ((i++) < len) sb.append(Constants.JsonMark_2);
			}
			sb.append(Constants.JsonMark_21);
			return;
		}
		sb.appendnull();
	}

	/**
	 * Map< String, Object > objectMap对象转成Json
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	@SuppressWarnings("rawtypes")
	private static final void ObjectToJson(final MQSBuilder sb, final Map<String, Object> obj) {
		final int len = obj.size();
		if (len > 0) {
			sb.append(Constants.JsonMark_20);
			int i = 0;
			Iterator<?> iterator = obj.entrySet().iterator();
			Map.Entry e = null;
			while (iterator.hasNext()) {
				e = (Map.Entry) iterator.next();
				sb.append(Constants.JsonMark_10);
				sb.append(Constants.JsonMark_0);
				sb.append(e.getKey());
				sb.append(Constants.JsonMark_0);
				sb.append(Constants.JsonMark_3);
				ObjectToJson(sb, e.getValue());
				sb.append(Constants.JsonMark_11);
				if ((i++) < (len - 1)) sb.append(Constants.JsonMark_2);
			}
			sb.append(Constants.JsonMark_21);
		} else {
			sb.appendnull();
		}
	}

	/**
	 * List< T > objectList对象转成Json
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	private static final <T> void ObjectToJson(final MQSBuilder sb, final List<T> obj) {
		final int len = obj.size();
		if (len > 0) {
			// List<T>提出
			final FieldTypeEnum FTE = ToFieldTypeEnum(obj.get(0).getClass().getName());
			if (FTE != null) {
				// 已知类型
				sb.append(Constants.JsonMark_20);
				for (int i = 0; i < len; i++) {
					FieldToJson(sb, FTE, obj.get(i));
					if (i < len - 1) sb.append(Constants.JsonMark_2);
				}
				sb.append(Constants.JsonMark_21);
			} else {
				// 自定义类型
				ObjectToJsonUser(sb, obj);
			}
		} else {
			sb.appendnull();
		}
	}

	/**
	 * BitSet objectList对象转成Json
	 * @param obj BitSet
	 * @return MQSBuilderFixed
	 */
	private static final void ObjectToJson(final MQSBuilder sb, final BitSet obj) {
		final int len = obj.length();
		if (len > 0) {
			sb.append(Constants.JsonMark_20);
			for (int i = 0; i < len; i++)
				if (obj.get(i)) {
					sb.append(i);
					if (i < len - 1) sb.append(Constants.JsonMark_2);
				}
			sb.append(Constants.JsonMark_21);
		} else {
			sb.appendnull();
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */

	/**
	 * 自定义对象的List提取
	 * @param obj
	 * @return MQSBuilderFixed
	 */
	private static final <T> void ObjectToJsonUser(final MQSBuilder sb, final List<T> obj) {
		final int len = obj.size();
		if (len > 0) {
			sb.append(Constants.JsonMark_20);
			final Field[] fields = obj.get(0).getClass().getDeclaredFields();
			final int fieldslen = fields.length;
			Field field;
			Object fieldObj;
			for (int i = 0, ii; i < len; i++) {
				sb.append(Constants.JsonMark_10);
				for (ii = 0; ii < fieldslen; ii++) {
					field = fields[ii];
					sb.append(Constants.JsonMark_0);
					sb.append(field.getName());
					sb.append(Constants.JsonMark_0);
					sb.append(Constants.JsonMark_3);
					final FieldTypeEnum FTE = ToFieldTypeEnum(field.getType().getName());
					fieldObj = getNativeFields(obj.get(i), field);
					if (FTE != null) {
						// 常用对象
						FieldToJson(sb, FTE, fieldObj);

					} else {
						// 自定义对象 单独对象
						ObjectToJson(sb, fieldObj);
					}
				}
				sb.append(Constants.JsonMark_11);
				if (i < len - 1) {
					sb.append(Constants.JsonMark_2);
				}
			}
			sb.append(Constants.JsonMark_21);
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
	 * @param fieldTypeStr
	 * @return Constants.FieldTypeEnum
	 */
	private static final FieldTypeEnum ToFieldTypeEnum(final String fieldTypeStr) {
		return FieldTypeEnum.getByReflectFields(fieldTypeStr);
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 自定义对象的各属性Set 组成Json
	 * @param set
	 * @return MQSBuilderFixed
	 */
	@SuppressWarnings("unused")
	private static final void SetToJson(final MQSBuilder sb, final Set<String> set) {
		int len = set.size();
		for (String set1 : set) {
			len--;
			sb.append(set1);
			if (len > 0) sb.append(Constants.JsonMark_2);
		}
	}

	/**
	 * 向类属性添加Json属性说明属性
	 * @param FieldsName String
	 * @param e String
	 * @return MQSBuilderFixed
	 */
	private static final void ToJsonAttribute(final MQSBuilder sb, final String FieldsName, final String e) {
		if (JsonAttrBoolean) {
			sb.append(Constants.JsonMark_0);
			sb.append(FieldsName);
			sb.append(Constants.JsonMark_0);
			sb.append(Constants.JsonMark_3);
			sb.append(Constants.JsonMark_0);
			sb.append(e);
			sb.append(Constants.JsonMark_0);
			sb.append(Constants.JsonMark_2);
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 自定义对象的提取
	 * @param obj
	 * @param class1
	 * @return MQSBuilderFixed
	 */
	private static final void ToJsonUser(final MQSBuilder sb, final Object obj) {
		final Field[] fields = obj.getClass().getDeclaredFields();
		final int len = fields.length;
		if (len > 0) {
			sb.append(Constants.JsonMark_10);
			for (int i = 0; i < len; i++) {
				final Field field = fields[i];
				final FieldTypeEnum FTE = ToFieldTypeEnum(field.getType().getName());
				Object fieldObject = getNativeFields(obj, field);
				if (FTE != null) {
					// 常用对象
					if (JsonAttrBoolean) ToJsonAttribute(sb, field.getName(), FTE.value().getFieldsClass().getName());
					sb.append(Constants.JsonMark_0);
					sb.append(field.getName());
					sb.append(Constants.JsonMark_0);
					sb.append(Constants.JsonMark_3);
					FieldToJson(sb, FTE, fieldObject);

				} else {
					// 自定义对象 单独对象
					if (fieldObject.getClass().isArray()) {
						// 数组对象
						sb.append(Constants.JsonMark_0);
						sb.append(field.getName());
						sb.append(Constants.JsonMark_0);
						sb.append(Constants.JsonMark_3);
						ArrayToJson(sb, fieldObject);
					} else {
						if (JsonAttrBoolean) ToJsonAttribute(sb, field.getName(), fieldObject.getClass().getName());
						sb.append(Constants.JsonMark_0);
						sb.append(field.getName());
						sb.append(Constants.JsonMark_0);
						sb.append(Constants.JsonMark_3);
						ToJsonUser(sb, fieldObject);
					}
				}
			}
			sb.append(Constants.JsonMark_11);
		} else {
			sb.appendnull();
		}
	}

	/*
	 * =============================================
	 * =============================================
	 * =============================================
	 */
	/**
	 * 通过Native反射得到Field Object对象的属性
	 * @param obj
	 * @param field
	 * @return Object
	 */
	private static final Object getNativeFields(final Object obj, final Field field) {
		return reflectionFactory.newFieldAccessor(field, false).get(obj);
	}

	static final ReflectionFactory reflectionFactory = AccessController.doPrivileged(new sun.reflect.ReflectionFactory.GetReflectionFactoryAction());

}
