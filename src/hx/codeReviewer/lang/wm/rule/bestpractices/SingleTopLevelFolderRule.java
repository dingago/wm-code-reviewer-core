package hx.codeReviewer.lang.wm.rule.bestpractices;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure there is only one single folder at top level.
 */
public class SingleTopLevelFolderRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		if (node.jjtGetNumChildren() > 1) {
			addViolation(data, node);
		}
		return null;
	}
}
