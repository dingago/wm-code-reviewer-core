package hx.codeReviewer.lang.wm.rule.bestpractices;

import com.wm.lang.ns.NSSignature;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractBaseService;
import hx.codeReviewer.lang.wm.rule.AbstractBaseServiceRule;
import hx.codeReviewer.util.NSRecordUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure no duplicate field is defined in service signature.
 */
public class DuplicateSignatureFieldRule extends AbstractBaseServiceRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractBaseService) node, data);
	}

	public Object visit(AbstractBaseService node, Object data) {
		NSSignature nsSignature = node.getSignature();
		if (nsSignature != null) {
			if (NSRecordUtil
					.isDuplicateFieldFound(nsSignature.getInput(), true)
					|| NSRecordUtil.isDuplicateFieldFound(
							nsSignature.getOutput(), true)) {
				addViolation(data, node, node.getNsName());
			}
		}
		return null;
	}
}
