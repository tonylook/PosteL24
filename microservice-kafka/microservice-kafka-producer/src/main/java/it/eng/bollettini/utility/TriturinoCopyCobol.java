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

public class TriturinoCopyCobol {
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

	 static List<BeanProperty> parsingFile(String path, String pathSalvataggio) throws IOException{
		String line="";
		List <String> names = new ArrayList <String>();
		List <String> names1 = new ArrayList <String>();
		List <String> types = new ArrayList <String>();
		List <String> types1 = new ArrayList <String>();
		List<BeanProperty> beanProperties = new ArrayList<BeanProperty>();
		List<BeanProperty> beanPropertiesOggetto = new ArrayList<BeanProperty>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			boolean continua=true;
			boolean fine=false;
			String titoloBean="";
			String numeroOccurs ="";
			while ((line = br.readLine()) != null) {
				boolean salta=false;
				boolean occurs=false;
				continua=true;
				String titolo = "";
				if(line.charAt(6)!='*') {
					if(line.toUpperCase().contains("OCCURS")) {
						titoloBean="";
						occurs=true;
						names1.clear();
						types1.clear();
						beanPropertiesOggetto.clear();
						char[] lineArray1 = line.substring(7,line.indexOf("OCCURS")).toCharArray();
						String nome ="";
						numeroOccurs ="";
						for (int i = 0; i < lineArray1.length; i++) {
							char c = lineArray1[i];
							if(c>=48 && c<=57 && !salta) {
								numeroOccurs=numeroOccurs+c;
								continue;
							}
							if ((c>=65 && c<=90) || (c>=48 && c<=57 && salta)) {
								salta = true;
								titolo=titolo+c;
							}
							titoloBean=titolo.toLowerCase().trim();
							names.add(titolo.toLowerCase().trim());
							types.add("List<"+capitalize(titolo.toLowerCase().trim())+">");
						}
					}
					while(!fine && !occurs) {
//							line = br.readLine();
							if(line != null) {
							if(line.charAt(6)!='*') {
								line=line.toUpperCase();
								if (line.contains(" PIC ")) {
									char[] lineArray1 = line.substring(7,line.indexOf(" PIC")).toCharArray();
									char[] lineArray2 = line.substring(line.indexOf("PIC")).replace("S", "").toCharArray();
									String numero ="";
									String nome ="";
									String tipo="";
									for (int i = 0; i < lineArray1.length; i++) {
										char c = lineArray1[i];
										if(c>=48 && c<=57 && !salta) {
											numero=numero+c;
											continue;
										}
										if ((c>=65 && c<=90) || (c>=48 && c<=57 && salta)) {
											salta = true;
											nome=nome+c;
										}
									}
									if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("9")) {
										if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().contains("V")) {
											tipo="Double";
										}
										tipo="Long";
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
									try {
										if(!(Integer.parseInt(numero)>Integer.parseInt(numeroOccurs))) {
											names.add(nome.toLowerCase());
											types.add(tipo);
											fine=true;
										}
									} catch (NumberFormatException e) {
										System.out.println(nome+" non occurs");
									}
									names1.add(nome);
									types1.add(tipo);
								}
							}
						}
					}
				}		

			}
			if(fine) {
				for (int i=0; i<names1.size(); i++){
					String name = names1.get(i);
					String type = types1.get(i);
					BeanProperty beanProperty = new BeanProperty(name, type);
					beanPropertiesOggetto.add(beanProperty);
				}
				FileWriter fw = new FileWriter(pathSalvataggio+"\\"+capitalize(titoloBean)+".java");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(generateBeanFile(capitalize(titoloBean), beanPropertiesOggetto));
				bw.close();
				continua=false;
			}

			if (continua) {
				if (line.contains("PIC")){
					if(line.charAt(6)!='*') {
						char[] lineArray1 = line.substring(7,line.indexOf(" PIC")).toCharArray();
						char[] lineArray2 = line.substring(line.indexOf("PIC")).replace("S", "").toCharArray();
						boolean salta=false;
						String numero ="";
						String nome ="";
						String tipo="";
						for (int i = 0; i < lineArray1.length; i++) {
							char c = lineArray1[i];
							if(c>=48 && c<=57 && !salta) {
								numero=numero+c;
								continue;
							}
							if ((c>=65 && c<=90) || (c>=48 && c<=57 && salta)) {
								salta = true;
								nome=nome+c;
							}
						}
						if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().startsWith("9")) {
							if(line.substring(line.indexOf("PIC")+3).replace("S", "").trim().contains("V")) {
								tipo="Double";
							}
							tipo="Long";
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
						names.add(nome);
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
