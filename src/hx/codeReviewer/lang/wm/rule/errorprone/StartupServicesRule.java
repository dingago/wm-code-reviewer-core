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
 *          Makes sure the startup services exist.
 */
public class StartupServicesRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		Manifest manifest = node.getManifest();
		Vector<String> startupServices = manifest.getStartupServices();
		if (startupServices != null){
			Iterator<String> iter = startupServices.iterator();
			while (iter.hasNext()){
				String serviceName = iter.next();
				AbstractWmNode serviceNode = node.getNode(serviceName);
				if (serviceNode == null || !(serviceNode instanceof AbstractNsService)){
					addViolation(data, node, serviceName);
				}
			}
		}
		return null;
	}

}
