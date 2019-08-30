package hx.codeReviewer.lang.wm.ast;

import java.util.HashSet;
import java.util.Set;

import com.wm.lang.flow.FlowRoot;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          This class represents flow element com.wm.lang.flow.FlowRoot.
 *
 */
public class ASTFlowRoot extends AbstractFlowElement {

	/**
	 * @since 1.2
	 * 
	 *        To store found label values in flow service for exit-from checking
	 *        purpose. Only for container flow element like SEQUENCE, BRANCH and
	 *        so on.
	 */
	Set<String> labels = new HashSet<String>();

	public ASTFlowRoot(ASTPackage _package, ASTFlowService flowService,
			FlowRoot flowRoot) {
		super(_package, null, null, flowRoot);
		this.root = this;
		flowService.jjtAddChild(this, 0);
		this.jjtSetParent(flowService);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowRoot";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.2
	 * @param label
	 *            The value of label to register.
	 */
	public synchronized void addLabel(String label) {
		if (label != null && !label.isEmpty()) {
			labels.add(label);
		}
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.2
	 * @param label
	 *            The value of label to check.
	 * @return Whether a given label exists in flow service.
	 */
	public boolean isLabelExist(String label) {
		return labels.contains(label);
	}

}
