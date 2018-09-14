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

import it.eng.bollettini.utility.TriturinoCopyCobol.BeanProperty;

public class TriturinoCopy {
	public static void main(String[] args) throws IOException{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] listOfFiles = chooser.getSelectedFile().listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].getName().toLowerCase().endsWith((".txt"))) {
					File selectedFile = listOfFiles[i];
					String path = selectedFile.getAbsolutePath();
					String beanName = selectedFile.getName();
					beanName = capitalize(selectedFile.getName().substring(0, beanName.indexOf('.')).toLowerCase());
					String pathSalvataggio = chooser.getSelectedFile().toString();
					List<BeanProperty> oggetto = parsingFile(path, pathSalvataggio);
					FileWriter fw = new FileWriter(pathSalvataggio+"\\"+beanName+".java");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(generateBeanFile(beanName, oggetto));
					bw.close();
				}
			}
		}
	}

	static BeanProperty occursObject(String line) {

		return null;
	}
	static List<BeanProperty> parsingFile(String path, String pathSalvataggio) throws IOException{
		String line="";
		List <String> names = new ArrayList <String>();
		List <String> names1 = new ArrayList <String>();
		List <String> types = new ArrayList <String>();
		List <String> types1 = new ArrayList <String>();
		List<BeanProperty> beanProperties = new ArrayList<BeanProperty>();
		List<BeanProperty> beanPropertiesOccurs = new ArrayList<BeanProperty>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			Integer numeroOccurs = null;
			Integer numero1 = null;
			String tipoOccurs = null;
			String tipo1 = null;
			String tipo = null;
			String nome1 = null;
			String nome = null;
			boolean inOccurs=false;
			boolean fine=false;
			while ((line = br.readLine()) != null) {
				if(line.charAt(6)!='*') {
					if(line.toUpperCase().contains("OCCURS")) {
						String lineArr[]=line.substring(7).trim().split(" ");
						numeroOccurs=Integer.parseInt(lineArr[0]);
						inOccurs=true;
						names1.clear();
						types1.clear();
						beanPropertiesOccurs.clear();
						tipoOccurs=lineArr[1].toLowerCase().replace("-","").trim();
						names.add(tipoOccurs.toLowerCase().trim());
						types.add("List<"+capitalize(tipoOccurs.toLowerCase().replace("-","").trim())+">");
					}
				}
				if(inOccurs) {
					if (line.contains(" PIC ")) {
						String lineArr[]=line.substring(7).trim().split(" ");
						numero1=Integer.parseInt(lineArr[0]);
						nome1=lineArr[1];
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("9")) {
							if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().replace("VALUE", "").contains("V")) {
								tipo1="Double";
							}
							tipo1="Long";
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("X")) {
							tipo1="String";
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("D")) {
							tipo1="Date";
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("T")) {
							tipo1="LocalTime";
						}
						if(numero1.intValue()>numeroOccurs.intValue()) {
							names1.add(nome1.toLowerCase().replace("-","").trim());
							types1.add(tipo1);
						}else {
							names.add(nome1.replace("-", "").toLowerCase().trim());
							types.add(tipo1);
							fine=true;
							inOccurs=false;
						}
						if(fine) {
							for (int i=0; i<names1.size(); i++){
								String name = names1.get(i);
								String type = types1.get(i);
								BeanProperty beanProperty = new BeanProperty(name, type);
								beanPropertiesOccurs.add(beanProperty);
							}
							FileWriter fw = new FileWriter(pathSalvataggio+"\\"+capitalize(tipoOccurs.toLowerCase())+".java");
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(generateBeanFile(capitalize(tipoOccurs.toLowerCase()), beanPropertiesOccurs));
							bw.close();
							fine=false;
						}
					}
				}else if (line.contains("PIC")){
					if(line.charAt(6)!='*') {
						String lineArr[]=line.substring(7).trim().split(" ");
						nome=lineArr[1];
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("9")) {
							tipo="Long";
							if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().replace("VALUE", "").contains("V")) {
								tipo="Double";
							}
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("X")) {
							tipo="String";
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("D")) {
							tipo="Date";
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("T")) {
							tipo="LocalTime";
						}
						names.add(nome.toLowerCase().replace("-","").trim());
						types.add(tipo);
					}
				}
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

	private static String capitalize(String s){
		char c = Character.toUpperCase(s.charAt(0));
		return c + s.substring(1);
	}

	private static String decapitalize(String s){
		char c = Character.toLowerCase(s.charAt(0));
		return c + s.substring(1);
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
}
