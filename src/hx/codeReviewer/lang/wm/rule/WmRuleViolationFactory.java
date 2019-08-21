package hx.codeReviewer.lang.wm.rule;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.AbstractRuleViolationFactory;
import net.sourceforge.pmd.lang.rule.ParametricRuleViolation;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class implements RuleViolationFactory.
 */
public class WmRuleViolationFactory extends AbstractRuleViolationFactory {

	public static final WmRuleViolationFactory INSTANCE = new WmRuleViolationFactory();

	@Override
	protected RuleViolation createRuleViolation(Rule rule,
			RuleContext ruleContext, Node node, String message) {
		return new ParametricRuleViolation<Node>(rule, ruleContext, node,
				message);
	}

	@Override
	protected RuleViolation createRuleViolation(Rule rule,
			RuleContext ruleContext, Node node, String message, int beginLine,
			int endLine) {
		return null;
	}

}
