package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.flow.FlowElement;

/**
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          The abstract AST node represents any webMethods node extends from
 *          class com.wm.lang.flow.FlowElement.
 *
 */
public abstract class AbstractFlowElement extends AbstractWmNode {

	FlowElement flowElement;
	ASTFlowRoot root;

	public AbstractFlowElement(ASTPackage _package, ASTFlowRoot root,
			AbstractFlowElement parentNode, FlowElement flowElement) {
		super(_package);
		this.root = root;
		this.flowElement = flowElement;

		if (parentNode != null) {
			parentNode.jjtAddChild(this, parentNode.jjtGetNumChildren());
			this.jjtSetParent(parentNode);
		}
	}

	public static FlowType getType(String flowType) {
		switch (flowType) {
		case FlowElement.TYPE_BRANCH:
			return FlowType.BRANCH;
		case FlowElement.TYPE_EXIT:
			return FlowType.EXIT;
		case FlowElement.TYPE_INVOKE:
			return FlowType.INVOKE;
		case FlowElement.TYPE_LOOP:
			return FlowType.LOOP;
		case FlowElement.TYPE_MAP:
			return FlowType.MAP;
		case FlowElement.TYPE_MAPCOPY:
			return FlowType.LINK;
		case FlowElement.TYPE_MAPDELETE:
			return FlowType.DROP;
		case FlowElement.TYPE_MAPINVOKE:
			return FlowType.TRANSFORMER;
		case FlowElement.TYPE_MAPSET:
			return FlowType.SET;
		case FlowElement.TYPE_RETRY:
			return FlowType.REPEAT;
		case FlowElement.TYPE_ROOT:
			return FlowType.ROOT;
		case FlowElement.TYPE_SEQUENCE:
			return FlowType.SEQUENCE;
		default:
			throw new RuntimeException("Found unrecognized flow type "
					+ flowType);
		}
	}

	public String getComment() {
		return flowElement.getComment() == null ? "" : flowElement.getComment();
	}

	public String getScope() {
		return flowElement.getScope() == null ? "" : flowElement.getScope();
	}

	public long getTimeout() {
		return flowElement.getTimeout();
	}

	public String getLabel() {
		return flowElement.getName() == null ? "" : flowElement.getName();
	}

	public String getPath() {
		return flowElement.getPathByRef();
	}

	public ASTFlowRoot getRoot() {
		return root;
	}

	public FlowElement getFlowElement() {
		return flowElement;
	}

	public String getNsName() {
		return ((ASTFlowService) root.jjtGetParent()).getNsName();
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @return Whether this flow element is enable.
	 */
	public boolean isEnabled() {
		return flowElement.isEnabled();
	}

	public enum FlowType {
		BRANCH, ROOT, SEQUENCE, REPEAT, LOOP, INVOKE, EXIT, MAP, TRANSFORMER, SET, DROP, LINK
	}
}
