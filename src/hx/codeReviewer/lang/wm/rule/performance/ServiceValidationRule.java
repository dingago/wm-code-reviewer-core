package hx.codeReviewer.lang.wm.rule.performance;

import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the service inputs/outputs validation is disabled for
 *          better performance.
 */
public class ServiceValidationRule extends AbstractWmRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		if (node.isValidateInputs() || node.isValidateOutputs()) {
			addViolation(data, node);
		}
		return null;
	}

}
