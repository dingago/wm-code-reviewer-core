package hx.codeReviewer.lang.wm.rule.documentation;

import hx.codeReviewer.lang.wm.ast.ASTDocumentType;
import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.rule.AbstractNsNodeRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          Makes sure there is always comment for a node.
 */
public class NoCommentRule extends AbstractNsNodeRule {

	public Object visit(ASTJavaService node, Object data) {
		return (visit((AbstractNsNode) node, data));
	}

	public Object visit(ASTFlowService node, Object data) {
		return (visit((AbstractNsNode) node, data));
	}

	public Object visit(ASTDocumentType node, Object data) {
		return (visit((AbstractNsNode) node, data));
	}

	public Object visit(AbstractNsNode node, Object data) {
		if (node.getComment() == null || node.getComment().isEmpty()) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}
}
