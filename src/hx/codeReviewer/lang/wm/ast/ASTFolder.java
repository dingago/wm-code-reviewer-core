package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          This class represents node com.wm.lang.ns.NSInterface.
 *
 */
public class ASTFolder extends AbstractNsNode {

	public ASTFolder(ASTPackage _package, String name, String nsName, String comment) {
		super(_package, name, nsName, comment);
	}

	@Override
	public String getXPathNodeName() {
		return "Folder";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param nodeName
	 *            The name of sub folder node.
	 * @return The namespace name of sub folder node.
	 */
	public String getSubFolderNSName(String nodeName) {
		return nsName + "." + nodeName;
	}
}
