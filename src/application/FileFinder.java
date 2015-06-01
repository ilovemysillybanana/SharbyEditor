package application;

import java.io.File;
import java.io.IOException;

public class FileFinder {

	//[parent][child]
	private static File dirArray[];
	private static int counter = 0;
	private static String lastFile = "";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("HELLO");
		File director = new File("/home/jose/Desktop/");
		
		//File[] files = director.listFiles();
		
		directorFinder(director);
		
		
	}
	
	private static void directorFinder(File dir) throws IOException{
		
		File[] files = dir.listFiles();
		
		for(File file : files){
			
			if(file.isDirectory()){
				//System.out.println(file.getCanonicalPath().toString());
				dirArray[counter]=file;
				directorFinder(file);
				
			}else{
				//dirArray[counter] = file.
			}
		}
		
		counter += 1;
		//System.out.println(counter);
	}

}
