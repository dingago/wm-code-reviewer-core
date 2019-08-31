package hx.codeReviewer.lang.wm.rule.errorprone;

import java.util.Iterator;
import java.util.Vector;

import com.wm.app.b2b.server.Manifest;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractNsService;
import hx.codeReviewer.lang.wm.ast.AbstractWmNode;
import hx.codeReviewer.lang.wm.ast.ASTPackage.ReleaseType;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.RuntimeUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          Makes sure the replication services exist.
 */
public class ReplicationServicesRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		Manifest manifest = node.getManifest();
		Vector<String> replicationServices = manifest.getReplicationServices();
		if (replicationServices != null) {
			Iterator<String> iter = replicationServices.iterator();
			while (iter.hasNext()) {
				String serviceName = iter.next();
				AbstractWmNode serviceNode = node.getNode(serviceName);
				if (serviceNode == null) {
					if (!AbstractNsNode
							.isServiceNode(RuntimeUtil.checkNodeExistence(
									serviceName,
									node.getReleaseType() == ReleaseType.PARTIAL ? null
											: node.getName()))) {
						addViolation(data, node, serviceName);
					}
				} else if (!(serviceNode instanceof AbstractNsService)) {
					addViolation(data, node, serviceName);
				}
			}
		}
		return null;
	}

}
