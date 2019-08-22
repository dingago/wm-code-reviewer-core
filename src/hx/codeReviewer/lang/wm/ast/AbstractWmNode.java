package hx.codeReviewer.lang.wm.ast;

import net.sourceforge.pmd.lang.ast.AbstractNode;

/**
 * @author Xiaowei Wang
 * @version 1.2
 *
 *          This abstract node of all webMethods assets.
 */
public abstract class AbstractWmNode extends AbstractNode implements WmNode {

	ASTPackage _package;

	public AbstractWmNode(ASTPackage _package) {
		super(0);
		this._package = _package;
		this.beginLine = 0;
		this.beginColumn = 0;
	}

	@Override
	public Object childrenAccept(WmParserVisitor visitor, Object data) {
		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				((WmNode) children[i]).jjtAccept(visitor, data);
			}
		}
		return data;
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	@Override
	public abstract String getXPathNodeName();

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @return The package node.
	 */
	public ASTPackage getPackage() {
		return _package;
	}
}
