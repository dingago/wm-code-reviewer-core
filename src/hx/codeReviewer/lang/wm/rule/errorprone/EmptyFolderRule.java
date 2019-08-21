package hx.codeReviewer.lang.wm.rule.errorprone;

import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the folder is not empty.
 */
public class EmptyFolderRule extends AbstractWmRule {

	public Object visit(ASTFolder node, Object data) {
		if (node.jjtGetNumChildren() == 0) {
			addViolation(data, node, node.getNsName());
		}
		return super.visit(node, data);
	}

}
