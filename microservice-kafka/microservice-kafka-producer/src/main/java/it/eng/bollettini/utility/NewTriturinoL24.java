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

public class NewTriturinoL24 {
	public static void main(String[] args) throws IOException{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] listOfFiles = chooser.getSelectedFile().listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].getName().toLowerCase().endsWith((".csv"))) {
					File selectedFile = listOfFiles[i];
					String path = selectedFile.getAbsolutePath();
					String beanName = selectedFile.getName();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setAcceptAllFileFilterUsed(false);
					beanName = beanName.substring(0, beanName.indexOf('.'));
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

	private static List<BeanProperty> parsingFile(String path, String pathSalvataggio) throws IOException{
		String line="";
		List <String> names = new ArrayList <String>();
		List <String> names1 = new ArrayList <String>();
		List <String> types = new ArrayList <String>();
		List <String> types1 = new ArrayList <String>();
		List<BeanProperty> beanProperties = new ArrayList<BeanProperty>();
		List<BeanProperty> beanPropertiesOggetto = new ArrayList<BeanProperty>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			boolean continua=true;
			int rigaVuota=0;
			while ((line = br.readLine()) != null) {
				continua=true;
				String titolo = "";
				String[] campo= line.split(";");
				if(campo.length<1) {
					rigaVuota++;
				}
				if(campo.length>1) {
					if(campo[1].trim().toLowerCase().equals("inizio")) {
						names1.clear();
						types1.clear();
						beanPropertiesOggetto.clear();
						titolo = campo[0].trim().toLowerCase().replace("-", "");

						boolean fine=false;
						while(!fine) {
							line = br.readLine();
							campo= line.split(";");
							if(campo.length<1) {
								rigaVuota++;
							}
							if(!campo[1].toLowerCase().equals("fine")) {
								String nome=campo[0].trim().toLowerCase().replace("-", "");
								names1.add(nome);
								if(campo.length>1) {
									String tipo1=campo[1].trim().toLowerCase();
									switch(tipo1) {
									case "numeric":
										tipo1="Long";
										break;
									case "decimal":
										tipo1="Double";
										break;
									case "char":
										tipo1="String";
										break;
									case "varchar":
										tipo1="String";
										break;
									case "date":
										tipo1="Date";
										break;
									case "time":
										tipo1="LocalTime";
										break;
									}
									types1.add(tipo1);
								}
							}
							if(campo[1].toLowerCase().equals("fine")) fine=true;
						}
					}		
				}
				if(campo.length>1) {
					if(campo[1].toLowerCase().equals("fine")) {
						names.add(titolo);
						types.add("List<"+capitalize(titolo)+">");
						for (int i=0; i<names1.size(); i++){
							String name = names1.get(i);
							String type = types1.get(i);
							BeanProperty beanProperty = new BeanProperty(name, type);
							beanPropertiesOggetto.add(beanProperty);
						}
						FileWriter fw = new FileWriter(pathSalvataggio+"\\"+capitalize(titolo)+".java");
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(generateBeanFile(capitalize(titolo), beanPropertiesOggetto));
						bw.close();
						continua=false;
					}
				}
				if (continua) {
					String nome=campo[0].trim().toLowerCase().replace("-", "");
					names.add(nome);
					if(campo.length>1) {
						String tipo=campo[1].trim().toLowerCase();
						switch(tipo) {
						case "numeric":
							tipo="Long";
							break;
						case "decimal":
							tipo="Double";
							break;
						case "char":
							tipo="String";
							break;
						case "varchar":
							tipo="String";
							break;
						case "date":
							tipo="Date";
							break;
						case "time":
							tipo="LocalTime";
							break;
						}
						types.add(tipo);
					}
				}
			}
			if(rigaVuota>0)
				JOptionPane.showMessageDialog(null, "Il file "+path+" contiene "+rigaVuota+" righe vuote");
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
