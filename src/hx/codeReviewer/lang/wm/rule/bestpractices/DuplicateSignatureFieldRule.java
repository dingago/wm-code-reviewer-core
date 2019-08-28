package hx.codeReviewer.lang.wm.rule.bestpractices;

import java.util.HashSet;
import java.util.Set;

import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSSignature;

import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.AbstractNsService;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure no duplicate field is defined in service signature.
 */
public class DuplicateSignatureFieldRule extends AbstractWmRule {

	@Override
	public Object visit(ASTJavaService node, Object data) {
		return visit((AbstractNsService) node, data);
	}

	@Override
	public Object visit(ASTFlowService node, Object data) {
		return visit((AbstractNsService) node, data);
	}

	private Object visit(AbstractNsService node, Object data) {
		NSSignature nsSignature = node.getSignature();
		if (nsSignature != null) {
			if (isDuplicateFieldFound(nsSignature.getInput())
					|| isDuplicateFieldFound(nsSignature.getOutput())) {
				addViolation(data, node, node.getNsName());
			}
		}
		return null;
	}

	private static boolean isDuplicateFieldFound(NSRecord nsRecord) {
		if (nsRecord != null){
			Set<String> fieldNames = new HashSet<String>();
			for (int i = 0; i < nsRecord.getFieldCount(); i++) {
				String fieldName = nsRecord.getField(i).getName();
				if (fieldNames.contains(fieldName)) {
					return true;
				} else {
					fieldNames.add(fieldName);
				}
			}
		}
		return false;
	}

}
