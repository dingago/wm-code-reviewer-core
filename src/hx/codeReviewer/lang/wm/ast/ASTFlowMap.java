package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowMap;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowMap.
 *
 */
public class ASTFlowMap extends AbstractFlowElement {

	public ASTFlowMap(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowMap flowMap) {
		super(_package, root, parentNode, flowMap);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowMap";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public Mode getMode() {
		switch (((FlowMap) flowElement).getMode()) {
		case FlowMap.MODE_STANDALONE:
			return Mode.STANDALONE;
		case FlowMap.MODE_INPUT:
			return Mode.INVOKE_INPUT;
		case FlowMap.MODE_OUTPUT:
			return Mode.INVOKE_OUTPUT;
		case FlowMap.MODE_INVOKEINPUT:
			return Mode.TRANSFORMER_INPUT;
		case FlowMap.MODE_INVOKEOUTPUT:
			return Mode.TRANSFORMER_OUTPUT;
		default:
			throw new RuntimeException("Found unrecognized mode "
					+ ((FlowMap) flowElement).getMode());
		}
	}

	public enum Mode {
		STANDALONE, INVOKE_INPUT, INVOKE_OUTPUT, TRANSFORMER_INPUT, TRANSFORMER_OUTPUT
	}
}
