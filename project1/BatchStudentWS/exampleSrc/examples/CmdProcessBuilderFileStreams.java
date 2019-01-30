package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of using a JavaSE 7 ProcessBuilder to execute a 
 * command that reads from a file passed as file streams.
 * The first phase sorts randomwords.txt and write the
 * command's output to the file sortedwords.txt. The second
 * phase reads sorted words and writes the reverse sort as
 * output. 
 */
public class CmdProcessBuilderFileStreams
{
	public static void main(String args[]) throws Exception
	{
		// Sort with the random words as input. 
		List<String> command = new ArrayList<String>();
		command.add("SORT");
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		
		File inFile = new File("randomwords.txt");
		builder.redirectInput(inFile);
		File outFile = new File("sortedwords.txt");
		builder.redirectOutput(outFile);

		Process process = builder.start();
		process.waitFor();
		System.out.println("Program One Terminated! " + process.exitValue());
		
		// Reverse sort with the sorted words as input. 
		command = new ArrayList<String>();
		command.add("SORT");
		command.add("/r");

		builder = new ProcessBuilder();
		builder.command(command);

		inFile = new File("sortedwords.txt");
		builder.redirectInput(inFile);
		outFile = new File("reversesortedwords.txt");
		builder.redirectOutput(outFile);

		process = builder.start();
		process.waitFor();
		System.out.println("Program Two Terminated! " + process.exitValue());
	}
}
