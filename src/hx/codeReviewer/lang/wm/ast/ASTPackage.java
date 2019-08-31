package hx.codeReviewer.lang.wm.ast;

import java.util.concurrent.ConcurrentHashMap;

import com.wm.app.b2b.server.Manifest;
import com.wm.util.Values;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.4
 * 
 *          This class represents node com.wm.lang.ns.NSPackage.
 *
 */
public class ASTPackage extends AbstractWmNode {

	private String name;
	private Manifest manifest;
	private ConcurrentHashMap<String, AbstractNsNode> namespace = new ConcurrentHashMap<String, AbstractNsNode>();
	/**
	 * Added since v1.3.
	 */
	private Values releaseValues;

	public ASTPackage(String name, Manifest manifest, Values releaseValues) {
		super(null);
		this.name = name;
		this.manifest = manifest;
		this.releaseValues = releaseValues;
	}

	public String getName() {
		return name;
	}

	public Manifest getManifest() {
		return manifest;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.3
	 * @return The release type of package.
	 */
	public ReleaseType getReleaseType() {
		if (releaseValues == null) {
			return ReleaseType.NONE;
		} else {
			String releaseType = releaseValues.getString("type");
			switch (releaseType) {
			case "full":
				return ReleaseType.FULL;
			case "partial":
				return ReleaseType.PARTIAL;
			default:
				return ReleaseType.NONE;
			}
		}
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
	 * @since 1.2
	 * @param nsName
	 *            The nsName of indexed node.
	 * @return Indexed node by given nsName.
	 */
	public AbstractNsNode getNode(String nsName) {
		return namespace.get(nsName);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.2
	 * @param nsName
	 *            The nsName of indexed node.
	 * @param node
	 *            The node to be indexed.
	 */
	public void indexNode(String nsName, AbstractNsNode node) {
		namespace.put(nsName, node);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.3
	 * 
	 *        FULL is full package to install, PARTIAL is patch to install, NONE
	 *        is an exiting package.
	 */
	public enum ReleaseType {
		FULL, PARTIAL, NONE
	}
}
