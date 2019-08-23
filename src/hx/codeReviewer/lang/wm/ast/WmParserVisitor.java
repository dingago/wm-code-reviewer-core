package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          The interface defines methods to visit all kinds of AST nodes.
 */
public interface WmParserVisitor {
	public abstract Object visit(WmNode node, Object data);

	public abstract Object visit(ASTPackage node, Object data);

	public abstract Object visit(ASTParsedUnit node, Object data);

	public abstract Object visit(ASTFolder node, Object data);

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 */
	public abstract Object visit(ASTJavaService node, Object data);
}
