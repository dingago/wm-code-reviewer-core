package hx.codeReviewer.util;

import java.util.HashSet;
import java.util.Set;

import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSRecord;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 */
public class NSRecordUtil {
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
