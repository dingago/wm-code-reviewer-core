package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          This class implements WmParserVisitor.
 *
 */
public class WmParserVisitorAdapter implements WmParserVisitor {

	@Override
	public Object visit(WmNode node, Object data) {
		return node.childrenAccept(this, data);
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

}
