package hx.codeReviewer.lang.wm;

import hx.codeReviewer.lang.wm.rule.WmRuleChainVisitor;
import net.sourceforge.pmd.lang.BaseLanguageModule;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The class defines a new language module for webMethods, and let PMD
 *          know what kind of file would be parsed as a source code file..
 */
public class WmLanguageModule extends BaseLanguageModule {

	public WmLanguageModule() {
		super("webMethods", null, "wm", WmRuleChainVisitor.class,
				"v3");
		addVersion("", new WmHandler(), true);
	}
}
