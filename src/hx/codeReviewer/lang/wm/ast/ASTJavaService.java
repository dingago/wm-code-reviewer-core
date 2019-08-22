package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.JavaService;
/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract service node represents any webMethods node extends
 *          from class com.wm.app.b2b.server.JavaService.
 *
 */
public class ASTJavaService extends AbstractNSService {
	
	public ASTJavaService(ASTPackage _package, JavaService javaService) {
		super(_package, javaService);
	}

	@Override
	public String getXPathNodeName() {
		return "JavaService";
	}

}
