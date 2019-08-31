package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowMapDelete;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowMapDelete.
 *
 */
public class ASTFlowDrop extends AbstractFlowElement {

	public ASTFlowDrop(ASTPackage _package, ASTFlowRoot root,
			ASTFlowMap parentNode, FlowMapDelete flowMapDelete) {
		super(_package, root, parentNode, flowMapDelete);
	}

	public String getField() {
		return ((FlowMapDelete) flowElement).getField();
	}

	@Override
	public String getXPathNodeName() {
		return "FlowDrop";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
