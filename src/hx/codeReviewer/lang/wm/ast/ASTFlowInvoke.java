package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowInvoke;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowInvoke.
 *
 */
public class ASTFlowInvoke extends AbstractFlowElement {

	public ASTFlowInvoke(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowInvoke FlowInvoke) {
		super(_package, root, parentNode, FlowInvoke);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowInvoke";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getService() {
		return ((FlowInvoke) flowElement).getService().getFullName();
	}

	public boolean isValidateInput() {
		return ((FlowInvoke) flowElement).getValidateIn().equalsIgnoreCase(
				"TRUE");
	}

	public boolean isValidateOutput() {
		return ((FlowInvoke) flowElement).getValidateOut().equalsIgnoreCase(
				"TRUE");
	}
}
