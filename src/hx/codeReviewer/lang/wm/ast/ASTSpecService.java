package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.SpecService;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract service node represents webMethods node class
 *          com.wm.app.b2b.server.SpecService.
 *
 */
public class ASTSpecService extends AbstractBaseService {

	public ASTSpecService(ASTPackage _package, ASTFolder parentNode,
			SpecService specService) {
		super(_package, parentNode, specService);
	}

	@Override
	public String getXPathNodeName() {
		return "SpecService";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
