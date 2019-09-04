package hx.codeReviewer.lang.wm.rule.errorprone;

import com.wm.lang.flow.FlowElement;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure the flow branch always has child elements.
 */
public class EmptyFlowBranchRule extends AbstractWmRule {

	public Object visit(ASTFlowBranch node, Object data) {
		if (!node.isEnabled()) {
			return null;
		}

		if (node.jjtGetNumChildren() == 0
				|| areChildrenDisabled(node.getFlowElement())) {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
		}
		return super.visit(node, data);
	}

	private boolean areChildrenDisabled(FlowElement flowElement) {
		for (FlowElement childElement : flowElement.getNodes()) {
			if (childElement.isEnabled()) {
				return false;
			}
		}
		return true;
	}

}