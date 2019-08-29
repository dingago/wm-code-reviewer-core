package hx.codeReviewer.util;

import com.wm.app.b2b.server.ns.Namespace;
import com.wm.lang.ns.NSNode;
import com.wm.app.b2b.server.Package;

import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode.NodeType;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          This class helps to retrieve dynamic information on runtime.
 *
 */
public class RuntimeUtil {
	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param nodeName
	 *            The name of node to check.
	 * @return The type of node.
	 * 
	 *         This method checks if a specific node exists, if so returns the
	 *         type of node, otherwise returns NodeType.NONE.
	 */
	public static NodeType checkNodeExistence(String nodeName) {
		NSNode nsNode = Namespace.current().getNode(nodeName);
		if (nsNode == null) {
			return NodeType.NONE;
		} else {
			return AbstractNsNode.getNodeType(nsNode.getValues());
		}
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param packageName
	 *            The name of package to check.
	 * @return The version of package.
	 * 
	 *         This method checks if a specific package exists, if so returns
	 *         the version of package, otherwise returns null.
	 */
	public static String getPackageVersion(String packageName) {
		Package _package = (Package) Namespace.current()
				.getPackage(packageName);
		if (_package == null) {
			return null;
		} else {
			return _package.getVersion();
		}
	}
}
