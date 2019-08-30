package hx.codeReviewer.lang.wm.rule.errorprone;

import java.util.Iterator;
import java.util.Vector;

import com.wm.app.b2b.server.Manifest;
import com.wm.app.b2b.server.Manifest.Requires;
import com.wm.app.b2b.server.PackageManager;

import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;
import hx.codeReviewer.util.RuntimeUtil;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          Makes sure the required packages exists and match required version.
 */
public class RequiredPackagesRule extends AbstractWmRule {

	public Object visit(ASTPackage node, Object data) {
		Manifest manifest = node.getManifest();
		Vector<Requires> requiredPackages = manifest.getRequires();
		if (requiredPackages != null) {
			Iterator<Requires> iter = requiredPackages.iterator();
			while (iter.hasNext()) {
				Requires requires = iter.next();
				String packageName = requires.getPackage();
				String requiredVersion = requires.getVersion();
				String actualVersion = RuntimeUtil
						.getPackageVersion(packageName);
				if (actualVersion == null) {
					addViolation(data, node, packageName);
				} else if (!PackageManager.checkDependencyVersion(
						requiredVersion, actualVersion)) {
					addViolation(data, node, packageName);
				}
			}
		}

		return null;
	}

}
