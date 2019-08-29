package hx.codeReviewer.util;

import com.wm.app.b2b.server.ns.Namespace;
import com.wm.lang.ns.NSNode;

import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode.NodeType;

public class RuntimeUtil {
	public static NodeType checkNodeExistence(String nodeName) {
		NSNode nsNode = Namespace.current().getNode(nodeName);
		if (nsNode == null) {
			return NodeType.NONE;
		} else {
			return AbstractNsNode.getNodeType(nsNode.getValues());
		}
	}
}
