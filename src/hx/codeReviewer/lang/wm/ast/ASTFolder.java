package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.NSInterface;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.3
 * 
 *          This class represents node com.wm.lang.ns.NSInterface.
 *
 */
public class ASTFolder extends AbstractNsNode {
	
	public ASTFolder(ASTPackage _package, ASTFolder parentNode, NSInterface nsInterface) {
		super(_package, parentNode, nsInterface);
	}

	public ASTFolder(ASTPackage _package, ASTPackage parentNode, NSInterface nsInterface) {
		super(_package, parentNode, nsInterface);
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
	 * @param folderName
	 *            The name of sub folder node.
	 * @return The namespace name of sub folder node.
	 */
	public String getSubFolderNSName(String folderName) {
		return nsNode.getNSName().toString() + "." + folderName;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param nodeName
	 *            The name of sub node.
	 * @return The namespace name of sub node.
	 */
	public String getSubNodeNSName(String nodeName) {
		return nsNode.getNSName().toString() + ":" + nodeName;
	}
}
