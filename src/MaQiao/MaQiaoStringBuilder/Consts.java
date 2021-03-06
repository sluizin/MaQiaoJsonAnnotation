package MaQiao.MaQiaoStringBuilder;
import MaQiao.Constants.Constants;
public final class Consts {
	static final char elementFill = '\0';/* 填充物：稻草、塑料、橡胶、硅胶 */
	/** UNSAFE 初始化前导空间(Byte) */
	static final long defHeadlen = 0L;
	/** UNSAFE 默认初始Char字符长度 同StringBuilder、StringBuffer同样的初始大小(16个字符)*/
	static final long defaultLen = 16L;
	static final int charArrayMaxLen = Integer.MAX_VALUE;/*输出到二维字符数组时，单行字符数组的最大长度*/
	/**
	 * null
	 */
	static final char[] nullArray = { 'n', 'u', 'l', 'l' };
	/**
	 * NULL
	 */
	static final char[] NULLArray = { 'N', 'U', 'L', 'L' };
	/**
	 * true
	 */
	static final char[] trueArray = { 't', 'r', 'u', 'e' };
	/**
	 * TRUE
	 */
	static final char[] TRUEArray = { 'T', 'R', 'U', 'E' };
	/**
	 * false
	 */
	static final char[] falseArray = { 'f', 'a', 'l', 's', 'e' };
	/**
	 * new char[0] 空数组，用于返回空值
	 */
	static final char[] ArrayNull = new char[0]; 
	/**
	 * new char[0][0] 空数组，用于返回空值
	 */
	static final char[][] Array2Null = new char[0][0];
	/**
	 * char[]数组地址偏移量
	 */
	static final long ArrayAddress = Constants.UNSAFE.arrayBaseOffset(char[].class);
	/**
	 * String对象中value(char[])地址偏移量
	 */
	static long StringArrayOffset = 0L;
	static {
		try {
			StringArrayOffset = Constants.UNSAFE.objectFieldOffset(String.class.getDeclaredField("value"));/*得到String对象中数组的偏移量*/
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 锁的状态:<br/>
	 * None(0):无锁<br/>
	 * Share(1):共享锁<br/>
	 * Monopoly(2):独占锁<br/>
	 * @author Sunjian
	 */
	static enum lockType {
		/**
		 * 无锁(0)
		 */
		None(0),
		/**
		 * 共享锁(1)
		 */
		Share(1),
		/**
		 * 独占锁(2)
		 */
		Monopoly(2);
		/**
		 * 0:无锁<br/>
		 * 1:共享锁<br/>
		 * 2:独占锁<br/>
		 */
		int value;

		// 构造方法
		private lockType(final int value) {
			this.value = value;
		}

		public static lockType getLockType(final int value) {
			for (lockType c : lockType.values())
				if (c.value == value) return c;
			return null;
		}
	}

	/**
	 * boolean的状态:<br/>
	 * False(0):假<br/>
	 * True(1):真<br/>
	 * @author Sunjian
	 */
	static enum booleanType {
		/**
		 * 假(0)
		 */
		False(0),
		/**
		 * 真(1)
		 */
		True(1);
		/**
		 * False:假(0)<br/>
		 * True:真(1)<br/>
		 */
		int value;

		// 构造方法
		private booleanType(final int value) {
			this.value = value;
		}

		public static final booleanType getBooleanType(final int value) {
			for (booleanType c : booleanType.values())
				if (c.value == value) return c;
			return null;
		}
	}
}
