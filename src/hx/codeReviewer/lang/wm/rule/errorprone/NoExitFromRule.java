package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure exit from property is configured for FLowExit.
 */
public class NoExitFromRule extends AbstractWmRule {

	public Object visit(ASTFlowExit node, Object data) {
		if (node.isEnabled()) {
			if (node.getExitFrom() == null || node.getExitFrom().isEmpty()) {
				addViolation(data, node,
						new String[] { node.getNsName(), node.getPath() });
			}
		}
		return null;
	}

}
