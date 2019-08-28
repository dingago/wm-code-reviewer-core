package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowLoop;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowLoop.
 *
 */
public class ASTFlowLoop extends AbstractFlowElement {

	public ASTFlowLoop(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowLoop flowLoop) {
		super(_package, root, parentNode, flowLoop);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowLoop";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getInputArray() {
		return ((FlowLoop) flowElement).getInArray();
	}

	public String getOutputArray() {
		return ((FlowLoop) flowElement).getOutArray();
	}

}
