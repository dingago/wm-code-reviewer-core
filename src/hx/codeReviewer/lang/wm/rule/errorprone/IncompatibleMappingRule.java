package hx.codeReviewer.lang.wm.rule.errorprone;

import com.wm.lang.flow.MapWmPathInfo;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.WmPathItem;

import hx.codeReviewer.lang.wm.ast.ASTFlowLink;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.NSFieldUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure there is no incompatible mapping e.g. Integer to String
 *          in FlowMapCopy.
 */
public class IncompatibleMappingRule extends AbstractWmRule {

	@Override
	public Object visit(ASTFlowLink node, Object data) {
		MapWmPathInfo mapFromPath = MapWmPathInfo.create(node.getMapFrom());
		MapWmPathInfo mapToPath = MapWmPathInfo.create(node.getMapTo());
		WmPathItem mapFromPathItem = mapFromPath.getPathItems()[mapFromPath
				.getPathItems().length - 1];
		WmPathItem mapToPathItem = mapToPath.getPathItems()[mapToPath
				.getPathItems().length - 1];

		switch (mapToPathItem.getType()) {
		case NSField.FIELD_STRING:
			switch (mapFromPathItem.getType()) {
			case NSField.FIELD_STRING:
				return null;
			case NSField.FIELD_OBJECT:
				switch (mapFromPathItem.getJavaType()) {
				case (NSFieldUtil.JAVA_TYPE_UNKNOWN):
					return null;
				}
			}
			break;
		case NSField.FIELD_RECORD:
		case NSField.FIELD_RECORDREF:
			switch (mapFromPathItem.getType()) {
			case NSField.FIELD_RECORD:
			case NSField.FIELD_RECORDREF:
				return null;
			case NSField.FIELD_OBJECT:
				switch (mapFromPathItem.getJavaType()) {
				case (NSFieldUtil.JAVA_TYPE_UNKNOWN):
					return null;
				}
			}
			break;
		case NSField.FIELD_OBJECT:
			switch (mapToPathItem.getJavaType()) {
			case (NSFieldUtil.JAVA_TYPE_UNKNOWN):
				return null;
			default:
				if (mapFromPathItem.getType() == NSField.FIELD_OBJECT
						&& (mapFromPathItem.getJavaType() == NSFieldUtil.JAVA_TYPE_UNKNOWN || mapFromPathItem
								.getJavaType() == mapToPathItem.getJavaType())) {
					return null;
				}
			}
			break;
		}
		addViolation(
				data,
				node,
				new String[] { node.getMapFrom(), node.getMapTo(),
						node.getNsName(), node.getPath() });
		return null;
	}

}
