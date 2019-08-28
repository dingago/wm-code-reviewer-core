package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the flow branch always has child elements.
 */
public class EmptyFlowBranchRule extends AbstractWmRule {

	public Object visit(ASTFlowBranch node, Object data) {
		if (node.jjtGetNumChildren() == 0) {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
		}
		return super.visit(node, data);
	}

}