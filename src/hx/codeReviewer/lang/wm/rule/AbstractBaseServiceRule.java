package hx.codeReviewer.lang.wm.rule;

import hx.codeReviewer.lang.wm.ast.AbstractBaseService;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract class of rule which is applied to multiple kind of
 *          AbstractBaseService. Once one more kind of service is supported, you could
 *          know which rules need to be updated.
 *
 */
public abstract class AbstractBaseServiceRule extends AbstractWmRule{

	public abstract Object visit(AbstractBaseService node, Object data) ;
	
}
