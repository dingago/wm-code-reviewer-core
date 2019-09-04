package hx.codeReviewer.lang.wm.rule.errorprone;

import com.wm.app.b2b.server.SpecService;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSRecordRef;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.ASTSpecService;
import hx.codeReviewer.lang.wm.ast.AbstractBaseService;
import hx.codeReviewer.lang.wm.rule.AbstractBaseServiceRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure there is no missing document type or specification
 *          reference in service signature.
 */
public class MissingReferencesInServiceSignatureRule extends
		AbstractBaseServiceRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	@Override
	public Object visit(ASTSpecService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	public Object visit(AbstractBaseService node, Object data) {
		if (node.getSpecNsName() != null && !node.getSpecNsName().isEmpty()) {
			NSNode nsNode = node.getPackage().getNamespace()
					.getNode(NSName.create(node.getSpecNsName()));
			if (nsNode == null || !(nsNode instanceof SpecService)) {
				addViolation(
						data,
						node,
						new String[] { "Spec", node.getSpecNsName(),
								node.getNsName() });
			}
		}

		if (node.getSignature() != null) {
			processMissingDocumentType(node, data, node.getSignature()
					.getInput());
			processMissingDocumentType(node, data, node.getSignature()
					.getOutput());
		}

		return null;
	}

	private void processMissingDocumentType(AbstractBaseService node,
			Object data, NSRecord nsRecord) {
		for (NSField nsField : nsRecord.getFields()) {
			if (nsField.getType() == NSField.FIELD_RECORDREF) {
				NSName nsName = ((NSRecordRef)nsField).getTargetName();
				NSNode nsNode = node.getPackage().getNamespace()
						.getNode(nsName);
				if (nsNode == null || !(nsNode instanceof NSRecord)) {
					addViolation(data, node, new String[] { "Document type",
							nsName.getFullName(), node.getNsName() });
				}
			} else if (nsField.getType() == NSField.FIELD_RECORD) {
				processMissingDocumentType(node, data, (NSRecord) nsField);
			}
		}
	}

}
