package hx.codeReviewer.lang.wm;

import hx.codeReviewer.lang.wm.ast.ASTFolder;
import hx.codeReviewer.lang.wm.ast.ASTPackage;
import hx.codeReviewer.lang.wm.ast.ASTParsedUnit;
import hx.codeReviewer.lang.wm.ast.AbstractNsNode;
import hx.codeReviewer.lang.wm.ast.AbstractWmNode;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.wm.app.b2b.server.Manifest;
import com.wm.util.Values;
import com.wm.util.coder.XMLCoder;

import net.sourceforge.pmd.lang.AbstractParser;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.TokenManager;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.ParseException;

/**
 * @author Xiaowei Wang
 * @version 1.1
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
		if (file.getName().equals(AbstractWmNode.FILE_V3)) {
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
		String packageName = manifestValues.getString(ASTPackage.KEY_NAME);
		if (packageName == null || packageName.isEmpty()) {
			packageName = new File(manifestFileName).getParentFile().getName();
		}
		ASTPackage astPackage = new ASTPackage(packageName, manifest);

		/**
		 * Read nodes under ns folder.
		 */
		String nsDirectory = Paths.get(manifestFileName)
				.resolveSibling(AbstractWmNode.FOLDER_NODES).toString();
		File nsDirectoryFile = new File(nsDirectory);
		if (nsDirectoryFile.isDirectory()) {
			for (String subDirectoryName : nsDirectoryFile.list()) {
				String subNsDirectory = Paths.get(nsDirectory)
						.resolve(subDirectoryName).toString();
				parseNodes(astPackage, astPackage, subNsDirectory);
			}
		}
		return astPackage;
	}

	/**
	 * @author Xiaowei Wang
	 * @since 1.1
	 * @param _package The package node.
	 * @param parentNode
	 *            The parent node.
	 * @param directory
	 *            The directory to parse.
	 * 
	 *            This method reads files under ns directory and add them to
	 *            parent node.
	 */
	private void parseNodes(ASTPackage _package, AbstractWmNode parentNode, String directory) {
		File directoryFile = new File(directory);
		if (directoryFile.isDirectory()) {
			String ndfFileName = Paths.get(directory)
					.resolve(AbstractWmNode.FILE_NDF).toString();
			File ndfFile = new File(ndfFileName);
			if (ndfFile.exists()) {
				if (ndfFile.isFile()) {
				} else {
					throw new ParseException("Found invalid file "
							+ ndfFileName);
				}
			} else {
				String idfFileName = Paths.get(directory)
						.resolve(AbstractWmNode.FILE_IDF).toString();
				File idfFile = new File(idfFileName);
				String name = null;
				String nsName = null;
				if (idfFile.exists()) {
					/**
					 * Read node name from node.idf if exists.
					 */
					XMLCoder coder = new XMLCoder(true);
					try {
						Values idfValues = coder.readFromFile(new File(
								idfFileName));
						nsName = idfValues
								.getString(AbstractNsNode.KEY_NODE_NAME);
						if (nsName != null && !nsName.isEmpty()) {
							name = nsName.contains(".") ? nsName
									.substring(nsName.lastIndexOf(".") + 1)
									: nsName;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (nsName == null || name == null) {
					/**
					 * Otherwise guess node name from file name.
					 */
					if (parentNode instanceof ASTPackage) {
						nsName = directoryFile.getName();
						name = directoryFile.getName();
					} else if (parentNode instanceof ASTFolder) {
						nsName = ((ASTFolder) parentNode)
								.getSubFolderNSName(directoryFile.getName());
						name = directoryFile.getName();
					} else {
						throw new ParseException(
								"Found unrecognized parent node "
										+ parentNode.getClass().getName()
										+ " for file " + directory);
					}
				}

				ASTFolder astFolder = new ASTFolder(_package, name, nsName, null);
				_package.indexNode(nsName, astFolder);
				parentNode.jjtAddChild(astFolder,
						parentNode.jjtGetNumChildren());
				astFolder.jjtSetParent(parentNode);
				for (String subDirectoryName : directoryFile
						.list(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								return !name.equals(AbstractWmNode.FILE_IDF);
							}
						})) {
					String subDirectory = Paths.get(directory)
							.resolve(subDirectoryName).toString();
					parseNodes(_package, astFolder, subDirectory);
				}
			}
		} else {
			throw new ParseException("Found invalid directory " + directory);
		}
	}
}