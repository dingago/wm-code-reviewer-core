package hx.codeReviewer.lang.wm.rule;

import hx.codeReviewer.lang.wm.ast.ASTDocumentType;
import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.ast.ASTFlowDrop;
import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.ast.ASTFlowInvoke;
import hx.codeReviewer.lang.wm.ast.ASTFlowLink;
import hx.codeReviewer.lang.wm.ast.ASTFlowLoop;
import hx.codeReviewer.lang.wm.ast.ASTFlowMap;
import hx.codeReviewer.lang.wm.ast.ASTFlowRepeat;
import hx.codeReviewer.lang.wm.ast.ASTFlowRoot;
import hx.codeReviewer.lang.wm.ast.ASTFlowSequence;
import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTFlowSet;
import hx.codeReviewer.lang.wm.ast.ASTFlowTransformer;
import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
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
 * @version 1.3
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

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowBranch node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowExit node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowInvoke node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowLoop node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowMap node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowRepeat node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowRoot node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowSequence node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowLink node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowSet node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowDrop node, Object data) {
		return visit((WmNode) node, data);
	}

	@Override
	public Object visit(ASTFlowTransformer node, Object data) {
		return visit((WmNode) node, data);
	}

	public Object visit(ASTDocumentType node, Object data) {
		return visit((WmNode) node, data);
	}
}
