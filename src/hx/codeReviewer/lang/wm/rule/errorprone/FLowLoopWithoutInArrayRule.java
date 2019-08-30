package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowLoop;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the flow loop always has in array.
 */
public class FLowLoopWithoutInArrayRule extends AbstractWmRule {

	public Object visit(ASTFlowLoop node, Object data) {
		if (node.getInputArray() == null || node.getInputArray().isEmpty()) {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
		}
		return super.visit(node, data);
	}

}