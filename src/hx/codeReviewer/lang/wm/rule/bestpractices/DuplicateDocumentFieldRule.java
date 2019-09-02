package hx.codeReviewer.lang.wm.rule.bestpractices;

import hx.codeReviewer.lang.wm.ast.ASTDocumentType;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.NSFieldUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure no duplicate field is defined in document type.
 */
public class DuplicateDocumentFieldRule extends AbstractWmRule {

	@Override
	public Object visit(ASTDocumentType node, Object data) {
		if (NSFieldUtil.isDuplicateFieldFound(node.getContent(), true)) {
			addViolation(data, node, node.getNsName());
		}
		return null;
	}

}
