package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure exit from property is configured correctly for FLowExit.
 */
public class InvalidExitFromRule extends AbstractWmRule {

	public Object visit(ASTFlowExit node, Object data) {
		String exitFrom = node.getExitFrom();

		if (exitFrom != null && !exitFrom.isEmpty()
				&& !exitFrom.equals("$parent") && !exitFrom.equals("$loop")
				&& !exitFrom.equals("$flow")
				&& !node.getRoot().isLabelExist(exitFrom)) {
			addViolation(data, node, new String[] { exitFrom, node.getNsName(),
					node.getPath() });
		}
		return null;
	}

}
