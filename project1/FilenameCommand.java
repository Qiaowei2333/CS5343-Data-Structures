package command;

import java.util.*;
import java.io.*;
import batch.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class FilenameCommand extends Command{
  public String path;
  public FilenameCommand(String name){};
  public void parse(Element element) throws ProcessException {
    this.id = element.getAttribute("id");
    if (this.id == null || this.id.isEmpty()) {
      throw new ProcessException("Missing ID in filename Command");
    }
    this.path = element.getAttribute("path");
    if (this.path == null || this.path.isEmpty()) {
      throw new ProcessException("Missing PATH in filename Command");
    }
  }
  public void execute() {
    System.out.println("Found file: " + this.path);
  }
  @Override
  public String describe() {
    // TODO Auto-generated method stub
    return "Executing filename";
  }
}
