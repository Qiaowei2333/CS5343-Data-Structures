package command;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class ExecCommand extends Command{
  public String path;
  public String args;
  public String in;
  public String out;
  private String inFileName;
  private String outFileName;
  public ExecCommand(String name) {};
  public void parse(Element element) throws ProcessException {
    this.id = element.getAttribute("id");
    if (id == null || id.isEmpty()) {
        throw new ProcessException("Missing ID in CMD Command");
    }
    //System.out.println("ID: " + id);
    
    this.path = element.getAttribute("path");
    if (path == null || path.isEmpty()) {
        throw new ProcessException("Missing PATH in CMD Command");
    }
    //System.out.println("Path: " + path);

    
    this.args = element.getAttribute("args");
    if (!(args == null || args.isEmpty())) {
      StringTokenizer st = new StringTokenizer(args);
      while (st.hasMoreTokens()) {
          String tok = st.nextToken();
          //System.out.println(tok);
      }
    }
    

    this.in = element.getAttribute("in");
//    if (!(in == null || in.isEmpty())) {
//        System.out.println("in= " + in);
//    }

    this.out = element.getAttribute("out");
//    if (!(out == null || out.isEmpty())) {
//        System.out.println("out= " + out);
//    }
  }
  
  
  public void execute() throws Exception {
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
    
    ProcessBuilder builder = new ProcessBuilder();
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
    Process process = builder.start();
    process.waitFor();
    System.out.println("Found " + this.path);
    //System.out.println("Program One Terminated! " + process.exitValue());
  }
  
 public void setFilePath(FilenameCommand fc) {
    if(fc !=null && fc.id.equalsIgnoreCase(this.in)) this.inFileName = fc.path;
    if(fc != null && fc.id.equalsIgnoreCase(this.out)) this.outFileName = fc.path;
  }
  @Override
  public String describe() {
    // TODO Auto-generated method stub
    return "Executing exec";
  }
}
