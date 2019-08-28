package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowSequence;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowSequence.
 *
 */
public class ASTFlowSequence extends AbstractFlowElement {

	public ASTFlowSequence(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowSequence flowSequence) {
		super(_package, root, parentNode, flowSequence);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowSequence";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public ExitOnOption getExitOn() {
		switch (((FlowSequence) flowElement).getExitOn()) {
		case 0:
			return ExitOnOption.FAILURE;
		case 1:
			return ExitOnOption.SUCCESS;
		case 2:
			return ExitOnOption.DONE;
		default:
			throw new RuntimeException("Found unrecognized exit on "
					+ ((FlowSequence) flowElement).getExitOn());
		}
	}

	public enum ExitOnOption {
		FAILURE, SUCCESS, DONE
	}

}
