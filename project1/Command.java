package command;

import java.util.*;
import java.io.*;
import batch.*;
import exception.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public abstract class Command {
  public String id;
  public Command(String s) {};
  public Command() {};
  public abstract void parse(Element element) throws ProcessException;
  public abstract void execute() throws Exception;
  public abstract String describe();
}
