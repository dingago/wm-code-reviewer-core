package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.JavaService;

/**
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          The abstract service node represents webMethods node class
 *          com.wm.app.b2b.server.JavaService.
 *
 */
public class ASTJavaService extends AbstractBaseService {

	public ASTJavaService(ASTPackage _package, ASTFolder parentNode, JavaService javaService) {
		super(_package, parentNode, javaService);
	}

	@Override
	public String getXPathNodeName() {
		return "JavaService";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
