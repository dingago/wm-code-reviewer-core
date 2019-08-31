package hx.codeReviewer.lang.wm.rule;

import hx.codeReviewer.lang.wm.ast.AbstractNsNode;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract class of rule which is applied to multiple kind of
 *          AbstractNsNode. Once one more kind of NsNode is supported, you could
 *          know which rules need to be updated.
 *
 */
public abstract class AbstractNsNodeRule extends AbstractWmRule{

	public abstract Object visit(AbstractNsNode node, Object data) ;
	
}
