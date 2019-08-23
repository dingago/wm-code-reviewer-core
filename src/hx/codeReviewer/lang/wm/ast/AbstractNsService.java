package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.AuditSettings;
import com.wm.lang.ns.NSService;

/**
 * @author Xiaowei Wang
 * @version 1.0
 * 
 *          The abstract service node represents any webMethods node extends
 *          from class com.wm.lang.ns.NSService.
 *
 */
public abstract class AbstractNsService extends AbstractNsNode {

	public AbstractNsService(ASTPackage _package, NSService nsService) {
		super(_package, nsService);
	}

	public String getSpecNsName() {
		return ((NSService) ((NSService) nsNode)).getSpecification()
				.getFullName();
	}

	public boolean isValidateInputs() {
		return ((NSService) nsNode).getInputValidatorOptions() == NSService.DEFAULT_VALIDATION;
	}

	public boolean isValidateOutputs() {
		return ((NSService) nsNode).getOutputValidatorOptions() == NSService.DEFAULT_VALIDATION;
	}

	public String getHttpUrlAlias() {
		return ((NSService) nsNode).getUrlInvokeAlias();
	}

	public AuditOption getAudit() {
		switch (((NSService) nsNode).getAuditOption()) {
		case NSService.AUDIT_ENABLE:
			return AuditOption.ALWAYS;
		case NSService.AUDIT_DISABLED:
			return AuditOption.NEVER;
		case NSService.AUDIT_ENABLE_TOPLEVEL:
			return AuditOption.TOP_LEVEL_ONLY;
		default:
			throw new RuntimeException("Found unrecognized audit option "
					+ ((NSService) nsNode).getAuditOption());
		}
	}

	public AuditLogOnOption getAuditLogOn() {
		if (((NSService) nsNode).getAuditSettings().isErrorAuditEnabled()
				&& !((NSService) nsNode).getAuditSettings()
						.isStartAuditEnabled()
				&& ((NSService) nsNode).getAuditSettings()
						.isCompleteAuditEnabled()) {
			return AuditLogOnOption.ERROR_SUCCESS;
		}

		if (((NSService) nsNode).getAuditSettings().isErrorAuditEnabled()
				&& ((NSService) nsNode).getAuditSettings()
						.isStartAuditEnabled()
				&& ((NSService) nsNode).getAuditSettings()
						.isCompleteAuditEnabled()) {
			return AuditLogOnOption.ERROR_SUCCESS_START;
		}

		if (((NSService) nsNode).getAuditSettings().isErrorAuditEnabled()
				&& !((NSService) nsNode).getAuditSettings()
						.isStartAuditEnabled()
				&& !((NSService) nsNode).getAuditSettings()
						.isCompleteAuditEnabled()) {
			return AuditLogOnOption.ERROR;
		}
		throw new RuntimeException("Found unrecognized audit log on option "
				+ ((NSService) nsNode).getAuditOption());
	}

	public AuditPipelineOption getAuditPipeline() {
		switch (((NSService) nsNode).getAuditSettings()
				.isDocumentAuditEnabled()) {
		case AuditSettings.PIPELINE_NEVER:
			return AuditPipelineOption.NEVER;
		case AuditSettings.PIPELINE_ONERROR:
			return AuditPipelineOption.ON_ERROR;
		case AuditSettings.PIPELINE_LOGALWAYS:
			return AuditPipelineOption.ALWAYS;
		default:
			throw new RuntimeException(
					"Found unrecognized audit pipeline option "
							+ ((NSService) nsNode).getAuditSettings()
									.isDocumentAuditEnabled());
		}
	}

	public enum AuditOption {
		NEVER, TOP_LEVEL_ONLY, ALWAYS
	}

	public enum AuditLogOnOption {
		ERROR, ERROR_SUCCESS, ERROR_SUCCESS_START
	}

	public enum AuditPipelineOption {
		NEVER, ON_ERROR, ALWAYS
	}

	public enum PipelineDebugOption {
		NONE, SAVE, RESTORE_OVERRIDE, RESTORE_MERGE
	}

	public enum XmlFormatOption {
		UNSPECIFIED, BYTES, NODE, STREAM, ENHANCED
	}
}
