package hx.codeReviewer.lang.wm.ast;

import com.wm.app.b2b.server.BaseService;

/**
 * @author Xiaowei Wang
 * @version 1.1
 * 
 *          The abstract service node represents any webMethods node extends
 *          from class com.wm.app.b2b.server.BaseService.
 *
 */
public abstract class AbstractBaseService extends AbstractNsService {

	public AbstractBaseService(ASTPackage _package, BaseService baseService) {
		super(_package, baseService);
	}

	public boolean isStateless() {
		return ((BaseService) nsNode).isStateless();
	}

	public boolean isCacheResults() {
		return ((BaseService) nsNode).isCacheEnabled();
	}

	public int getCacheExpire() {
		return ((BaseService) nsNode).getCacheTTL();
	}

	public boolean isPrefetch() {
		return ((BaseService) nsNode).isPrefetchEnabled();
	}

	public XmlFormatOption getXmlFormatOption() {
		switch (((BaseService) nsNode).getXmlFormat()) {
		case BaseService.XML_FORMAT_UNSPECIFIED:
			return XmlFormatOption.UNSPECIFIED;
		case BaseService.XML_FORMAT_NODE:
			return XmlFormatOption.NODE;
		case BaseService.XML_FORMAT_BYTES:
			return XmlFormatOption.BYTES;
		case BaseService.XML_FORMAT_STREAM:
			return XmlFormatOption.STREAM;
		case BaseService.XML_FORMAT_ENHANCED:
			return XmlFormatOption.ENHANCED;
		default:

			throw new RuntimeException("Found unrecognized xml format "
					+ ((BaseService) nsNode).getXmlFormat());
		}
	}

	public int getMaxRetryAttempts() {
		return ((BaseService) nsNode).getRetryMax();
	}

	public long getRetryInterval() {
		return ((BaseService) nsNode).getRetryInterval();
	}

	public String getTemplate() {
		return ((BaseService) nsNode).getTemplate();
	}

	public enum XmlFormatOption {
		UNSPECIFIED, BYTES, NODE, STREAM, ENHANCED
	}
}
