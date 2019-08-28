package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowRoot;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the flow service is not empty.
 */
public class EmptyFlowServiceRule extends AbstractWmRule {

	public Object visit(ASTFlowRoot node, Object data) {
		if (node.jjtGetNumChildren() == 0) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}

}