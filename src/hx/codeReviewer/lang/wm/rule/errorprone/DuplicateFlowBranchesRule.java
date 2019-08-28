package hx.codeReviewer.lang.wm.rule.errorprone;

import java.util.HashSet;
import java.util.Set;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the flow branch doesn't has child element with the same
 *          label.
 */
public class DuplicateFlowBranchesRule extends AbstractWmRule {

	public Object visit(ASTFlowBranch node, Object data) {
		Set<String> labels = new HashSet<String>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			AbstractFlowElement childElement = (AbstractFlowElement) node
					.jjtGetChild(i);
			String label = childElement.getLabel();
			if (labels.contains(label)) {
				addViolation(data, node, new String[] { node.getNsName(),
						label, node.getPath() });
			} else {
				labels.add(label);
			}
		}
		return super.visit(node, data);
	}

}