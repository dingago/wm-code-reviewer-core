package hx.codeReviewer.lang.wm.ast;

import java.util.concurrent.ConcurrentHashMap;

import com.wm.app.b2b.server.Manifest;
import com.wm.data.IData;
import com.wm.lang.ns.NSException;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSInterface;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSPackage;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSSchema;
import com.wm.lang.ns.NSService;
import com.wm.lang.ns.NSServiceType;
import com.wm.lang.ns.Namespace;
import com.wm.util.Values;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.5
 * 
 *          This class represents node com.wm.lang.ns.NSPackage.
 *
 */
public class ASTPackage extends AbstractWmNode {

	private String name;
	private Manifest manifest;
	private ConcurrentHashMap<String, AbstractNsNode> nodes = new ConcurrentHashMap<String, AbstractNsNode>();
	private Namespace namespace = new ASTNamespace();
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
		return nodes.get(nsName);
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
		nodes.put(nsName, node);
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

	/**
	 * @since 1.5
	 * @return The package namespace.
	 */
	public Namespace getNamespace() {
		return namespace;
	}

	/**
	 * 
	 * @author Xiaowei Wang
	 * @since 1.5
	 * 
	 *        This class is a wrapper of
	 *        com.wm.app.b2b.server.ns.Namespace.current() and provide extra
	 *        node information in current package.
	 *
	 */
	private class ASTNamespace extends Namespace {
		ASTPackage astPackage = ASTPackage.this;

		@Override
		public NSPackage createPackage(String name) {
			System.err.println("ASTNamespace doesn't support createPackage");
			return null;
		}

		@Override
		public NSService createService(NSPackage pkg, NSName name,
				NSServiceType stype) {
			System.err.println("ASTNamespace doesn't support createService");
			return null;
		}

		@Override
		public void deleteNode(NSNode name) throws NSException {
			System.err.println("ASTNamespace doesn't support deleteNode");
		}

		@Override
		public NSPackage[] getAllPackages() {
			System.err.println("ASTNamespace doesn't support getAllPackages");
			return null;
		}

		@Override
		public NSNode getNode(NSName name) {
			if (nodes.containsKey(name.getFullName())) {
				return nodes.get(name.getFullName()).getNSNode();
			} else {
				NSNode node = com.wm.app.b2b.server.ns.Namespace.current()
						.getNode(name);
				if (node == null
						|| astPackage.getName().equals(
								node.getPackage().getName())
						&& astPackage.getReleaseType() == ReleaseType.FULL) {
					return null;
				} else {
					return node;
				}
			}

		}

		@Override
		public NSPackage getPackage(String name) {
			if (name == null) {
				return new NSPackage(astPackage.name) {
				};
			} else {
				return com.wm.app.b2b.server.ns.Namespace.current().getPackage(
						name);
			}
		}

		@Override
		public NSInterface getRootNode() {
			System.err.println("ASTNamespace doesn't support getRootNode");
			return null;
		}

		@Override
		public IData makeNode(NSNode node) throws NSException {
			System.err.println("ASTNamespace doesn't support makeNode");
			return null;
		}

		@Override
		public boolean nodeExists(NSName name) {
			if (nodes.containsKey(name.getFullName())) {
				return true;
			} else {
				NSNode node = com.wm.app.b2b.server.ns.Namespace.current()
						.getNode(name);
				if (node == null
						|| astPackage.getName().equals(
								node.getPackage().getName())
						&& astPackage.getReleaseType() == ReleaseType.FULL) {
					return false;
				} else {
					return true;
				}
			}
		}

		@Override
		public void putNode(NSNode node) {
			System.err.println("ASTNamespace doesn't support putNode");
		}

		@Override
		public void putNode(NSNode node, boolean unlock) {
			System.err.println("ASTNamespace doesn't support putNode");
		}

		@Override
		public boolean registerField(NSField field) throws NSException {
			System.err.println("ASTNamespace doesn't support registerField");
			return false;
		}

		@Override
		public boolean registerRecord(NSRecord record) throws NSException {
			System.err.println("ASTNamespace doesn't support registerRecord");
			return false;
		}

		@Override
		public boolean registerSchema(NSSchema schema) throws NSException {
			System.err.println("ASTNamespace doesn't support registerSchema");
			return false;
		}

	}

}
