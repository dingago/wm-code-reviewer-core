package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode.NodeType;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.RuntimeUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure node with the same namespace name doesn't exist on
 *          runtime.
 */
public class DuplicateNodeRule extends AbstractWmRule {

	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractNsNode) node, data);
	}
	
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractNsNode) node, data);
	}

	private Object visit(AbstractNsNode node, Object data) {
		if (RuntimeUtil.checkNodeExistence(node.getNsName(), node.getPackage()
				.getName()) != NodeType.NONE) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}

}