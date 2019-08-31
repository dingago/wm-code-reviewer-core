package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.NSRecord;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class represents node com.wm.lang.ns.NSRecord.
 *
 */
public class ASTDocumentType extends AbstractNsNode {

	public ASTDocumentType(ASTPackage _package, ASTFolder parentNode,
			NSRecord nsRecord) {
		super(_package, parentNode, nsRecord);
	}

	@Override
	public String getXPathNodeName() {
		return "DocumentType";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public NSRecord getContent() {
		return (NSRecord) nsNode;
	}
}
