package MaQiao.Constants;

public final class FieldType {
	//JDBC RS Type
	private String javaRsType = "";
	//ASM Type
	private String asmType = "";
	//Json中 Value值状态，默认0(直接连接),1:加",2:集合[] 
	private int jsonType = 0;
	//ASM DefaultObject
	private Object asmDefaultObject = null;
	// Fields Type
	private String reflectFields = "";
	// Fields ClassType
	private Class<?> fieldsClass = null;
	//unsafe 类型元素 ARRAY_INDEX_SCALE偏移量
	private int ARRAY_INDEX_SCALE = 0;
	//可以通过unsafe的getXXXX提取
	private boolean isUNSAFEget = false;

	/**
	 * 构造函数
	 */
	public FieldType() {

	}

	/**
	 * 构造函数
	 * @param javaRsType java得到的recordSet数据类型
	 * @param asmType ASM数据类型
	 * @param jsonType Value值状态，默认0(直接连接),1:加",2:集合[]
	 * @param asmDefaultObject ASM数据类型初始化
	 * @param reflectFields 反射Fields所需要的类型(String)
	 * @param fieldsClass Fields所需要 Class<?> 类
	 * @param ARRAY_INDEX_SCALE unsafe 类型元素 数组偏移量
	 * @param isUNSAFEget 是否可以通过unsafe的getXXXX提取
	 */
	public FieldType(final String javaRsType, final String asmType, final int jsonType, final Object asmDefaultObject, final String reflectFields, final Class<?> fieldsClass,
			final int ARRAY_INDEX_SCALE, final boolean isUNSAFEget) {
		this.javaRsType = javaRsType;
		this.asmType = asmType;
		this.jsonType = jsonType;
		this.asmDefaultObject = asmDefaultObject;
		this.reflectFields = reflectFields;
		this.fieldsClass = fieldsClass;
		this.ARRAY_INDEX_SCALE = ARRAY_INDEX_SCALE;
		this.isUNSAFEget = isUNSAFEget;
	}

	public final String getJavaRsType() {
		return javaRsType;
	}

	public final void setJavaRsType(final String javaRsType) {
		this.javaRsType = javaRsType;
	}

	public final String getASMType() {
		return asmType;
	}

	public final void setASMType(final String asmType) {
		this.asmType = asmType;
	}

	/**
	 * Json中 Value值状态，默认0(直接连接),1:加",2:集合[]
	 * @return int
	 */
	public final int getJsonType() {
		return jsonType;
	}

	public final void setJsonType(final int jsonType) {
		this.jsonType = jsonType;
	}

	public final Object getASMDefaultObject() {
		return asmDefaultObject;
	}

	public final void setASMDefaultObject(final Object asmDefaultObject) {
		this.asmDefaultObject = asmDefaultObject;
	}

	public final String getReflectFields() {
		return reflectFields;
	}

	public final void setReflectFields(final String reflectFields) {
		this.reflectFields = reflectFields;
	}

	public final Class<?> getFieldsClass() {
		return fieldsClass;
	}

	public final void setFieldsClass(final Class<?> fieldsClass) {
		this.fieldsClass = fieldsClass;
	}

	public final int getARRAY_INDEX_SCALE() {
		return ARRAY_INDEX_SCALE;
	}

	public final void setARRAY_INDEX_SCALE(final int ARRAY_INDEX_SCALE) {
		this.ARRAY_INDEX_SCALE = ARRAY_INDEX_SCALE;
	}

	public final boolean isUNSAFEget() {
		return isUNSAFEget;
	}

	public final void setUNSAFEget(boolean isUNSAFEget) {
		this.isUNSAFEget = isUNSAFEget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ARRAY_INDEX_SCALE;
		result = prime * result + ((asmDefaultObject == null) ? 0 : asmDefaultObject.hashCode());
		result = prime * result + ((asmType == null) ? 0 : asmType.hashCode());
		result = prime * result + ((fieldsClass == null) ? 0 : fieldsClass.hashCode());
		result = prime * result + ((javaRsType == null) ? 0 : javaRsType.hashCode());
		result = prime * result + ((reflectFields == null) ? 0 : reflectFields.hashCode());
		result = prime * result + (isUNSAFEget ? 1231 : 1237);
		result = prime * result + jsonType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof FieldType)) return false;
		FieldType other = (FieldType) obj;
		if (ARRAY_INDEX_SCALE != other.ARRAY_INDEX_SCALE) return false;
		if (asmDefaultObject == null) {
			if (other.asmDefaultObject != null) return false;
		} else if (!asmDefaultObject.equals(other.asmDefaultObject)) return false;
		if (asmType == null) {
			if (other.asmType != null) return false;
		} else if (!asmType.equals(other.asmType)) return false;
		if (fieldsClass == null) {
			if (other.fieldsClass != null) return false;
		} else if (!fieldsClass.equals(other.fieldsClass)) return false;
		if (javaRsType == null) {
			if (other.javaRsType != null) return false;
		} else if (!javaRsType.equals(other.javaRsType)) return false;
		if (reflectFields == null) {
			if (other.reflectFields != null) return false;
		} else if (!reflectFields.equals(other.reflectFields)) return false;
		if (isUNSAFEget != other.isUNSAFEget) return false;
		if (jsonType != other.jsonType) return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldType [JavaRsType=" + javaRsType + ", ASMType=" + asmType + ", jsonType=" + jsonType + ", ASMDefaultObject=" + asmDefaultObject + ", ReflectFields=" + reflectFields
				+ ", FieldsClass=" + fieldsClass + ", ARRAY_INDEX_SCALE=" + ARRAY_INDEX_SCALE + ", isUNSAFEget=" + isUNSAFEget + "]";
	}

}
