package hx.codeReviewer.lang.wm.rule;

import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.ASTParsedUnit;
import hx.codeReviewer.lang.wm.ast.WmNode;
import hx.codeReviewer.lang.wm.ast.WmParserVisitor;

import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.AbstractRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract class for all rules webMethods rules.
 *
 */
public abstract class AbstractWmRule extends AbstractRule implements
		WmParserVisitor {

	@Override
	public void apply(List<? extends Node> nodes, RuleContext ruleContext) {
		visitAll(nodes, ruleContext);
	}

	protected void visitAll(List<? extends Node> nodes, RuleContext ctx) {
		for (Object element : nodes) {
			if ((element instanceof ASTParsedUnit)) {
				ASTParsedUnit node = (ASTParsedUnit) element;
				visit(node, ctx);
			}
		}
	}

	@Override
	public Object visit(WmNode node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTPackage node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTParsedUnit node, Object data) {
		return visit((WmNode) node, data);

	}

	@Override
	public Object visit(ASTFolder node, Object data) {
		return visit((WmNode) node, data);

	}

}
