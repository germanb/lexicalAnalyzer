import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {

		try{

			// Archivo de entrada
			File inputFile = null;
//			if ("HTML".equals(args[0])){
//				inputFile = new File("entradaHTML.txt");
//			}else if ("JAVA".equals(args[0])){
				inputFile = new File("entradaJAVA.txt");
//			}
			String input = fileToString(inputFile);
	
			LexicalAnalyzer analizer = new LexicalAnalyzer();
			ArrayList<String> result = analizer.analize(input);
			
			// TODO: revisar segun el practico
			File outputFile = new File("salida.tok");
			String array[] = new String[result.size()];
			array = result.toArray(array);
			stringToFile(arrayToString(array, "\n"), outputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String fileToString(File file) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String line;
		FileReader fReader;
		BufferedReader bReader;

		fReader = new FileReader(file);
		bReader = new BufferedReader(fReader);
		while ((line = bReader.readLine()) != null) {
			buffer.append(line+"\n");
		}
		bReader.close();
		fReader.close();

		return buffer.toString();
	}

	private static void stringToFile(String string, File file) throws Exception {
		FileWriter fWriter;
		BufferedWriter bWriter;
		fWriter = new FileWriter(file);
		bWriter = new BufferedWriter(fWriter);
		bWriter.write(string);
		bWriter.close();
		fWriter.close();
	}

	private static String arrayToString(String[] a, String separator) {
		StringBuffer result = new StringBuffer();
		if (a.length > 0) {
			result.append(a[0]);
			for (int i = 1; i < a.length; i++) {
				result.append(separator);
				result.append(a[i]);
			}
		}
		return result.toString();
	}
}