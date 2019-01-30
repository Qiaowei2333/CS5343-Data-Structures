package batch;

import java.util.*;
import java.io.*;

public class outputDirectory {
  public static void main(String[] args) throws Exception{
    List<String> command = new ArrayList<>();
    command.add("ls");
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(command);
    File outFile = new File("lsout.txt");
    builder.redirectOutput(outFile);
    Process process = builder.start();
    process.waitFor();
    System.out.println("Program One Terminated! " + process.exitValue());
  }
}
