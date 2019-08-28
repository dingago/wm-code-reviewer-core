package hx.codeReviewer.lang.wm.ast;

import com.wm.lang.ns.AuditSettings;
import com.wm.lang.ns.NSService;
import com.wm.lang.ns.NSSignature;

/**
 * @author Xiaowei Wang
 * @version 1.2
 * 
 *          The abstract service node represents any webMethods node extends
 *          from class com.wm.lang.ns.NSService.
 *
 */
public abstract class AbstractNsService extends AbstractNsNode {

	public AbstractNsService(ASTPackage _package, ASTFolder parentNode,
			NSService nsService) {
		super(_package, parentNode, nsService);
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
					+ ((NSService) nsNode).getAuditOption() + " in node "
					+ getNsName());
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
				+ ((NSService) nsNode).getAuditOption() + " in node "
				+ getNsName());
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
									.isDocumentAuditEnabled() + " in node "
							+ getNsName());
		}
	}

	public PipelineDebugOption getPipelineDebug() {
		switch (((NSService) nsNode).getPipelineOption()) {
		case 0:
		case NSService.NO_PIPELINE:
			return PipelineDebugOption.NONE;
		case NSService.SAVE_PIPELINE:
			return PipelineDebugOption.SAVE;
		case NSService.RESTORE_PIPELINE_MERGE:
			return PipelineDebugOption.RESTORE_MERGE;
		case NSService.RESTORE_PIPELINE_NO_MERGE:
			return PipelineDebugOption.RESTORE_OVERRIDE;
		default:
			throw new RuntimeException(
					"Found unrecognized pipeline debug option "
							+ ((NSService) nsNode).getPipelineOption()
							+ " in node " + getNsName());
		}
	}
	
	public NSSignature getSignature(){
		return ((NSService)nsNode).getSig();
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
