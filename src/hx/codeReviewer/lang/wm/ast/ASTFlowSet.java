package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowMapSet;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowMapSet.
 *
 */
public class ASTFlowSet extends AbstractFlowElement {

	public ASTFlowSet(ASTPackage _package, ASTFlowRoot root,
			ASTFlowMap parentNode, FlowMapSet flowMapSet) {
		super(_package, root, parentNode, flowMapSet);
	}

	public String getField() {
		return ((FlowMapSet) flowElement).getField();
	}
	
	public Object getData(){
		return ((FlowMapSet) flowElement).getInput();
	}

	public String getEncoding() {
		return ((FlowMapSet) flowElement).getEncoding();
	}

	public boolean isOverwrite() {
		return ((FlowMapSet) flowElement).isOverwrite();
	}

	public boolean isVariableSubstituted() {
		return ((FlowMapSet) flowElement).isVariables();
	}

	public boolean isGlobalVariableSubstituted() {
		return ((FlowMapSet) flowElement).isGlobalVariables();
	}

	@Override
	public String getXPathNodeName() {
		return "FlowSet";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
