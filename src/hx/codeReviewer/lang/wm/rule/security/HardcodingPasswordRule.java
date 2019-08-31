package hx.codeReviewer.lang.wm.rule.security;

import java.util.Stack;

import com.wm.lang.flow.MapWmPathInfo;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.WmPathItem;
import com.wm.util.Values;

import hx.codeReviewer.lang.wm.ast.ASTDocumentType;
import hx.codeReviewer.lang.wm.ast.ASTFlowSet;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.RuntimeUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure there is no hard-coding value set for a password field in
 *          Flow Service.
 */
public class HardcodingPasswordRule extends AbstractWmRule {

	@Override
	public Object visit(ASTFlowSet node, Object data) {
		String field = node.getField();
		MapWmPathInfo path = MapWmPathInfo.create(field);
		WmPathItem[] pathItems = path.getPathItems();
		WmPathItem lastPathItem = pathItems[pathItems.length - 1];
		/**
		 * Only do the checking when target field is a scalar string.
		 */
		if (lastPathItem.getType() == NSField.FIELD_STRING
				&& lastPathItem.getDimension() == NSField.DIM_SCALAR) {
			Stack<String> fieldNames = new Stack<String>();
			for (int i = pathItems.length - 1; i >= 0; i--) {
				WmPathItem pathItem = pathItems[i];
				/**
				 * Only do the checking when target field is under a referenced
				 * document.
				 */
				if (pathItem.getType() == NSField.FIELD_RECORDREF) {
					NSRecord nsRecord = null;
					String documentTypeName = pathItem.getNSName();
					AbstractNsNode documentTypeNode = node.getPackage()
							.getNode(documentTypeName);
					/**
					 * Trying to locate the referenced document type.
					 */
					if (documentTypeNode != null) {
						if (documentTypeNode instanceof ASTDocumentType) {
							nsRecord = ((ASTDocumentType) documentTypeNode)
									.getContent();
						}
					} else {
						NSNode nsNode = RuntimeUtil.getNode(documentTypeName);
						if (nsNode != null && nsNode instanceof NSRecord) {
							nsRecord = (NSRecord) nsNode;
						}
					}

					if (nsRecord != null) {
						while (!fieldNames.isEmpty()) {
							String fieldName = fieldNames.pop();
							NSField nsField = nsRecord
									.getFieldByName(fieldName);
							if (nsField == null) {
								/**
								 * Not found target field which is unexpected,
								 * but this is what does this rule care, so let
								 * other rules to complain.
								 */
								return null;
							} else {
								if (!fieldNames.isEmpty()) {
									if (nsField.getType() == NSField.FIELD_RECORD) {
										/**
										 * Not reaching the target field yet,
										 * keep looking for.
										 */
										nsRecord = (NSRecord) nsField;
										continue;
									} else {
										/**
										 * Not found target field because no
										 * further sub-field to look for.
										 */
										return null;
									}
								} else {
									if (nsField.getType() == NSField.FIELD_STRING
											&& nsField.getDimensions() == NSField.DIM_SCALAR) {
										/**
										 * Found the target field.
										 */
										Values hintsValues = nsField.getHints();
										if (hintsValues != null) {
											if ("true"
													.equalsIgnoreCase(hintsValues
															.getString("field_password"))) {
												addViolation(
														data,
														node,
														new String[] {
																node.getNsName(),
																node.getPath(),
																field });
											}
										}
										return null;
									} else {
										/**
										 * Not found target field because the
										 * found field is not scalar string.
										 */
										return null;
									}
								}
							}
						}
					}
					/**
					 * Not found target field in the closest referenced document
					 * type. No need to keep looking for.
					 */
					return null;
				} else {
					/**
					 * Store field name and lookup next.
					 */
					fieldNames.push(pathItem.getName());
				}
			}
		}
		return null;
	}

}
