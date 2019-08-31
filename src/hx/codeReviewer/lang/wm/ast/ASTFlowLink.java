package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowMapCopy;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowMapCopy.
 *
 */
public class ASTFlowLink extends AbstractFlowElement {

	public ASTFlowLink(ASTPackage _package, ASTFlowRoot root,
			ASTFlowMap parentNode, FlowMapCopy flowMapCopy) {
		super(_package, root, parentNode, flowMapCopy);
	}

	public String getMapFrom() {
		return ((FlowMapCopy) flowElement).getMapFrom();
	}

	public String getMapTo() {
		return ((FlowMapCopy) flowElement).getMapTo();
	}

	public String getCopyCondition() {
		return ((FlowMapCopy) flowElement).getCondition();
	}

	public boolean isCopyConditionEvaluated() {
		return !((FlowMapCopy) flowElement).isConditionDisabled();
	}

	@Override
	public String getXPathNodeName() {
		return "FlowLink";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
