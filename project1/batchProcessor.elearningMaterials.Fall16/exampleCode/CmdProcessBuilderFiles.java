package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of using a JavaSE 7 ProcessBuilder to execute a 
 * command that reads from a file passed as a command-line 
 * argument(randomwords.txt) and write the
 * command's output to a file (sortedwords.txt).
 */
public class CmdProcessBuilderFiles
{
	public static void main(String args[]) throws Exception
	{
		List<String> command = new ArrayList<String>();
		command.add("SORT");
		command.add("/r");
		command.add("randomwords.txt");
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		
		builder.redirectError(new File("error.txt"));
		builder.redirectOutput(new File("sortedwords.txt"));

		Process process = builder.start();
		process.waitFor();
		
		System.out.println("Program terminated!");
	}
}
