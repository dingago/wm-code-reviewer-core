package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowRoot;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          This class represents flow element com.wm.lang.flow.FlowRoot.
 *
 */
public class ASTFlowRoot extends ASTFlowSequence {

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

}
