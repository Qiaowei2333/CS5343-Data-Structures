package Project1Commands;
import Project1BatchProcess.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.w3c.dom.Element;

public class FilenameCommand extends Command {
	
	public File getFile(){
		return file;
	}
	
	public String getPath(){
		return path;
	}
	
	public FilenameCommand(String id, PrintStream printStream, String path){
		super(id, printStream);
		this.path = path;
	}
	
	@Override
	public void execute(Map<String, Command> commands) 
			throws ProcessException{
		file = new File(path);
	}
	
	@Override
	public void describe(){
		System.out.println("File Command on file: " + path);
	}
	
	public InputStream getInputStream() throws ProcessException{
		try{
			return new FileInputStream(file);
		}
		catch(FileNotFoundException ex){
			throw new ProcessException("Unable to open file: " + file.getAbsolutePath());
		}
	}
	
	public OutputStream getOutputStream() throws ProcessException{
		try{
			return new FileOutputStream(file);
		}
		catch(FileNotFoundException ex){
			throw new ProcessException("Unable to open file: " + file.getAbsolutePath());
		}
	}
	
	//@Override v// public static Command ?
	public void parse(PrintStream printStream, Element elem) 
			throws ProcessException{
		String id = elem.getAttribute("id");
		if(id == null || id.isEmpty()){
			throw new ProcessException("Missing ID in FILE Command");
		}
		String path = elem.getAttribute("path");
		if(path == null || path.isEmpty()){
			throw new ProcessException("Missing PATH in FILE Command");
		}
		//FilenameCommand cmd = new FilenameCommand(id, printStream, path);
		//return cmd;
	}

}
