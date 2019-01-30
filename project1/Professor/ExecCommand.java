package Project1Commands;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import Project1BatchProcess.*;

public class ExecCommand extends Command {
	//private String id;
	private String inID;
	private String outID;
	//private String path;
	private String executablePath;
	private List<String> args;
	
	public String getInID(){
		return inID;
	}
	
	public String getOutID(){
		return outID;
	}
	
	public String getPath(){
		return executablePath;
	}
	
	public void setArgs(List<String> args){
		this.args = args;
	}
	
	public void setInID(String inID){
		this.inID = inID;
	}
	
	public void setOutID(String outID){
		this.outID = outID;
	}
	
	@Override
	public void describe(){
		System.out.println("Comand: " + getId());
	}
	
	@Override
	public void parse(PrintStream printStream, Element elem) 
			throws ProcessException{
	
	}
	
	public ExecCommand(String id, PrintStream printStream, String executablePath){
		super(id, printStream);
		this.executablePath = executablePath;
	}
	
	@Override
	public void execute(Map<String, Command> commands) 
			throws ProcessException{
		System.out.println("Executing Comamnd: " + getId());
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(null);
		processBuilder.redirectError(new File(id + "error.txt"));
		
		try{
			if(inID != null){
				FileCommand fileCommand = (FileCommand) commands.get(inID);
				/*if(fileCommand == null){
					throw new ProcessException("Unable to locate IN FileCommand with")
				}
				*/
				File file = fileCommand.getFile();
				processBuilder.redirectInput(file);
			}
			
			if(outID != null){
				FileCommand fileCommand = (FileCommand) commands.get(outID);
				/*if(fileCommand == null){
					throw new ProcessException("Unable to locate OUT FileCommand with")
				}
				*/
				File file = fileCommand.getFile();
				processBuilder.redirectOutput(file);
			}
			
			args.add(0, executablePath);
			processBuilder.command(args);
			
			Process process = processBuilder.start();
			try{
				process.waitFor();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			
			if(process.exitValue() != 0){
				System.err.println("Warning Cmd Exiting with non-zero status" + process);
			}
		}
		catch(IOException ex){
			throw new ProcessException(ex.getMessage(), ex);
		}
	}
}
