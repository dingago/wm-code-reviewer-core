package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowRetry;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents flow element com.wm.lang.flow.FlowRetry.
 *
 */
public class ASTFlowRepeat extends AbstractFlowElement {

	public ASTFlowRepeat(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowRetry flowRetry) {
		super(_package, root, parentNode, flowRetry);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowRepeat";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public int getCount() {
		int count = 0;
		try {
			count = Integer
					.parseInt(((FlowRetry) flowElement).getCountString());
		} catch (Exception e) {
		}
		return count;
	}

	public RepeatOnOption getRepeatOn() {
		switch (((FlowRetry) flowElement).getRepeatOn()) {
		case "FAILURE":
			return RepeatOnOption.FAILURE;
		case "SUCCESS":
			return RepeatOnOption.SUCCESS;
		default:
			throw new RuntimeException("Found unrecognized repeat on "
					+ ((FlowRetry) flowElement).getRepeatOn());
		}
	}

	public long getRepeatInterval() {
		long interval = 0;
		try {
			interval = Long.parseLong(((FlowRetry) flowElement)
					.getBackoffString());
		} catch (Exception e) {
		}
		return interval;
	}

	public enum RepeatOnOption {
		FAILURE, SUCCESS
	}

}
