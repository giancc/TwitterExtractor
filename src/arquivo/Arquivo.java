package arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

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
			dir = dir.replace("\\", "\\\\");
		}

		String caminhoArquivo = dir.concat("\\\\" +nome +".txt");
		System.out.println("Arquivo com tweets salvo em: " +caminhoArquivo);

		this.file = new File(caminhoArquivo);
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
				System.out.println(linha);
				bw.write(linha);
				bw.newLine();
//				Thread.sleep(3000);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void write(String linha) throws IOException {
		if (this.file == null) return;

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
		int faltante = 0;

		String[] vetorString = linhaArquivo.split(";");

		if(vetorString[1].length() < MAX_CARACTERES) {
			faltante = MAX_CARACTERES - vetorString[1].length();
		}

		String aux = vetorString[1];
		for(int i=0; i<faltante; i++) {
			aux = aux.concat("0");
		}

		vetorString[1] = aux;

		linhaArquivo = Arrays.toString(vetorString);

		return linhaArquivo;
	}


}
