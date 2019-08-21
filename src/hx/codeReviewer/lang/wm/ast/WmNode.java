package hx.codeReviewer.lang.wm.ast;

import net.sourceforge.pmd.lang.ast.Node;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Root interface for all Nodes of the webMethods AST.
 *
 */
public interface WmNode extends Node {
	Object jjtAccept(WmParserVisitor visitor, Object data);

	Object childrenAccept(WmParserVisitor visitor, Object data);
}
