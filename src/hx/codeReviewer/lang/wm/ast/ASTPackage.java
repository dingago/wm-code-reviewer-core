package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.Manifest;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents node com.wm.lang.ns.NSPackage.
 *
 */
public class ASTPackage extends AbstractWmNode {
	public final static String KEY_NAME = "name";
	
	String name;
	Manifest manifest;

	public ASTPackage(String name, Manifest manifest) {
		this.name = name;
		this.manifest = manifest;
	}

	public String getName() {
		return name;
	}

	public Manifest getManifest() {
		return manifest;
	}

	@Override
	public String getXPathNodeName() {
		return "Package";
	}

	@Override
    public Object jjtAccept(WmParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
