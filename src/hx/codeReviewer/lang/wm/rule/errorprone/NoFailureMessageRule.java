package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.ast.ASTFlowExit.SignalOption;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure failure-message is set to some value if signal equals to
 *          FAILURE for FLowExit.
 */
public class NoFailureMessageRule extends AbstractWmRule {

	public Object visit(ASTFlowExit node, Object data) {
		if (node.isEnabled()){
			if (node.getSignal() == SignalOption.FAILURE
					&& (node.getFailureMessage() == null || node
							.getFailureMessage().isEmpty())) {
				addViolation(data, node,
						new String[] { node.getNsName(), node.getPath() });
			}
		}
		return null;
	}

}
