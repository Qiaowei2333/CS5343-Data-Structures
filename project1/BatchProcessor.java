package batch;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class BatchProcessor {
  public void executeBatch(Batch batch) throws Exception {
    Map<String, Command> map = batch.getCommands();
    Map<String, FilenameCommand> filenamePath = new LinkedHashMap<>();
    PipecmdCommand pc = null;
    for(String s: map.keySet()) {
      if(s.contains("file")) {
        FilenameCommand fc = (FilenameCommand)map.get(s);
        String des = fc.describe();
        System.out.println(des);
        filenamePath.put(fc.id, fc);
        fc.execute();
      }
      else if(s.contains("exec") || s.contains("cmd")) {
        ExecCommand ec = (ExecCommand)map.get(s);
        String des = ec.describe();
        System.out.println(des);
        if(ec.in != null) {
          if(filenamePath.containsKey(ec.in)) {
            ec.setFilePath(filenamePath.get(ec.in));
          }
          else {
            throw new ProcessException("Unable to locate IN FileCommand with id: " + ec.in);
          }
        }
        if(ec.out != null) {
          if(filenamePath.containsKey(ec.out)) {
            ec.setFilePath(filenamePath.get(ec.out));
          }
          else {
            throw new ProcessException("Unable to locate OUT FileCommand with id: " + ec.out);
          }
        }
        ec.execute();
      }
      else if(s.contains("pipe")) {
        PipecmdCommand p = (PipecmdCommand)map.get(s);
        String des = p.describe();
        System.out.println(des);
        PipecmdExeCommand cmd1 = p.cmd1;
        PipecmdExeCommand cmd2 = p.cmd2;
        if(filenamePath.get(cmd1.in) != null) {
          cmd1.setFilePath(filenamePath.get(cmd1.in));
        }
        if(filenamePath.get(cmd2.out) != null) {
          cmd2.setFilePath(filenamePath.get(cmd2.out));
        }
        p.execute();
      }
    }
  }
  
  public static void main(String[] args) throws Exception {
    try {
      String filename = null;
      if(args.length == 1) {
          filename = args[0];
          System.out.println("Opening " + filename);
      }
      else
          System.out.println("Error: accept only one file!");
      
      File batchFile = new File(filename);
      if(!batchFile.exists()){
          System.err.println("No such file" + filename);
          System.exit(-1);
      }
      try{
          BatchParser batchParser = new BatchParser();
          Batch batch = batchParser.buildBatch(batchFile);
          
          BatchProcessor batchProcessor = new BatchProcessor();
          batchProcessor.executeBatch(batch);
      }
      catch(ProcessException e){
          System.err.println("Error Processing Batch " + e.getMessage());
          e.printStackTrace();
          System.exit(-1);
      }
      
  }
  catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
  }
}

}
