package hx.codeReviewer.lang.wm.ast;

import com.softwareag.util.IDataMap;
import com.wm.data.IData;
import com.wm.lang.flow.FlowExit;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowExit.
 *
 */
public class ASTFlowExit extends AbstractFlowElement {

	public ASTFlowExit(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowExit flowExit) {
		super(_package, root, parentNode, flowExit);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowExit";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getExitFrom() {
		return ((FlowExit) flowElement).getExitFrom();
	}

	public SignalOption getSignal() {
		switch (((FlowExit) flowElement).getSignal()) {
		case "FAILURE":
			return SignalOption.FAILURE;
		case "SUCCESS":
			return SignalOption.SUCCESS;
		default:
			throw new RuntimeException("Found unrecognized signal "
					+ ((FlowExit) flowElement).getSignal());
		}
	}

	public String getFailureMessage() {
		/**
		 * Retrieve failure message from IData because v9.9 has a misspell issue
		 * which might be fixed later.
		 */
		IData data = ((FlowExit) flowElement).getAsData();
		IDataMap dataMap = new IDataMap(data);
		return dataMap.getAsString(FlowExit.KEY_EXIT_FAILURE_MESSAGE);
	}

	public enum SignalOption {
		FAILURE, SUCCESS
	}
}
