package hx.codeReviewer.lang.wm;

import hx.codeReviewer.lang.wm.rule.WmRuleViolationFactory;
import net.sourceforge.pmd.lang.AbstractLanguageVersionHandler;
import net.sourceforge.pmd.lang.Parser;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.rule.RuleViolationFactory;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          This class implements LanguageVersionHandler.
 */
public class WmHandler extends AbstractLanguageVersionHandler {

	@Override
	public Parser getParser(ParserOptions parserOptions) {
		return new WmParser(parserOptions);
	}

	@Override
	public RuleViolationFactory getRuleViolationFactory() {
		return WmRuleViolationFactory.INSTANCE;
	}
}
