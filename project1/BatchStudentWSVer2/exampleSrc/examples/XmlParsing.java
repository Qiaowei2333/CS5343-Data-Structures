package examples;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Example of using Java's built-in DOM XML parsing to
 * parse one of the XML batch files. 
 */
public class XmlParsing
{

	public static void main(String[] args)
	{
		try {
			String filename = null;
			if(args.length > 0) {
				filename = args[0];
			}
			else {
				filename = "work/batch1.xml";
			}
			System.out.println("Opening " + filename);
			File f = new File(filename);
			
			FileInputStream fis = new FileInputStream(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					parseCommand(elem);
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void parseCommand(Element elem) throws ProcessException
	{
		String cmdName = elem.getNodeName();
		
		if (cmdName == null) {
			throw new ProcessException("unable to parse command from " + elem.getTextContent());
		}
		else if ("filename".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing filename");
			//Command cmd = FilenameCommand.parse(elem);
			//batch.addCommand(cmd); To Be Added
		}
		else if ("exec".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing exec");
			//Command cmd = ExecCommand.parse(elem);
			//batch.addCommand(cmd); To Be Added
		}
		else if ("pipe".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing pipe");
			//Command cmd = PipecmdCommand.parse(elem);
			//batch.addCommand(cmd); To Be Added
		}
		else {
			throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
		}
	}
	
	/** 
	 * An example of parsing a CMD element 
	 * THIS LOGIC BELONGS IN INDIVIDUAL Command subclasses
	 */
	public static void parseCmd(Element elem) throws ProcessException
	{
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}
		System.out.println("ID: " + id);
		
		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}
		System.out.println("Path: " + path);

		// Arguments must be passed to ProcessBuilder as a list of
		// individual strings. 
		List<String> cmdArgs = new ArrayList<String>();
		String arg = elem.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}
		for(String argi: cmdArgs) {
			System.out.println("Arg " + argi);
		}

		String inID = elem.getAttribute("in");
		if (!(inID == null || inID.isEmpty())) {
			System.out.println("inID: " + inID);
		}

		String outID = elem.getAttribute("out");
		if (!(outID == null || outID.isEmpty())) {
			System.out.println("outID: " + outID);
		}
	}
}
