package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowBranch;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowBranch.
 *
 */
public class ASTFlowBranch extends AbstractFlowElement {

	public ASTFlowBranch(ASTPackage _package, ASTFlowRoot root,AbstractFlowElement parentNode, 
			FlowBranch flowBranch) {
		super(_package, root, parentNode, flowBranch);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowBranch";
	}

	public String getSwitchOn(){
		return ((FlowBranch)flowElement).getBranchSwitch();
	}
	
	public boolean isEvaluateLabels(){
		return flowElement.isCondition();
	}
	
	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}


}
