package hx.codeReviewer.lang.java.rule.bestpractices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTReferenceType;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.TypeNode;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.typeresolution.TypeHelper;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure you destroy IDataCursor at the same level you declare it.
 *          It does this by looking for code patterned like this:
 * 
 *          <pre>
 *          IDataCursor cursor = pipeline.getCursor();
 *          // do stuff
 *          cursor.destroy();
 *          </pre>
 */
public class DestroyCursorRule extends AbstractJavaRule {
	private static final String CURSOR_TYPE = "com.wm.data.IDataCursor";
	private static final String CURSOR_SIMPLE_TYPE = "IDataCursor";

	private static String toSimpleType(String fullyQualifiedClassName) {
		int lastIndexOf = fullyQualifiedClassName.lastIndexOf('.');
		if (lastIndexOf > -1) {
			return fullyQualifiedClassName.substring(lastIndexOf + 1);
		} else {
			return fullyQualifiedClassName;
		}
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		checkForCursors(node, data);
		return super.visit(node, data);
	}

	private void checkForCursors(Node node, Object data) {
		List<ASTVariableDeclarator> vars = new ArrayList<ASTVariableDeclarator>();
		Set<ASTVariableDeclaratorId> ids = new HashSet<ASTVariableDeclaratorId>();

		for (ASTLocalVariableDeclaration localVar : node
				.findDescendantsOfType(ASTLocalVariableDeclaration.class)) {
			vars.addAll(localVar
					.findChildrenOfType(ASTVariableDeclarator.class));
		}

		for (ASTVariableDeclarator var : vars) {
			TypeNode type = ((ASTLocalVariableDeclaration) var.jjtGetParent())
					.getTypeNode();
			if (type != null && isCursorType(type)) {
				ids.add(var.getVariableId());
			}
		}

		for (ASTVariableDeclaratorId id : ids) {
			ensureDestroyed((ASTLocalVariableDeclaration) id.jjtGetParent()
					.jjtGetParent(), id, data);
		}
	}

	private boolean isCursorType(TypeNode refType) {
		if (refType.getType() != null) {
			if (TypeHelper.isA(refType, CURSOR_TYPE)) {
				return true;
			}
		} else if (refType.jjtGetChild(0) instanceof ASTReferenceType) {
			ASTReferenceType ref = (ASTReferenceType) refType.jjtGetChild(0);
			if (ref.jjtGetChild(0) instanceof ASTClassOrInterfaceType) {
				ASTClassOrInterfaceType clazz = (ASTClassOrInterfaceType) ref
						.jjtGetChild(0);
				if (toSimpleType(clazz.getImage()).equals(CURSOR_SIMPLE_TYPE)
						&& !clazz.isReferenceToClassSameCompilationUnit()
						|| clazz.getImage().equals(CURSOR_TYPE)
						&& !clazz.isReferenceToClassSameCompilationUnit()) {
					return true;
				}
			}
		}
		return false;
	}

	private void ensureDestroyed(ASTLocalVariableDeclaration var,
			ASTVariableDeclaratorId id, Object data) {
		boolean destroyed = false;
		String cursorToDestroy = id.getImage();

		Node parentNode = var.jjtGetParent();
		Node grandParentNode = parentNode.jjtGetParent();
		for (int i = parentNode.jjtGetChildIndex() + 1; i < grandParentNode
				.jjtGetNumChildren(); i++) {
			Node uncleNode = grandParentNode.jjtGetChild(i);

			List<ASTName> names = uncleNode
					.findDescendantsOfType(ASTName.class);
			for (ASTName oName : names) {
				String name = oName.getImage();
				if (name != null && name.contains(".")) {
					String[] parts = name.split("\\.");
					if (parts.length == 2) {
						String methodName = parts[1];
						String varName = parts[0];
						if (varName.equals(cursorToDestroy)
								&& methodName.equals("destroy")) {
							destroyed = true;
							break;
						}
					}
				}
			}
		}

		if (!destroyed) {
			addViolation(data, id);
		}
	}
}
