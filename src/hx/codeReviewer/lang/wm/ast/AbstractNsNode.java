package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.NSNode;

/**
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          The abstract AST node represents any webMethods node extends from
 *          class com.wm.lang.ns.NSNode.
 *
 */
public abstract class AbstractNsNode extends AbstractWmNode {

	NSNode nsNode;

	public AbstractNsNode(ASTPackage _package, NSNode nsNode) {
		super(_package);
		this.nsNode = nsNode;
	}

	public String getName() {
		return nsNode.getNSName().getNodeName().toString();
	}

	public String getNsName() {
		return nsNode.getNSName().getFullName();
	}

	public String getComment() {
		return nsNode.getComment();
	}
}
