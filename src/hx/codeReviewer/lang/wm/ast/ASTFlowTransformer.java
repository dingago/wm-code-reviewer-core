package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowMapInvoke;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowMapInvoke.
 *
 */
public class ASTFlowTransformer extends ASTFlowInvoke {

	public ASTFlowTransformer(ASTPackage _package, ASTFlowRoot root,
			ASTFlowMap parentNode, FlowMapInvoke flowMapInvoke) {
		super(_package, root, parentNode, flowMapInvoke);
	}


	@Override
	public String getXPathNodeName() {
		return "FlowTransformer";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
