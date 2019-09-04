package hx.codeReviewer.lang.wm;

import hx.codeReviewer.lang.wm.ast.ASTDocumentType;
import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.ast.ASTFlowDrop;
import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.ast.ASTFlowInvoke;
import hx.codeReviewer.lang.wm.ast.ASTFlowLink;
import hx.codeReviewer.lang.wm.ast.ASTFlowLoop;
import hx.codeReviewer.lang.wm.ast.ASTFlowMap;
import hx.codeReviewer.lang.wm.ast.ASTFlowRepeat;
import hx.codeReviewer.lang.wm.ast.ASTFlowRoot;
import hx.codeReviewer.lang.wm.ast.ASTFlowSequence;
import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTFlowSet;
import hx.codeReviewer.lang.wm.ast.ASTFlowTransformer;
import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.ASTParsedUnit;
import hx.codeReviewer.lang.wm.ast.ASTSpecService;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement.FlowType;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode.NodeType;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.app.b2b.server.JavaService;
import com.wm.app.b2b.server.Manifest;
import com.wm.app.b2b.server.SpecService;
import com.wm.app.b2b.server.ns.Interface;
import com.wm.lang.flow.FlowBranch;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowExit;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowLoop;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapCopy;
import com.wm.lang.flow.FlowMapDelete;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowMapSet;
import com.wm.lang.flow.FlowRetry;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowSequence;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSRecord;
import com.wm.util.Values;
import com.wm.util.coder.XMLCoder;

import net.sourceforge.pmd.lang.AbstractParser;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.TokenManager;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.ParseException;

/**
 * @author Xiaowei Wang
 * @version 1.5
 *
 *          This class would parse local file and convert to AST nodes.
 */
public class WmParser extends AbstractParser {

	public WmParser(ParserOptions parserOptions) {
		super(parserOptions);
	}

	@Override
	public boolean canParse() {
		return true;
	}

	@Override
	public Map<Integer, String> getSuppressMap() {
		return new HashMap<Integer, String>();
	}

	@Override
	public Node parse(String fileName, Reader source) throws ParseException {
		File file = new File(fileName);
		if (file.getName().equals("manifest.v3")) {
			ASTParsedUnit astParsedUnit = new ASTParsedUnit();
			ASTPackage astPackage = parsePackage(fileName);
			astParsedUnit.jjtAddChild(astPackage, 0);
			astPackage.jjtSetParent(astParsedUnit);
			return astParsedUnit;
		}
		return null;
	}

	@Override
	protected TokenManager createTokenManager(Reader reader) {
		return null;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.0
	 * @param manifestFileName
	 *            The file name of manifest file of the package to parse.
	 * @return The ASTPackage represents package and assets inside.
	 * 
	 *         This method reads package manifest file and all other contents in
	 *         package folder to AST nodes.
	 */
	private ASTPackage parsePackage(String manifestFileName) {
		/**
		 * Parse manifest.v3 file.
		 */
		XMLCoder coder = new XMLCoder(true);
		Values manifestValues;
		Values releaseValues = null;
		try {
			manifestValues = coder.readFromFile(new File(manifestFileName));
			File releaseFile = Paths.get(manifestFileName)
					.resolveSibling("manifest.rel").toFile();
			if (releaseFile.isFile()) {
				releaseValues = coder.readFromFile(releaseFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException(e);
		}
		Manifest manifest = new Manifest(manifestValues);
		String packageName = manifestValues.getString("name");
		if ((packageName == null || packageName.isEmpty())
				&& releaseValues != null) {
			packageName = releaseValues.getString("name");
		}
		if (packageName == null || packageName.isEmpty()) {
			packageName = new File(manifestFileName).getParentFile().getName();
		}
		ASTPackage astPackage = new ASTPackage(packageName, manifest,
				releaseValues);
		/**
		 * Read nodes under ns folder.
		 */
		String nsDirectory = Paths.get(manifestFileName).resolveSibling("ns")
				.toString();
		File nsDirectoryFile = new File(nsDirectory);
		if (nsDirectoryFile.isDirectory()) {
			for (String subDirectoryName : nsDirectoryFile.list()) {
				String subNsDirectory = Paths.get(nsDirectory)
						.resolve(subDirectoryName).toString();
				parseNsNodes(astPackage, null, subNsDirectory);
			}
		}
		return astPackage;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param _package
	 *            The package node.
	 * @param parentNode
	 *            The parent node.
	 * @param directory
	 *            The directory to parse.
	 * 
	 *            This method reads files under ns directory and add them to
	 *            parent node.
	 */
	private void parseNsNodes(ASTPackage _package, ASTFolder parentNode,
			String directory) {
		File directoryFile = new File(directory);
		if (directoryFile.isDirectory()) {
			String ndfFileName = Paths.get(directory).resolve("node.ndf")
					.toString();
			File ndfFile = new File(ndfFileName);
			if (ndfFile.exists()) {
				if (ndfFile.isFile()) {
					String nsName = ((ASTFolder) parentNode)
							.getSubNodeNSName(directoryFile.getName());
					XMLCoder coder = new XMLCoder(true);
					try {
						Values ndfValues = coder.readFromFile(ndfFile);
						NodeType nodeType = AbstractNsNode
								.getNodeType(ndfValues);
						switch (nodeType) {
						case JAVA_SERVICE:
							JavaService javaService = JavaService.create(null,
									NSName.create(nsName), ndfValues);
							new ASTJavaService(_package, parentNode,
									javaService);
							break;
						case FLOW_SERVICE:
							FlowSvcImpl flowSvcImpl = new FlowSvcImpl(null,
									NSName.create(nsName), ndfValues);
							File flowFile = Paths.get(ndfFileName)
									.resolveSibling("flow.xml").toFile();
							if (flowFile.isFile()) {
								Values flowValues;
								try {
									flowValues = flowSvcImpl.loadFlow(flowFile);
								} catch (Exception e) {
									e.printStackTrace();
									throw new ParseException(e);
								}
								ASTFlowService astFlowService = new ASTFlowService(
										_package, parentNode, flowSvcImpl);
								FlowRoot flowRoot = new FlowRoot(flowValues);
								ASTFlowRoot astFlowRoot = new ASTFlowRoot(
										_package, astFlowService, flowRoot);
								parseFlowElements(astFlowRoot, astFlowRoot);
							} else {
								throw new ParseException("Failed to read "
										+ flowFile.getAbsolutePath());
							}
							break;
						case DOCUMENT_TYPE:
							NSRecord nsRecord = NSRecord.createRecord(null,
									ndfValues.getValues("record"));
							new ASTDocumentType(_package, parentNode, nsRecord);
							break;
						case SPEC:
							SpecService specService = SpecService.create(null, NSName.create(nsName), ndfValues);
							new ASTSpecService(_package, parentNode, specService);
							break;
						case UNKNOWN:
						case NONE:
							throw new ParseException("Found unrecognized node "
									+ ndfFile.getAbsolutePath());
						default:
							System.out.println("Do not support to parse node "
									+ ndfFile.getAbsolutePath()
									+ ", which type is " + nodeType.toString());
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new ParseException(e);
					}
				} else {
					throw new ParseException("Found invalid file "
							+ ndfFileName);
				}
			} else {
				String idfFileName = Paths.get(directory).resolve("node.idf")
						.toString();
				File idfFile = new File(idfFileName);
				String nsName = null;
				if (idfFile.exists()) {
					/**
					 * Read node name from node.idf if exists.
					 */
					XMLCoder coder = new XMLCoder(true);
					try {
						Values idfValues = coder.readFromFile(new File(
								idfFileName));
						nsName = AbstractNsNode.getNsName(idfValues);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (nsName == null) {
					/**
					 * Otherwise guess node name from file name.
					 */
					if (parentNode == null) {
						nsName = directoryFile.getName();
					} else {
						nsName = ((ASTFolder) parentNode)
								.getSubFolderNSName(directoryFile.getName());
					}
				}

				ASTFolder astFolder = (parentNode == null) ? (new ASTFolder(
						_package, _package,
						new Interface(NSName.create(nsName))))
						: (new ASTFolder(_package, parentNode, new Interface(
								NSName.create(nsName))));
				for (String subDirectoryName : directoryFile
						.list(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								return !name.equals("node.idf");
							}
						})) {
					String subDirectory = Paths.get(directory)
							.resolve(subDirectoryName).toString();
					parseNsNodes(_package, astFolder, subDirectory);
				}
			}
		} else {
			throw new ParseException("Found invalid directory " + directory);
		}
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.2
	 * @param root
	 *            The ASTFlowRoot object represents the root element of flow
	 *            service.
	 * @param parentNode
	 *            The parent node.
	 * 
	 *            This method converts sub-elements of parentNode to AST nodes
	 *            and add them as sub-nodes.
	 */
	private void parseFlowElements(ASTFlowRoot root,
			AbstractFlowElement parentNode) {
		FlowElement parentElement = parentNode.getFlowElement();

		for (FlowElement childElement : parentElement.getNodes()) {
			FlowType childFlowType = AbstractFlowElement.getType(childElement
					.getFlowType());
			if (childFlowType == FlowType.SEQUENCE) {
				parseFlowElements(root, new ASTFlowSequence(root.getPackage(),
						root, parentNode, (FlowSequence) childElement));
			} else if (childFlowType == FlowType.BRANCH) {
				parseFlowElements(root, new ASTFlowBranch(root.getPackage(),
						root, parentNode, (FlowBranch) childElement));
			} else if (childFlowType == FlowType.REPEAT) {
				parseFlowElements(root, new ASTFlowRepeat(root.getPackage(),
						root, parentNode, (FlowRetry) childElement));
			} else if (childFlowType == FlowType.LOOP) {
				parseFlowElements(root, new ASTFlowLoop(root.getPackage(),
						root, parentNode, (FlowLoop) childElement));
			} else if (childFlowType == FlowType.INVOKE) {
				parseFlowElements(root, new ASTFlowInvoke(root.getPackage(),
						root, parentNode, (FlowInvoke) childElement));
			} else if (childFlowType == FlowType.EXIT) {
				new ASTFlowExit(root.getPackage(), root, parentNode,
						(FlowExit) childElement);
			} else if (childFlowType == FlowType.MAP) {
				ASTFlowMap astFlowMap = new ASTFlowMap(root.getPackage(), root,
						parentNode, (FlowMap) childElement);
				
				parseFlowElements(root, astFlowMap);
			} else if (childFlowType == FlowType.TRANSFORMER) {
				parseFlowElements(root,
						new ASTFlowTransformer(root.getPackage(), root,
								(ASTFlowMap) parentNode,
								(FlowMapInvoke) childElement));
			} else if (childFlowType == FlowType.LINK) {
				new ASTFlowLink(root.getPackage(), root,
						(ASTFlowMap) parentNode, (FlowMapCopy) childElement);
			} else if (childFlowType == FlowType.SET) {
				new ASTFlowSet(root.getPackage(), root,
						(ASTFlowMap) parentNode, (FlowMapSet) childElement);
			} else if (childFlowType == FlowType.DROP) {
				new ASTFlowDrop(root.getPackage(), root,
						(ASTFlowMap) parentNode, (FlowMapDelete) childElement);
			} else {
				throw new RuntimeException("Found unsupported flow element "
						+ childFlowType.toString());
			}
		}

	}

}