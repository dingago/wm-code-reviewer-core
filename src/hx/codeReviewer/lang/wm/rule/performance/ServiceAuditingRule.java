package hx.codeReviewer.lang.wm.rule.performance;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractBaseService;
import hx.codeReviewer.lang.wm.ast.AbstractNsService.AuditOption;
import hx.codeReviewer.lang.wm.rule.AbstractBaseServiceRule;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure the service auditing is set properly for better
 *          performance.
 */
public class ServiceAuditingRule extends AbstractBaseServiceRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	public Object visit(AbstractBaseService node, Object data) {
		if (node.getAudit() == AuditOption.ALWAYS) {
			addViolation(data, node, new String[] { node.getAudit().toString(),
					node.getNsName() });
		}
		return null;
	}

}
