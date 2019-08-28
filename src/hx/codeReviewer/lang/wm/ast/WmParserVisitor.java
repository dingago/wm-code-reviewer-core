package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.2
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

	/**
	 * @author Xiaowei Wang
	 * @since 1.2
	 */
	public abstract Object visit(ASTFlowBranch node, Object data);

	public abstract Object visit(ASTFlowExit node, Object data);

	public abstract Object visit(ASTFlowInvoke node, Object data);

	public abstract Object visit(ASTFlowLoop node, Object data);

	public abstract Object visit(ASTFlowMap node, Object data);

	public abstract Object visit(ASTFlowRepeat node, Object data);

	public abstract Object visit(ASTFlowRoot node, Object data);

	public abstract Object visit(ASTFlowSequence node, Object data);

	public abstract Object visit(ASTFlowService node, Object data);
}
