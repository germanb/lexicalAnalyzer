import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {

		try{

			// Setea las configuraciones según el lenguaje
			Configs.setConfigs(args[0]);

			// Levanta el archivo de entrada como un String
			String input = fileToString(new File(Configs.input));

			// Procesa la entrada con el analizador lexicográfico
			LexicalAnalyzer analizer = new LexicalAnalyzer();
			ArrayList<String> result = analizer.analize(input);

			// Imprime el archivo de salida
			String array[] = new String[result.size()];
			array = result.toArray(array);
			stringToFile(arrayToString(array, "\n"), new File(Configs.output));

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
		buffer.append(line+" ");
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