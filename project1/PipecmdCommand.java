package command;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PipecmdCommand extends Command{
  public PipecmdExeCommand cmd1;
  public PipecmdExeCommand cmd2;
  public PipecmdCommand() {};
  public PipecmdCommand(String name) {}; 
  public void parse(Element element) throws ProcessException {
    this.id = element.getAttribute("id");
    if(id == null || id.isEmpty()){
      throw new ProcessException("Missing ID in PIPE Command");
    }
    List<Command> pipecmdCommands = new ArrayList<Command>();
    NodeList nodes = element.getChildNodes();
    for(int i=0; i<nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) node;
          PipecmdExeCommand pcec = new PipecmdExeCommand(elem.getNodeName());
          pcec.parse(elem);
          //System.out.println(pcec.describe());
          pipecmdCommands.add(pcec);
      }
    }
    
    if(pipecmdCommands.size() != 2){
      throw new ProcessException("Exactly two commands can be executed by the pipeCommand ");
    }
    
    this.cmd1 = (PipecmdExeCommand) pipecmdCommands.get(0);
    this.cmd2 = (PipecmdExeCommand) pipecmdCommands.get(1);
  }
  public void execute() throws Exception {
    try{
      cmd1.execute();
      Process p1 = cmd1.builder.start();
      InputStream Is = p1.getInputStream();
      
      cmd2.execute();
      Process p2 = cmd2.builder.start();
      OutputStream os = p2.getOutputStream();
      
      int temp;
      while((temp = Is.read()) != -1){
          os.write(temp);
      }
      
      os.flush();
      os.close();
      
      System.out.println("waiting for cmd1 to exit");
      try{
          p1.waitFor();
          
          if(p1.exitValue() != 0){
              System.err.format("Warning %s Exiting with non-zero status %d\n", cmd1.path, p1.exitValue());  ///?????
          }
          
          System.out.println("cmd1 has exited");
      }
      catch(InterruptedException e){
          e.printStackTrace();
      }   
      System.out.println("waiting for cmd2 to exit");
      try{
          p2.waitFor();
          
          if(p2.exitValue() != 0){
              System.err.format("Warning %s Exiting with non-zero status %d\n", cmd2.path, p2.exitValue());  ///?????
          }
          
          System.out.println("cmd2 has exited");
      }
      catch(InterruptedException e) {
          e.printStackTrace();
      }
    }
    catch(IOException ex){
      throw new ProcessException(ex.getMessage(), ex);
  }   
}
//      for(int i=0; i<nodes.getLength(); i++) {
//      Node node = nodes.item(i);
//      if (node.getNodeType() == Node.ELEMENT_NODE) {
//          Element elem = (Element) node;
//          PipecmdExeCommand pcec = new PipecmdExeCommand(elem.getNodeName());
//          pcec.parse(elem);
//          System.out.println(pcec.describe());
//          pcec.execute();
//      }
//    }
  @Override
  public String describe() {
    // TODO Auto-generated method stub
    return "Executing " + this.id;
  }

}
