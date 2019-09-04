package hx.codeReviewer.lang.wm.rule.bestpractices;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the package is not empty.
 */
public class EmptyPackageRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		if (node.jjtGetNumChildren() == 0) {
			addViolation(data, node);
		}
		return null;
	}

}
