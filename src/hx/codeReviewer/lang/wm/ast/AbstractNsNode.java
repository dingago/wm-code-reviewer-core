package hx.codeReviewer.lang.wm.ast;

/**
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          The abstract AST node represents any webMethods node extends from
 *          class com.wm.lang.ns.NSNode.
 *
 */
public abstract class AbstractNsNode extends AbstractWmNode {
	public final static String KEY_NODE_NAME = "node_nsName";

	String name;
	String nsName;
	String comment;

	public AbstractNsNode(ASTPackage _package,String name, String nsName, String comment) {
		super(_package);
		this.name = name;
		this.nsName = nsName;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public String getNsName() {
		return nsName;
	}

	public String getComment() {
		return comment;
	}

}
