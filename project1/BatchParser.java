package batch;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class BatchParser {
  
  public Batch buildBatch(File batchFile) {
    Batch b = new Batch();
    try {
      FileInputStream fis = new FileInputStream(batchFile);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fis);

      Element pnode = doc.getDocumentElement();
      NodeList nodes = pnode.getChildNodes();
      for (int idx = 0; idx < nodes.getLength(); idx++) {
          Node node = nodes.item(idx);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
              Element elem = (Element) node;
              Command c = buildCommand(elem);
              String name = elem.getNodeName();
              b.addCommand(c);
          }
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    return b;
  }
  
  public Command buildCommand(Element elem) throws ProcessException
  {
      String cmdName = elem.getNodeName();
      
      if (cmdName == null) {
          throw new ProcessException("unable to parse command from " + elem.getTextContent());
      }
      else if ("filename".equalsIgnoreCase(cmdName) || "file".equalsIgnoreCase(cmdName)) {
          System.out.println("Parsing filename");
          Command filecommand = new FilenameCommand("filename");
          filecommand.parse(elem);
          return filecommand;
          //Command cmd = FileCommand.parse(elem);
      }
      else if ("exec".equalsIgnoreCase(cmdName) || "cmd".equalsIgnoreCase(cmdName)) {
          System.out.println("Parsing cmd");
          Command cmd = new ExecCommand("cmd");
          cmd.parse(elem);
          return cmd;
          //Command cmd = CmdCommand.parse(elem);
          //parseCmd(elem); // Example of parsing a cmd element
      }
      else if ("pipe".equalsIgnoreCase(cmdName)) {
          System.out.println("Parsing pipe");
          Command pipe = new PipecmdCommand("pipe");
          pipe.parse(elem);
          return pipe;
          //Command cmd = PipeCommand.parse(elem);
      }
      else {
          System.out.println(cmdName);
          throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
      }
  }
}
