package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.NSNode;

/**
 * @author Xiaowei Wang
 * @version 1.3
 * 
 *          The abstract AST node represents any webMethods node extends from
 *          class com.wm.lang.ns.NSNode.
 *
 */
public abstract class AbstractNsNode extends AbstractWmNode {

	NSNode nsNode;

	public AbstractNsNode(ASTPackage _package, AbstractWmNode parentNode,
			NSNode nsNode) {
		super(_package);
		this.nsNode = nsNode;

		/**
		 * Index node in package, and associate with parent node.
		 */
		_package.indexNode(this.getNsName(), this);
		parentNode.jjtAddChild(this, parentNode.jjtGetNumChildren());
		this.jjtSetParent(parentNode);
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
