package hx.codeReviewer.lang.wm.rule.performance;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractNsService;
import hx.codeReviewer.lang.wm.ast.AbstractNsService.PipelineDebugOption;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the service debug pipeline is not set to save for better
 *          performance.
 */
public class SaveDebugPipelineRule extends AbstractWmRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractNsService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractNsService) node, data);
	}

	private Object visit(AbstractNsService node, Object data) {
		if (node.getPipelineDebug() == PipelineDebugOption.SAVE) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}

}
