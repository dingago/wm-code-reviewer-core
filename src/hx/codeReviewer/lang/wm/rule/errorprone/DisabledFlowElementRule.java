package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.ast.ASTFlowInvoke;
import hx.codeReviewer.lang.wm.ast.ASTFlowLoop;
import hx.codeReviewer.lang.wm.ast.ASTFlowMap;
import hx.codeReviewer.lang.wm.ast.ASTFlowRepeat;
import hx.codeReviewer.lang.wm.ast.ASTFlowSequence;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure there is no disabled code in flow service.
 */
public class DisabledFlowElementRule extends AbstractWmRule {

	public Object visit(ASTFlowLoop node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowRepeat node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowBranch node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowExit node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowSequence node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowInvoke node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	public Object visit(ASTFlowMap node, Object data) {
		return visit((AbstractFlowElement) node, data);
	}

	private Object visit(AbstractFlowElement node, Object data) {
		if (node.isEnabled()) {
			return super.visit(node, data);
		} else {
			addViolation(data, node,
					new String[] { node.getNsName(), node.getPath() });
			return null;
		}
	}

}