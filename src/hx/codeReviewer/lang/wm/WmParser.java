package hx.codeReviewer.lang.wm;

import hx.codeReviewer.lang.wm.ast.ASTFlowBranch;
import hx.codeReviewer.lang.wm.ast.ASTFlowExit;
import hx.codeReviewer.lang.wm.ast.ASTFlowInvoke;
import hx.codeReviewer.lang.wm.ast.ASTFlowLoop;
import hx.codeReviewer.lang.wm.ast.ASTFlowMap;
import hx.codeReviewer.lang.wm.ast.ASTFlowRepeat;
import hx.codeReviewer.lang.wm.ast.ASTFlowRoot;
import hx.codeReviewer.lang.wm.ast.ASTFlowSequence;
import hx.codeReviewer.lang.wm.ast.ASTFlowService;
import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTJavaService;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.ASTParsedUnit;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement;
import hx.codeReviewer.lang.wm.ast.AbstractFlowElement.FlowType;

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
import com.wm.app.b2b.server.ns.Interface;
import com.wm.lang.flow.FlowBranch;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowExit;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowLoop;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowRetry;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowSequence;
import com.wm.lang.ns.NSName;
import com.wm.util.Values;
import com.wm.util.coder.XMLCoder;

import net.sourceforge.pmd.lang.AbstractParser;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.TokenManager;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.ParseException;

/**
 * @author Xiaowei Wang
 * @version 1.2
 *
 *          This class would parse local file and convert to AST nodes.
 */
public class WmParser extends AbstractParser {
	private final static String FOLDER_NODES = "ns";
	private final static String FILE_V3 = "manifest.v3";
	private final static String FILE_IDF = "node.idf";
	private final static String FILE_NDF = "node.ndf";
	private final static String FILE_FLOW = "flow.xml";
	private final static String KEY_NAME = "name";
	private final static String KEY_NODE_NAME = "node_nsName";
	private final static String KEY_NODE_TYPE = "node_type";
	private final static String KEY_SVC_TYPE = "svc_type";
	private final static String KEY_SVC_SUBTYPE = "svc_subtype";
	private final static String TYPE_JAVA = "java";
	private final static String TYPE_FLOW = "flow";

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
		if (file.getName().equals(FILE_V3)) {
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
		try {
			manifestValues = coder.readFromFile(new File(manifestFileName));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException(e);
		}
		Manifest manifest = new Manifest(manifestValues);
		String packageName = manifestValues.getString(KEY_NAME);
		if (packageName == null || packageName.isEmpty()) {
			packageName = new File(manifestFileName).getParentFile().getName();
		}
		ASTPackage astPackage = new ASTPackage(packageName, manifest);
		/**
		 * Read nodes under ns folder.
		 */
		String nsDirectory = Paths.get(manifestFileName)
				.resolveSibling(FOLDER_NODES).toString();
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
			String ndfFileName = Paths.get(directory).resolve(FILE_NDF)
					.toString();
			File ndfFile = new File(ndfFileName);
			if (ndfFile.exists()) {
				if (ndfFile.isFile()) {
					String nsName = ((ASTFolder) parentNode)
							.getSubNodeNSName(directoryFile.getName());
					XMLCoder coder = new XMLCoder(true);
					try {
						Values ndfValues = coder.readFromFile(ndfFile);
						String nodeType = ndfValues.getString(KEY_NODE_TYPE);
						String serviceType = ndfValues.getString(KEY_SVC_TYPE);
						if (serviceType != null && !serviceType.isEmpty()) {
							switch (serviceType) {
							case TYPE_JAVA:
								JavaService javaService = JavaService.create(
										null, NSName.create(nsName), ndfValues);
								ASTJavaService astJavaService = new ASTJavaService(
										_package, parentNode, javaService);
								break;
							case TYPE_FLOW:
								FlowSvcImpl flowSvcImpl = new FlowSvcImpl(null,
										NSName.create(nsName), ndfValues);
								File flowFile = Paths.get(ndfFileName)
										.resolveSibling(FILE_FLOW).toFile();
								if (flowFile.isFile()) {
									Values flowValues;
									try {
										flowValues = flowSvcImpl
												.loadFlow(flowFile);
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
									break;
								} else {
									throw new ParseException(
											"Not found valid file "
													+ flowFile
															.getAbsolutePath());
								}
							default:
								System.err
										.println("Found unrecoginized service type "
												+ serviceType);
							}
						} else {

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
				String idfFileName = Paths.get(directory).resolve(FILE_IDF)
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
						nsName = idfValues.getString(KEY_NODE_NAME);
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
								return !name.equals(FILE_IDF);
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
				new ASTFlowInvoke(root.getPackage(), root, parentNode,
						(FlowInvoke) childElement);
			} else if (childFlowType == FlowType.EXIT) {
				new ASTFlowExit(root.getPackage(), root, parentNode,
						(FlowExit) childElement);
			} else if (childFlowType == FlowType.MAP) {
				new ASTFlowMap(root.getPackage(), root, parentNode,
						(FlowMap) childElement);
			} else {
				throw new RuntimeException("Found unsupported flow element "
						+ childFlowType.toString());
			}
		}

	}

}