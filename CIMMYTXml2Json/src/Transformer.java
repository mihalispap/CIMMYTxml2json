import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Transformer {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        if (args.length != 2) {
            System.err.println("Usage: param1(inputdir) param2(outputdir)");                
            System.exit(1);
        } 
        
        String output=args[1];
        File file = new File(output);
		file.mkdirs();
        
        String folder_path=args[0];
        
        File folder = new File(folder_path);
        //File[] listOfFiles = folder.listFiles();

        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });
        
        //folder.list
        
        for (int i = 0; i < listOfFiles.length; i++) 
        {
        	if (listOfFiles[i].isFile()) 
        	{
        		System.out.println("File " + listOfFiles[i].getName());
        	} 
        	else if (listOfFiles[i].isDirectory()) 
        	{
        		System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
       
        for(int i=0;i<listOfFiles.length;i++)
        {
        	if(listOfFiles[i].getName().endsWith(".xml"))
        		convert2Json(listOfFiles[i].getAbsolutePath(), output, listOfFiles[i].getName());
        }
	}
	
	static void convert2Json(String filename, String output_folder, String init_fname)
	{

        try 
        {
        	String contents=readFile(filename,StandardCharsets.UTF_8);
        	
        	
        	//String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
        	
            JSONObject xmlJSONObj = XML.toJSONObject(contents);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
            
            /*
             * TODO:
             * 	check existent and enrich with values already stored
             * */
            
            /*try(FileWriter fw = new FileWriter(output_folder+System.getProperty("file.separator")+
            		init_fname.replace(".xml", ".json"), false);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.print(jsonPrettyPrintString);
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
					
				}
			*/
            
            PrintWriter writer = new PrintWriter(output_folder+System.getProperty("file.separator")+
            		init_fname.replace(".xml", ".json"), "UTF-8");
    		writer.println(jsonPrettyPrintString);
    		writer.close();
            
        } 
        catch (JSONException je) 
        {
        	je.printStackTrace();
        	System.out.println("At file: "+filename);
        	System.exit(1);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("At file: "+filename);
			System.exit(1);
		}
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

}
