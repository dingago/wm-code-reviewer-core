package hx.codeReviewer.lang.wm.rule;

import hx.codeReviewer.lang.wm.ast.ASTParsedUnit;
import hx.codeReviewer.lang.wm.ast.WmNode;
import hx.codeReviewer.lang.wm.ast.WmParserVisitor;
import hx.codeReviewer.lang.wm.ast.WmParserVisitorAdapter;

import java.util.List;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.AbstractRuleChainVisitor;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class implements RuleChainVisitor.
 */
public class WmRuleChainVisitor extends AbstractRuleChainVisitor {

	@Override
	protected void indexNodes(List<Node> nodes, RuleContext ruleContext) {
		WmParserVisitor wmParserVistor = new WmParserVisitorAdapter() {
			@Override
			public Object visit(WmNode node, Object data) {
				indexNode(node);
				return super.visit(node, data);
			}
		};

		for (final Node node : nodes) {
			try{
				wmParserVistor.visit((ASTParsedUnit) node, ruleContext);
			}catch(StackOverflowError e){
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void visit(Rule rule, Node node, RuleContext ruleContext) {
		((WmNode) node).jjtAccept((WmParserVisitor) rule, ruleContext);
	}
}
