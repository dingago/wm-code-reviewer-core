package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure switch-on is not set if evaluate-label set to true, or
 *          switch-no is set to some value if evaluate-label set to false for
 *          flow branch.
 */
public class InvalidFlowBranchRule extends AbstractWmRule {

	public Object visit(ASTFlowBranch node, Object data) {
		if (!node.isEnabled()) {
			return null;
		}
		if (!node.isEvaluateLabels()
				&& (node.getSwitchOn() == null || node.getSwitchOn().isEmpty())) {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
		}

		if (node.isEvaluateLabels() && node.getSwitchOn() != null
				&& !node.getSwitchOn().isEmpty()) {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
		}
		return super.visit(node, data);
	}
}
