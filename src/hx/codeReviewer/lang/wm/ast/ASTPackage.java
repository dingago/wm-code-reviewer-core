package hx.codeReviewer.lang.wm.ast;

import java.util.Hashtable;

import com.wm.app.b2b.server.Manifest;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          This class represents node com.wm.lang.ns.NSPackage.
 *
 */
public class ASTPackage extends AbstractWmNode {
	public final static String KEY_NAME = "name";

	private String name;
	private Manifest manifest;
	private Hashtable<String, AbstractWmNode> namespace = new Hashtable<String, AbstractWmNode>();

	public ASTPackage(String name, Manifest manifest) {
		super(null);
		this.name = name;
		this.manifest = manifest;
	}

	public String getName() {
		return name;
	}

	public Manifest getManifest() {
		return manifest;
	}

	@Override
	public String getXPathNodeName() {
		return "Package";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param nsName
	 *            The nsName of indexed node.
	 * @return Indexed node by given nsName.
	 */
	public AbstractWmNode getNode(String nsName) {
		return namespace.get(nsName);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param nsName
	 *            The nsName of indexed node.
	 * @param node
	 *            The node to be indexed.
	 */
	public void indexNode(String nsName, AbstractWmNode node) {
		namespace.put(nsName, node);
	}
}
