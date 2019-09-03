package hx.codeReviewer.lang.wm.rule.bestpractices;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.properties.PropertyDescriptor;
import net.sourceforge.pmd.properties.PropertyFactory;

import com.wm.lang.flow.FlowBranch;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapDelete;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.MapWmPathInfo;
import com.wm.lang.flow.sig.SignatureState;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSSignature;
import com.wm.lang.ns.Namespace;
import com.wm.lang.ns.WmPathItem;

import hx.codeReviewer.lang.wm.ast.ASTFlowLink;
import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.rule.AbstractWmRule;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          Makes sure the variable is defined before it's mapped.
 */
public class UndefinedVariableRule extends AbstractWmRule {
	private final PropertyDescriptor<Boolean> strictMode = PropertyFactory
			.booleanProperty("strictMode")
			.desc("Whether to check variable is defined in each branch before it's mapped")
			.defaultValue(false).build();

	public UndefinedVariableRule() {
		definePropertyDescriptor(strictMode);
	}

	@Override
	public Object visit(ASTFlowLink node, Object data) {
		boolean strictModeBool = getProperty(strictMode);
		// A flag to indicate whether the variable is defined in previous flow
		// elements.
		boolean variableDefined = false;
		MapWmPathInfo mapFromPath = MapWmPathInfo.create(node.getMapFrom());
		WmPathItem mapFromPathItem = mapFromPath.getPathItems()[0];

		FlowRoot flowRoot = (FlowRoot) node.getRoot().getFlowElement();
		NSSignature nsSignature = ((ASTFlowService) node.getRoot()
				.jjtGetParent()).getSignature();
		Namespace namespace = node.getPackage().getNamespace();
		SignatureState signatureState = new SignatureState(flowRoot, namespace,
				nsSignature);

		FlowElement currentFlowElement = ((AbstractFlowElement) node
				.jjtGetParent()).getFlowElement();
		if (!((FlowMap) currentFlowElement).getMode().equals(
				FlowMap.MODE_STANDALONE)) {
			// Get parent element if the FlowMap is not a stand alone one.
			currentFlowElement = currentFlowElement.getParent();
			if (currentFlowElement instanceof FlowMapInvoke) {
				// Get grand parent element if parent element is transformer.
				currentFlowElement = currentFlowElement.getParent();
			}
		}
		FlowElement[] previousFlowElements = calculatePreviousFlowElements(currentFlowElement);
		loopOnPreviousFlowElements: for (FlowElement previousFlowElement : previousFlowElements) {
			NSSignature pipelineSignature = signatureState
					.getPipelineSignature(previousFlowElement);
			NSRecord pipeline = (previousFlowElement instanceof FlowMap || previousFlowElement instanceof FlowInvoke) ? pipelineSignature
					.getOutput() : pipelineSignature.getInput();
			pipelineFields: for (NSField nsField : pipeline.getFields()) {
				if (nsField.getName().equals(mapFromPathItem.getName())
						&& nsField.getType() == mapFromPathItem.getType()) {
					if (previousFlowElement instanceof FlowInvoke) {
						FlowMap outputMap = ((FlowInvoke) previousFlowElement)
								.getOutputMap();
						for (FlowElement elementsInOutputMap : outputMap
								.getNodes()) {
							if (elementsInOutputMap instanceof FlowMapDelete) {
								MapWmPathInfo dropPath = MapWmPathInfo
										.create(((FlowMapDelete) elementsInOutputMap)
												.getField());
								WmPathItem dropPathItem = dropPath
										.getPathItems()[0];
								if (dropPathItem.getName().equals(
										mapFromPathItem.getName())
										&& dropPathItem.getType() == mapFromPathItem
												.getType()) {
									break pipelineFields;
								}
							}
						}
					}
					variableDefined = true;
					if (strictModeBool) {
						/**
						 * In strict mode, try to find the variable in other
						 * branches.
						 */
						continue loopOnPreviousFlowElements;
					} else {
						/**
						 * Not in strict mode, stop to find the variable in
						 * other branches.
						 */
						break loopOnPreviousFlowElements;
					}
				}
			}
			if (strictModeBool) {
				/**
				 * In strict mode, add violation because the variable is not
				 * found in current branch.
				 */
				addViolation(
						data,
						node,
						new String[] {
								mapFromPathItem.getName(),
								node.getPath(),
								" at previous flow element "
										+ previousFlowElement.getPathByRef()
										+ " ", node.getNsName() });
			}
		}
		if (!strictModeBool && !variableDefined) {
			addViolation(data, node, new String[] { mapFromPathItem.getName(),
					node.getPath(), " ", node.getNsName() });
		}

		return null;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param currentFlowElement
	 *            The current flow element to calculate.
	 * @return All the flow element could be previous flow element.
	 * 
	 *         This method calculates all the flow elements could be previous
	 *         element of current flow element.
	 */
	private static FlowElement[] calculatePreviousFlowElements(
			FlowElement currentFlowElement) {
		List<FlowElement> previousFlowElements = new ArrayList<FlowElement>();
		int currentIndex = currentFlowElement.getParent().indexByRef(
				currentFlowElement);
		if (currentIndex == 0) {
			previousFlowElements.add(currentFlowElement.getParent());
		} else {
			FlowElement previousFlowElement = currentFlowElement.getParent()
					.getNodeAt(currentIndex - 1);
			if (previousFlowElement.getNodeCount() == 0
					|| previousFlowElement instanceof FlowMap
					|| previousFlowElement instanceof FlowInvoke) {
				previousFlowElements.add(previousFlowElement);
			} else {
				collectLastFlowElements(previousFlowElement,
						previousFlowElements);
			}
		}
		return previousFlowElements
				.toArray(new FlowElement[previousFlowElements.size()]);
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param flowElement
	 *            The flow element to collect.
	 * @param lastFlowElements
	 *            A list to store all collected flow elements.
	 * 
	 *            This method collects all last flow elements based on the given
	 *            flow element. If could be itself if there is no child element,
	 *            or the last child if there is, or multiple elements if the
	 *            given element is a FLowBranch.
	 */
	private static void collectLastFlowElements(FlowElement flowElement,
			List<FlowElement> lastFlowElements) {
		int nodeCount = flowElement.getNodeCount();
		if (nodeCount == 0 || flowElement instanceof FlowMap
				|| flowElement instanceof FlowInvoke) {
			lastFlowElements.add(flowElement);
		} else if (flowElement instanceof FlowBranch) {
			for (FlowElement branchFlowElement : flowElement.getNodes()) {
				collectLastFlowElements(branchFlowElement, lastFlowElements);
			}
		} else {
			FlowElement lastFlowElement = flowElement.getNodeAt(nodeCount - 1);
			collectLastFlowElements(lastFlowElement, lastFlowElements);
		}
	}
}