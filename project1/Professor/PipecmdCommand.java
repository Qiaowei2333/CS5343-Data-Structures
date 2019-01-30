package Project1Commands;
import Project1BatchProcess.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PipecmdCommand extends Command{
	
	private PipecmdExecCommand cmd1, cmd2;
	
	public PipecmdCommand(String id, PrintStream printStream, 
			PipecmdExecCommand cmd1, PipecmdExecCommand cmd2){
		super(id, printStream);
		this.cmd1 = cmd1;
		this.cmd2 = cmd2;
	}
	
	
	@Override
	public void describe(){
		System.out.println("****Pipecmd Command****");
	}
	
	@Override
	public void execute(Map<String, Command> commands) 
			throws ProcessException{
		
		System.out.println("Executing Command: " + getId());
		
		try{
			cmd1.execute(commands);
			Process p1 = cmd1.start();
			InputStream Is = p1.getInputStream();
			
			cmd2.execute(commands);
			Process p2 = cmd2.start();
			OutputStream os = p2.getOutputStream();
			
			int temp;
			while((temp = Is.read()) != -1){
				os.write(temp);
			}
			
			os.flush();
			os.close();
			
			System.out.println("waiting for cmd1 to exit");
			try{
				p1.waitFor();
				
				if(p1.exitValue() != 0){
					System.err.format("Warning %s Exiting with non-zero status %d\n", cmd1.getPath(), p1.exitValue());  ///?????
				}
				
				System.out.println("cmd1 has exited");
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}	
			System.out.println("waiting for cmd2 to exit");
			try{
				p2.waitFor();
				
				if(p2.exitValue() != 0){
					System.err.format("Warning %s Exiting with non-zero status %d\n", cmd2.getPath(), p2.exitValue());  ///?????
				}
				
				System.out.println("cmd2 has exited");
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		catch(IOException ex){
			throw new ProcessException(ex.getMessage(), ex);
		}	
	}
	
	@Override
	public void parse(PrintStream printStream, Element elem) 
			throws ProcessException{
		String id = elem.getAttribute("id");
		if(id == null || id.isEmpty()){
			throw new ProcessException("Missing ID in PIPE Command");
		}
		
		List<Command> pipecmdCommands = new ArrayList<Command>();
		NodeList nodes = elem.getChildNodes();
		for (int idx = 0; idx < nodes.getLength(); idx++) {
			Node node = nodes.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element pelem = (Element) node;
				Command cmd = PipecmdExecCommand.parse(printStream, pelem);
				pipecmdCommands.add(cmd);
			}
		}
		
		if(pipecmdCommands.size() != 2){
			throw new ProcessException("Exactly two commands can be executed by the pipeCommand ");
	
		}
		
		PipecmdExecCommand cmd1 = (PipecmdExecCommand)pipecmdCommands.get(0);
		PipecmdExecCommand cmd2 = (PipecmdExecCommand)pipecmdCommands.get(1);
		PipecmdCommand cmd = new PipecmdCommand(id, printStream, cmd1, cmd2);
		//return cmd;
	}

}
