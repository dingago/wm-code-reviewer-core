package hx.codeReviewer.lang.wm.rule.codestyle;

import java.util.regex.Pattern;

import net.sourceforge.pmd.properties.PropertyDescriptor;
import net.sourceforge.pmd.properties.PropertyFactory;
import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the folder name will matches given regex pattern. By
 *          default, the folder name is supposed to follow lower camel case. And
 *          it's the folder is at top level, the checking is ignored by default
 *          because usually the top folder name should be the same as package
 *          name which follows upper camel case.
 */
public class FolderNamingConventionsRule extends AbstractWmRule {
	private final PropertyDescriptor<Pattern> folderNameRegex = PropertyFactory
			.regexProperty("regex").desc("Folder name regular expression")
			.defaultValue("[a-z][a-zA-Z0-9]*").build();
	private final PropertyDescriptor<Boolean> ignoreTopFolder = PropertyFactory
			.booleanProperty("ignoreTopFolder")
			.desc("Whether to ignore top level folder").defaultValue(true)
			.build();

	public FolderNamingConventionsRule() {
		definePropertyDescriptor(folderNameRegex);
		definePropertyDescriptor(ignoreTopFolder);
	}

	public Object visit(ASTFolder node, Object data) {
		String folderName = node.getName();
		Pattern regex = getProperty(folderNameRegex);
		if ((!getProperty(ignoreTopFolder) || !(node.jjtGetParent() instanceof ASTPackage))
				&& !regex.matcher(folderName).matches()) {
			addViolation(data, node,
					new String[] { node.getNsName(), regex.toString() });
		}
		return super.visit(node, data);
	}
}
