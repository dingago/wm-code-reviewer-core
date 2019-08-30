package hx.codeReviewer.lang.wm.rule.errorprone;

import java.util.Iterator;
import java.util.Vector;

import com.wm.app.b2b.server.Manifest;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.AbstractNsService;
import hx.codeReviewer.lang.wm.ast.AbstractWmNode;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure the shutdown services exist.
 */
public class ShutdownServicesRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		Manifest manifest = node.getManifest();
		Vector<String> shutdownServices = manifest.getShutdownServices();
		if (shutdownServices != null) {
			Iterator<String> iter = shutdownServices.iterator();
			while (iter.hasNext()) {
				String serviceName = iter.next();
				AbstractWmNode serviceNode = node.getNode(serviceName);
				if (serviceNode == null
						|| !(serviceNode instanceof AbstractNsService)) {
					addViolation(data, node, serviceName);
				}
			}
		}
		return null;
	}

}
