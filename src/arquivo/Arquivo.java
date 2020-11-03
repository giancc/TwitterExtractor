package arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arquivo {
	private File file;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;

	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private File f;
	private FileWriter fw;
	private BufferedWriter bw;

	public Arquivo(String nome){
		this.setaCaminho(nome);
	}

	public void setaCaminho(String nome) {
		String dir = System.getProperty("user.dir");

		if (dir.contains("\\")) {
			dir = dir.replace("\\", "\\");
		}

		dir = dir.concat("\\" +nome +".txt");

		this.file = new File(dir);
		System.out.println("Dados extraídos em:" +file.getAbsolutePath());
	}

	public void trataArquivo() {
		String linha="";

		if (this.file == null) return;

		try {

			this.f = new File(System.getProperty("user.dir")+"\\novo.txt");
			this.fw = new FileWriter(this.f, true);
			this.bw = new BufferedWriter(this.fw);

			System.out.println("Arquivo tratado em " +f.getAbsolutePath());

			fileReader = new FileReader(this.file);
			this.bufferedReader = new BufferedReader(this.fileReader);


			while(true) {
				linha = bufferedReader.readLine();
				if(linha==null) {
					break;
				}

				linha = this.trataLinhaArquivo(linha);
				//System.out.println(linha);
				bw.write(linha);
				bw.newLine();
//				Thread.sleep(3000);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void write(String linha) throws IOException {
		if (this.file == null) {
			System.out.println("ERRO");
			return;
		}

		try {
			this.fileWriter = new FileWriter(this.file, true);
			this.bufferedWriter = new BufferedWriter(this.fileWriter);

			this.bufferedWriter.write(linha);
			this.bufferedWriter.newLine();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.bufferedWriter.close();
		}
	}


	public String trataLinhaArquivo(String linhaArquivo) {
		final int MAX_CARACTERES = 280;
		final int MAX_HASHTAGS = 250;
		int faltante = 0;
		
		String res = "";

		String[] vetorString = linhaArquivo.split(";");
		
		String aux = vetorString[1];
		
		if(aux.contains("http")) {
			String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
	        
	        Matcher m = p.matcher(aux);
	        int i = 0;
	        while (m.find()) {
	        	aux = aux.replaceAll(m.group(i),"").trim();
	            i++;
	        }
//	        System.out.println(aux);
		}

		if(aux.length() < MAX_CARACTERES) {
			faltante = MAX_CARACTERES - aux.length();
		}
				
		for(int i=0; i<faltante; i++) {
			aux = aux.concat("0");
		}
		
		res = res.concat(vetorString[0]);
		res = res.concat(";");
		res = res.concat(aux);
		res = res.concat(";");
		res = res.concat(vetorString[2]);
		res = res.concat(";");
		if(vetorString.length==4) {
			aux = vetorString[3];
		}else {
			aux = "";
		}
		//-----------------------------------
		
		if(aux.length() < MAX_HASHTAGS) {
			faltante = MAX_HASHTAGS - aux.length();
		}
		
		for(int i=0; i<faltante; i++) {
			aux = aux.concat("0");
		}
		
		res = res.concat(aux);
		
		//System.out.println(res);
		
		return res;
	}


}
