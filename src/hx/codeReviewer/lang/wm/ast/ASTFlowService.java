package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.FlowSvcImpl;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract service node represents webMethods node class
 *          com.wm.app.b2b.server.FlowSvcImpl.
 *
 */
public class ASTFlowService extends AbstractBaseService {

	public ASTFlowService(ASTPackage _package, ASTFolder parentNode,
			FlowSvcImpl flowSvcImpl) {
		super(_package, parentNode, flowSvcImpl);
	}

	@Override
	public String getXPathNodeName() {
		return "FlowService";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
