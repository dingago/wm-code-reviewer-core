package hx.codeReviewer.lang.wm.ast;

import net.sourceforge.pmd.lang.ast.AbstractNode;

/**
 * @author Xiaowei Wang
 * @version 1.0
 *
 *          This abstract node of all webMethods assets.
 */
public abstract class AbstractWmNode extends AbstractNode implements WmNode {
	public final static String FOLDER_NODES = "ns";
	public final static String FILE_V3 = "manifest.v3";
	public final static String FILE_IDF = "node.idf";
	public final static String FILE_NDF = "node.ndf";

	public AbstractWmNode() {
		super(0);
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
	public Object jjtAccept(WmParserVisitor visitor, Object data){
		return visitor.visit(this, data);
	}
	
	@Override
    public abstract String getXPathNodeName();
}
