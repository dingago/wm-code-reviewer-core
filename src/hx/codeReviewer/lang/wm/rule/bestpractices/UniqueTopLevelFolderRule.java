package hx.codeReviewer.lang.wm.rule.bestpractices;

import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the top level folder has the same name as package, so the
 *          namespace won't make any conflict with other package.
 */
public class UniqueTopLevelFolderRule extends AbstractWmRule {

	public Object visit(ASTFolder node, Object data) {
		if (node.jjtGetParent() instanceof ASTPackage) {
			if (!node.getName().equals(
					((ASTPackage) node.jjtGetParent()).getName())) {
				addViolation(data, node, node.getName());
			}
		}
		return null;
	}
}
