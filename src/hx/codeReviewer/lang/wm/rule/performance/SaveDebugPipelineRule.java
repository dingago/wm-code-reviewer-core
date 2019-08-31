package hx.codeReviewer.lang.wm.rule.performance;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractBaseService;
import hx.codeReviewer.lang.wm.ast.AbstractNsService.PipelineDebugOption;
import hx.codeReviewer.lang.wm.rule.AbstractBaseServiceRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure the service debug pipeline is not set to save for better
 *          performance.
 */
public class SaveDebugPipelineRule extends AbstractBaseServiceRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	public Object visit(AbstractBaseService node, Object data) {
		if (node.getPipelineDebug() == PipelineDebugOption.SAVE) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}

}
