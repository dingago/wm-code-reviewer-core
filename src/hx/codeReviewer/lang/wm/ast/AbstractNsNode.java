package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.NSNode;
import com.wm.util.Values;

/**
 * @author Xiaowei Wang
 * @version 1.4
 * 
 *          The abstract AST node represents any webMethods node extends from
 *          class com.wm.lang.ns.NSNode.
 *
 */
public abstract class AbstractNsNode extends AbstractWmNode {

	NSNode nsNode;

	public AbstractNsNode(ASTPackage _package, AbstractWmNode parentNode,
			NSNode nsNode) {
		super(_package);
		this.nsNode = nsNode;

		/**
		 * Index node in package, and associate with parent node.
		 */
		_package.indexNode(this.getNsName(), this);
		parentNode.jjtAddChild(this, parentNode.jjtGetNumChildren());
		this.jjtSetParent(parentNode);
	}

	public String getName() {
		return nsNode.getNSName().getNodeName().toString();
	}

	public String getNsName() {
		return nsNode.getNSName().getFullName();
	}

	public String getComment() {
		return nsNode.getComment();
	}

	public NodeType getType() {
		return getNodeType(nsNode.getValues());
	}

	public enum NodeType {
		NONE, UNKNOWN, DOCUMENT_TYPE, DOT_NET_SERVICE, JAVA_SERVICE, FLOW_SERVICE, CLOUD_SERVICE, XSLT_SERVICE, ODATA_SERVICE, C_SERVICE, ADAPTER_SERVICE, ADAPTER_CONNECTION, ADAPTER_LISTENER, ADAPTER_NOTIFICATION, MESSAGING_TRIGGER, JMS_TRIGGER, FF_SCHEMA, FF_DIRECTORY, SCHEMA, WSD_CONSUMER, WSD_PROVIDER, SPEC
	}

	public static String getNsName(Values nodeValues) {
		String nodeType = nodeValues.getString("node_type");
		if (nodeType == null) {
			if (nodeValues.containsKey("schema")) {
				return nodeValues.getValues("schema").getString("node_nsName");
			} else if (nodeValues.containsKey("record")) {
				return nodeValues.getValues("record").getString("node_nsName");
			}
		} else {
			return nodeValues.getString("node_nsName");
		}
		return null;
	}

	public static NodeType getNodeType(Values nodeValues) {
		if (nodeValues == null) {
			return NodeType.NONE;
		} else {
			String nodeType = nodeValues.getString("node_type");
			String serviceType = nodeValues.getString("svc_type");
			String serviceSubType = nodeValues.getString("svc_subtype");
			if (serviceType != null) {
				if (serviceType.equals("flow")) {
					return NodeType.FLOW_SERVICE;
				} else if (serviceType.equals("java")
						&& serviceSubType.equals("default")) {
					return NodeType.JAVA_SERVICE;
				} else if (serviceType.equals("AdapterService")) {
					return NodeType.ADAPTER_SERVICE;
				} else if (serviceType.equals("spec")) {
					return NodeType.SPEC;
				} else if (serviceType.equals("dotnet")) {
					return NodeType.DOT_NET_SERVICE;
				} else if (serviceType.equals("CloudService")) {
					return NodeType.CLOUD_SERVICE;
				} else if (serviceType.equals("java")
						&& serviceSubType.equals("c")) {
					return NodeType.C_SERVICE;
				}
			} else {
				if (nodeType == null) {
					if (nodeValues.containsKey("schema")) {
						return NodeType.SCHEMA;
					} else if (nodeValues.containsKey("record")) {
						return NodeType.DOCUMENT_TYPE;
					} else {
						return NodeType.UNKNOWN;
					}
				} else if (nodeType.equals("ConnectionData")) {
					return NodeType.ADAPTER_CONNECTION;
				} else if (nodeType.equals("AdapterRuntimeListener")) {
					return NodeType.ADAPTER_LISTENER;
				} else if (nodeType.equals("AdapterRuntimeNotification")) {
					return NodeType.ADAPTER_NOTIFICATION;
				} else if (nodeType.equals("Flat File Schema")) {
					return NodeType.FF_SCHEMA;
				} else if (nodeType.equals("Document Part Holder")) {
					return NodeType.FF_DIRECTORY;
				} else if (nodeType.equals("webMethods/trigger")) {
					String triggerType = nodeValues.getString("trigger_type");
					if (triggerType.equals("jms-trigger")) {
						return NodeType.JMS_TRIGGER;
					} else if (triggerType.equals("broker-trigger")) {
						return NodeType.MESSAGING_TRIGGER;
					}
				} else if (nodeType.equals("webServiceDescriptor")) {
					boolean inbound = nodeValues.getBoolean("inbound");
					if (inbound) {
						return NodeType.WSD_CONSUMER;
					} else {
						return NodeType.WSD_PROVIDER;
					}
				} else if (nodeType.equals("odataService")) {
					return NodeType.ODATA_SERVICE;
				} else if (nodeType.equals("xsltservice")) {
					return NodeType.XSLT_SERVICE;
				}
			}
		}
		return NodeType.UNKNOWN;
	}
}
