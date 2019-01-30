package command;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PipecmdExeCommand extends PipecmdCommand{
  public String path;
  public String args;
  public String in;
  public String out;
  public String inFileName;
  public String outFileName;
  public ProcessBuilder builder;
  public PipecmdExeCommand(String name) {};
  public void parse(Element element){
    this.id = element.getAttribute("id");
    this.path = element.getAttribute("path");
    this.args = element.getAttribute("args");
    this.in = element.getAttribute("in") == null? null: element.getAttribute("in");
    this.out = element.getAttribute("out") == null? null : element.getAttribute("out");
    //System.out.println("id =" + id + "path =" + "path =" + path + "args =" + args + in != null? "in = " + in: "out = " + out);
  }
  
  public void execute() throws Exception {
    if(this.in == null && this.out == null) System.out.println("This is an invalid PipeExeCommand");
    List<String> cmdArgs = new ArrayList<String>();
    if(!path.startsWith("java")) {
      cmdArgs.add(path);
    }
    else {
      cmdArgs.add("java");
    }
    if (!(args == null || args.isEmpty())) {
      StringTokenizer st = new StringTokenizer(args);
      while (st.hasMoreTokens()) {
          String tok = st.nextToken();
          cmdArgs.add(tok);
      }
    }
    
    this.builder = new ProcessBuilder();
    builder.command(cmdArgs);
    //File outFile = new File("lsout.txt");
    //builder.directory(null);
    //File wd = builder.directory();
    if(inFileName != null) {
      File inFile = new File(inFileName);
      builder.redirectInput(inFile);
    }
    builder.redirectError(new File("error.txt"));
    if(outFileName != null) {
      File outFile  = new File(outFileName);
      builder.redirectOutput(outFile);
    }
    //Process process = builder.start();
    //process.waitFor();
    //System.out.println("Found " + this.path);
  }
  
  public void setFilePath(FilenameCommand fc) {
    if(fc !=null && fc.id.equalsIgnoreCase(this.in)) this.inFileName = fc.path;
    if(fc != null && fc.id.equalsIgnoreCase(this.out)) this.outFileName = fc.path;
  }
  
  public String describe() {
    return "Executing exec " + id;
  }
}
