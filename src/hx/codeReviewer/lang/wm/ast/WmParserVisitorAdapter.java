package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
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

}
