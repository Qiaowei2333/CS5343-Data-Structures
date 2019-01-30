package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of using a JavaSE 7 ProcessBuilder to execute a 
 * JAVA executable Jar that reads from files passed as file streams.
 * The first phase uses addLines.jar to sum the contents of numberdata.txt and write the
 * JAR's output to the file sumout.txt. The second phase executes
 * the executable JAR avgFile.jar on sumout.txt and writes the average as
 * output to the file avgout.txt.
 */
public class CmdProcessBuilderJavaFileStreams
{
	public static void main(String args[]) throws Exception
	{
		// Execute addLines.jar reading numberdata.txt and outputting to sumout.txt
		List<String> command = new ArrayList<String>();
		command.add("java");
		command.add("-jar");
		command.add("addLines.jar");
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		
		File inFile = new File("numberdata.txt");
		builder.redirectInput(inFile);
		File outFile = new File("sumout.txt");
		builder.redirectOutput(outFile);

		Process process = builder.start();
		process.waitFor();
		System.out.println("Program One Terminated! " + process.exitValue());
		
		// Execute avgLines.jar reading sumout.txt and outputting to avgout.txt
		command = new ArrayList<String>();
		command.add("java");
		command.add("-jar");
		command.add("avgFile.jar");

		builder = new ProcessBuilder();
		builder.command(command);

		inFile = new File("sumout.txt");
		builder.redirectInput(inFile);
		outFile = new File("avgout.txt");
		builder.redirectOutput(outFile);

		process = builder.start();
		process.waitFor();
		System.out.println("Program Two Terminated! " + process.exitValue());
	}
}
