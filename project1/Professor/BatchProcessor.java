package Project1BatchProcess;
import Project1Commands.*;

import java.io.File;
import java.util.Map;

public class BatchProcessor {
	
	public static void main(String[] args) {
		try {
			String filename = null;
			if(args.length == 1) {
				filename = args[0];
				System.out.println("Opening " + filename);
			}
			else
				System.out.println("Error: accept only one file!");
			
			File batchFile = new File(filename);
			if(!batchFile.exists()){
				System.err.println("No such file" + filename);
				System.exit(-1);
			}
			try{
				BatchParser batchParser = new BatchParser();
				Batch batch = batchParser.buildBatch(System.out, batchFile);
				
				BatchProcessor batchProcessor = new BatchProcessor();
				batchProcessor.executeBatch(batch);
			}
			catch(ProcessException e){
				System.err.println("Error Processing Batch " + e.getMessage());
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	void executeBatch(Batch batch) throws ProcessException{
		Map<String, Command> commands = batch.getCommands();
		for(int i = 0; i < commands.size(); i++){
			commands.describe();
			commands.execute(commands);
		}
		System.out.println("Finished Batch");
	}
}
