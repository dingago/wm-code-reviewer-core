package hx.codeReviewer.lang.wm.rule.errorprone;

import java.util.Iterator;

import com.wm.app.b2b.server.Manifest;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractNsService;
import hx.codeReviewer.lang.wm.ast.AbstractWmNode;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.RuntimeUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the replication services exist.
 */
public class ReplicationServicesRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		Manifest manifest = node.getManifest();
		Iterator<String> iter = manifest.getReplicationServices().iterator();
		while (iter.hasNext()){
			String serviceName = iter.next();
			AbstractWmNode serviceNode = node.getNode(serviceName);
			if (serviceNode == null || !(serviceNode instanceof AbstractNsService)){
				if (!AbstractNsNode.isServiceNode(RuntimeUtil.checkNodeExistence(serviceName))){
					addViolation(data, node, serviceName);
				}
			}
		}
		return null;
	}

}