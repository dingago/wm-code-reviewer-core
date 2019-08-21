package hx.codeReviewer.lang.wm.ast;

/**
 * 
 * @author Xiaowei Wang
 * @version 1.0
 *
 *          This is wrapper AST node returned by WmParser and pass to
 *          WmRuleChainVisitor for processing.
 */
public class ASTParsedUnit extends AbstractWmNode {

	@Override
	public String getXPathNodeName() {
		return "Parsed";
	}

	@Override
	public Object jjtAccept(WmParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
