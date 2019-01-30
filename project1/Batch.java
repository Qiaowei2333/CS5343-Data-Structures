package batch;

import java.util.*;
import java.io.*;
import command.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class Batch {
  public Map<String,Command> cmdLookup;
  public List<Command> commandList;
  public void addCommand(Command command){
    if(command == null) System.out.println("command is null");
    if(commandList == null || commandList.isEmpty()) commandList = new ArrayList<Command>();
    commandList.add(command);
    if(this.cmdLookup == null || this.cmdLookup.isEmpty()) cmdLookup = new LinkedHashMap<String,Command>();
    cmdLookup.put(command.id, command);
  }
  
  public Map<String,Command> getCommands() {
    if(this.cmdLookup == null || this.cmdLookup.isEmpty()) cmdLookup = new LinkedHashMap<String,Command>();
    return cmdLookup;
  }
}
