package hx.codeReviewer.util;

import java.util.HashSet;
import java.util.Set;

import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSRecord;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 */
public class NSFieldUtil {
	/**
	 * @since 1.1
	 * 
	 *        The value of sub type when a NSField is set to Object.
	 */
	public static final int JAVA_TYPE_UNKNOWN = 0;
	public static final int JAVA_TYPE_BOOLEAN = 1;
	public static final int JAVA_TYPE_BYTE = 2;
	public static final int JAVA_TYPE_CHARACTER = 3;
	public static final int JAVA_TYPE_DOUBLE = 4;
	public static final int JAVA_TYPE_FLOAT = 5;
	public static final int JAVA_TYPE_INTEGER = 6;
	public static final int JAVA_TYPE_LONG = 7;
	public static final int JAVA_TYPE_SHORT = 8;
	public static final int JAVA_TYPE_DATE = 9;
	public static final int JAVA_TYPE_BYTES = 10;
	public static final int JAVA_TYPE_BIGDECIMAL = 11;
	public static final int JAVA_TYPE_BIGINTEGER = 12;
	public static final int JAVA_TYPE_XOPOBJECT = 13;

	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param nsRecord
	 *            The NSRecord to look up duplicate field.
	 * @param recursive
	 *            Whether to look up in child NSRecord.
	 * @return Returns true if duplicate field is found, otherwise returns
	 *         false.
	 */
	public static boolean isDuplicateFieldFound(NSRecord nsRecord,
			boolean recursive) {
		if (nsRecord != null) {
			Set<String> fieldNames = new HashSet<String>();
			for (int i = 0; i < nsRecord.getFieldCount(); i++) {
				NSField nsField = nsRecord.getField(i);
				String fieldName = nsField.getName();
				if (fieldNames.contains(fieldName)) {
					return true;
				} else {
					if (recursive
							&& nsField.getType() == NSField.FIELD_RECORD
							&& isDuplicateFieldFound((NSRecord) nsField,
									recursive)) {
						return true;
					}
					fieldNames.add(fieldName);
				}
			}
		}
		return false;
	}

}
