package it.eng.bollettini.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TriturinoL24 {
	public static void main(String[] args) throws IOException{
		JFileChooser chooser = new JFileChooser();
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			String rimuovi = JOptionPane.showInputDialog(null, "Quale stringa vuoi rimuovere dal nome?");
			String beanName = selectedFile.getName();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
				beanName = beanName.substring(0, beanName.indexOf('.'));
				List<BeanProperty> oggetto = parsingFile(path, rimuovi);
				FileWriter fw = new FileWriter(chooser.getSelectedFile().toString()+"\\"+beanName+".java");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(generateBeanFile(beanName, oggetto));
				bw.close();
			}
		}
	}

	private static List<BeanProperty> parsingFile(String path, String rimuovi) throws IOException{
		String line="";
		List <String> names = new ArrayList <String>();
		List <String> types = new ArrayList <String>();
		List<BeanProperty> beanProperties = new ArrayList<BeanProperty>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			while ((line = br.readLine()) != null) {
				String[] campo= line.split(";");
				String nome=campo[0].trim().replaceAll(rimuovi, "").toLowerCase().replace("-", "");
				names.add(nome);
				String tipo=campo[1].trim().toLowerCase();
				switch(tipo) {
				case "numeric":
					tipo="Long";
					break;
				case "numerico":
					tipo="Long";
					break;
				case "decimal":
					tipo="Double";
					break;
				case "decimale":
					tipo="Double";
					break;
				case "char":
					tipo="String";
					break;
				case "varchar":
					tipo="String";
					break;
				case "intero":
					tipo="Long";
					break;
				case "alfanumerico":
					tipo="String";
					break;
				}
				types.add(tipo);
			}
			for (int i=0; i<names.size(); i++){
				String name = names.get(i);
				String type = types.get(i);
				BeanProperty beanProperty = new BeanProperty(name, type);
				beanProperties.add(beanProperty);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return beanProperties;
	}

	static class BeanProperty{
		private final String name;
		private final String type;
		BeanProperty(String name, String type){
			this.name = name;
			this.type = type;
		}
		String getName(){
			return name;
		}
		String getType(){
			return type;
		}
	}

	static String generateBeanFile(String beanName, List<BeanProperty> beanProperties){
		StringBuilder sb = new StringBuilder();

		sb.append("public class "+beanName+"\n");
		sb.append("{"+"\n");
		for (BeanProperty beanProperty : beanProperties){
			sb.append("    private ");
			sb.append(beanProperty.getType());
			sb.append(" ");
			sb.append(decapitalize(beanProperty.getName()));
			sb.append(";"+"\n");
		}
		sb.append("\n");
		sb.append("    public "+beanName+"()"+"\n");
		sb.append("    {"+"\n");
		sb.append("        // Costruttore di Default (TriturinoL24)"+"\n");
		sb.append("    }"+"\n");
		for (BeanProperty beanProperty : beanProperties){
			sb.append("\n");
			sb.append(createSetterString(beanProperty));
			sb.append("\n");
			sb.append(createGetterString(beanProperty));
		}
		sb.append("}"+"\n");
		return sb.toString();
	}

	private static String createSetterString(BeanProperty beanProperty){
		StringBuilder sb = new StringBuilder();

		sb.append("    public void set");
		sb.append(capitalize(beanProperty.getName()));
		sb.append("(");
		sb.append(beanProperty.getType());
		sb.append(" ");
		sb.append(decapitalize(beanProperty.getName()));
		sb.append(")"+"\n");

		sb.append("    {"+"\n");

		sb.append("        this.");
		sb.append(decapitalize(beanProperty.getName()));
		sb.append(" = ");
		sb.append(decapitalize(beanProperty.getName()));
		sb.append(";"+"\n");

		sb.append("    }"+"\n");

		return sb.toString();
	}

	private static String createGetterString(BeanProperty beanProperty){
		StringBuilder sb = new StringBuilder();

		sb.append("    public ");
		sb.append(beanProperty.getType());
		sb.append(" get");
		sb.append(capitalize(beanProperty.getName()));
		sb.append("()"+"\n");

		sb.append("    {"+"\n");

		sb.append("        return ");
		sb.append(decapitalize(beanProperty.getName()));
		sb.append(";"+"\n");

		sb.append("    }"+"\n");

		return sb.toString();
	}

	private static String decapitalize(String s){
		char c = Character.toLowerCase(s.charAt(0));
		return c + s.substring(1);
	}

	private static String capitalize(String s){
		char c = Character.toUpperCase(s.charAt(0));
		return c + s.substring(1);
	}
}
