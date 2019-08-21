package hx.codeReviewer.lang.wm.rule.codestyle;

import java.util.regex.Pattern;

import net.sourceforge.pmd.properties.PropertyDescriptor;
import net.sourceforge.pmd.properties.PropertyFactory;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the package name will matches given regex pattern. By
 *          default, the package name is supposed to follow upper camel case.
 */
public class PackageNamingConventionsRule extends AbstractWmRule {
	private final PropertyDescriptor<Pattern> packageNameRegex = PropertyFactory
			.regexProperty("regex").desc("Package name regular expression")
			.defaultValue("[A-Z][a-zA-Z0-9]*").build();

	public PackageNamingConventionsRule() {
		definePropertyDescriptor(packageNameRegex);
	}

	public Object visit(ASTPackage node, Object data) {
		String packageName = node.getName();
		Pattern regex = getProperty(packageNameRegex);
		if (!regex.matcher(packageName).matches()) {
			addViolation(data, node,
					new String[] { packageName, regex.toString() });
		}
		return null;
	}

}
