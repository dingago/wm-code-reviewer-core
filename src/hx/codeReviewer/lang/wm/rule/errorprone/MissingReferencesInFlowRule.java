package hx.codeReviewer.lang.wm.rule.errorprone;

import com.wm.lang.flow.MapWmPathInfo;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSService;
import com.wm.lang.ns.WmPathItem;

import hx.codeReviewer.lang.wm.ast.ASTFlowDrop;
import hx.codeReviewer.lang.wm.ast.ASTFlowInvoke;
import hx.codeReviewer.lang.wm.ast.ASTFlowLink;
import hx.codeReviewer.lang.wm.ast.ASTFlowSet;
import hx.codeReviewer.lang.wm.ast.ASTFlowTransformer;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure there is no missing references in flow service.
 */
public class MissingReferencesInFlowRule extends AbstractWmRule {

	public Object visit(ASTFlowInvoke node, Object data) {
		if (node.isEnabled()) {
			String serviceName = node.getService();
			NSNode nsNode = node.getPackage().getNamespace()
					.getNode(NSName.create(serviceName));
			if (nsNode == null || !(nsNode instanceof NSService)) {
				addViolation(data, node, new String[] { "Service", serviceName,
						node.getNsName(), node.getPath() });
			}
			return super.visit(node, data);
		} else {
			return null;
		}
	}

	public Object visit(ASTFlowTransformer node, Object data) {
		if (node.isEnabled()) {
			String serviceName = node.getService();
			NSNode nsNode = node.getPackage().getNamespace()
					.getNode(NSName.create(serviceName));
			if (nsNode == null || !(nsNode instanceof NSService)) {
				addViolation(data, node, new String[] { "Service", serviceName,
						node.getNsName(), node.getPath() });
			}
			return super.visit(node, data);
		} else {
			return null;
		}
	}

	public Object visit(ASTFlowLink node, Object data) {
		if (node.isEnabled()) {
			processMissingDocumentType(node, data, node.getMapFrom());
			processMissingDocumentType(node, data, node.getMapTo());
		}
		return null;
	}

	public Object visit(ASTFlowSet node, Object data) {
		if (node.isEnabled()) {
			processMissingDocumentType(node, data, node.getField());
		}
		return null;
	}

	public Object visit(ASTFlowDrop node, Object data) {
		if (node.isEnabled()) {
			processMissingDocumentType(node, data, node.getField());
		}
		return null;
	}

	private void processMissingDocumentType(AbstractFlowElement node,
			Object data, String field) {
		MapWmPathInfo fieldPathInfo = MapWmPathInfo.create(field);
		WmPathItem[] fieldPathItems = fieldPathInfo.getPathItems();
		for (WmPathItem fieldPathItem : fieldPathItems) {
			if (fieldPathItem.getType() == NSField.FIELD_RECORDREF) {
				String documentTypeName = fieldPathItem.getNSName();
				NSNode documentTypeNode = node.getPackage().getNamespace()
						.getNode(NSName.create(documentTypeName));
				if (documentTypeNode == null
						|| !(documentTypeNode instanceof NSRecord)) {
					addViolation(data, node,
							new String[] { "Document type", documentTypeName,
									node.getNsName(), node.getPath() });
				}
			}
		}
	}

}