package arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
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

	public Arquivo(String nome) {
		this.setaCaminho(nome);
	}

	public void setaCaminho(String nome) {
		String dir = System.getProperty("user.dir");

		if (dir.contains("\\")) {
			dir = dir.replace("\\", "\\");
		}

		dir = dir.concat("\\" + nome + ".txt");

		this.file = new File(dir);
		System.out.println("Dados extraídos em:" + file.getAbsolutePath());
	}

	public void trataArquivo() {
		String linha = "";

		if (this.file == null)
			return;

		try {

			this.f = new File(System.getProperty("user.dir") + "\\novo.txt");
			this.fw = new FileWriter(this.f, true);
			this.bw = new BufferedWriter(this.fw);

			System.out.println("Arquivo tratado em " + f.getAbsolutePath());

			fileReader = new FileReader(this.file);
			this.bufferedReader = new BufferedReader(this.fileReader);

			while (true) {
				linha = bufferedReader.readLine();
				if (linha == null) {
					break;
				}

				linha = this.trataLinhaArquivo(linha);
				// System.out.println(linha);
				bw.write(linha);
				bw.newLine();
				//				Thread.sleep(3000);
			}
		} catch (Exception e) {
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
		} catch (Exception e) {
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

		if (aux.contains("http")) {
			String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
			Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);

			Matcher m = p.matcher(aux);
			int i = 0;
			while (m.find()) {
				aux = aux.replaceAll(m.group(i), "").trim();
				i++;
			}
			//	        System.out.println(aux);
		}

		if (aux.length() < MAX_CARACTERES) {
			faltante = MAX_CARACTERES - aux.length();
		}

		for (int i = 0; i < faltante; i++) {
			aux = aux.concat("0");
		}

		res = res.concat(vetorString[0]);
		res = res.concat(";");
		res = res.concat(aux);
		res = res.concat(";");
		res = res.concat(vetorString[2]);
		res = res.concat(";");
		if (vetorString.length == 4) {
			aux = vetorString[3];
		} else {
			aux = "";
		}
		// -----------------------------------

		if (aux.length() < MAX_HASHTAGS) {
			faltante = MAX_HASHTAGS - aux.length();
		}

		for (int i = 0; i < faltante; i++) {
			aux = aux.concat("0");
		}

		res = res.concat(aux);

		// System.out.println(res);

		return res;
	}

	public static List<String> binarySearch(String string) {
		List<String> result = new ArrayList<String>();
		try {
			File file = new File("novo.txt");
			RandomAccessFile raf = new RandomAccessFile(file, "r");

			long low = 0;
			long high = file.length();

			long p = -1;
			while (low < high) {
				long mid = (low + high) / 2;
				p = mid;
				while (p >= 0) {
					raf.seek(p);

					char c = (char) raf.readByte();
					// System.out.println(p + "\t" + c);
					if (c == '\n')
						break;
					p--;
				}
				if (p < 0)
					raf.seek(0);
				String line = raf.readLine();
				// System.out.println("-- " + mid + " " + line);
				if (line.compareTo(string) < 0)
					low = mid + 1;
				else
					high = mid;
			}

			p = low;
			while (p >= 0) {
				raf.seek(p);
				if (((char) raf.readByte()) == '\n')
					break;
				p--;
			}

			if (p < 0)
				raf.seek(0);

			while (true) {
				String line = raf.readLine();
				if (line == null || !line.startsWith(string))
					break;
				String retorno = "";
				String[] split = line.split(";");

				retorno = retorno.concat(split[0]);
				retorno = retorno.concat(";");
				retorno = retorno.concat(split[1].replaceAll("0", ""));
				retorno = retorno.concat(";");
				retorno = retorno.concat(split[2]);
				retorno = retorno.concat(";");
				retorno = retorno.concat(split[3].replaceAll("0", ""));

				result.add(retorno);
			}

			raf.close();
		} catch (IOException e) {
			System.out.println("IOException:");
			e.printStackTrace();
		}

		return result;
	}

	public void retornaPartidoMaisComentado() {
		String partidos[][] = {
				{"MDB", "0"}, 
				{"PTB", "0"  },
				{"PDT", "0"},  
				{"PT", "0"},  
				{"DEM", "0"},  
				{"PCdoB", "0"},  
				{"PSB", "0"},  
				{"PSDB", "0"},  
				{"PTC", "0"},  
				{"PSC", "0"},  
				{"CIDADANIA", "0"},  
				{"AVANTE", "0"},  
				{"PP", "0"},  
				{"PSTU", "0"},  
				{"PCB", "0"},  
				{"PRTB", "0"},  
				{"DC", "0"},  
				{"PCO", "0"},  
				{"PODE", "0"},  
				{"PSL", "0"},  
				{"REPUBLICANOS", "0"},  
				{"PSOL", "0"},  
				{"PL", "0"},  
				{"PATRIOTA", "0"},  
				{"PROS", "0"},  
				{"SOLIDARIEDADE", "0"},  
				{"NOVO", "0"},  
				{"REDE", "0"},  
				{"PMB", "0"},  
				{"UP", "0"},  
		};

		String linha="";

		try {

			this.f = new File(System.getProperty("user.dir") + "\\extracao.txt");
			this.fw = new FileWriter(this.f, true);
			this.bw = new BufferedWriter(this.fw);

			System.out.println("Arquivo tratado em " + f.getAbsolutePath());

			fileReader = new FileReader(this.file);
			this.bufferedReader = new BufferedReader(this.fileReader);
			int c = 0;
			while (true) {
				linha = bufferedReader.readLine();
				if (linha == null) {
					break;
				}
				for(int i=0; i<partidos.length; i++) {
					if(linha.toUpperCase().contains(partidos[i][0])) {
						partidos[i][1] = Integer.toString(Integer.parseInt(partidos[i][1]) + 1) ;
					}
				}
				System.out.println(++c);
			}
			fw.close();
			bw.close();
		} catch (IOException e) {
			System.out.println("IOException:");
			e.printStackTrace();
		}

		for(int i=0; i<partidos.length; i++) {
			System.out.println("Partido " +partidos[i][0] +" Contagem: "+ partidos[i][1]);
		}
		
		int maior = Integer.parseInt(partidos[0][1]);
		int indice=0;
		for(int i=0; i<partidos.length; i++) {
			if(Integer.parseInt(partidos[i][1]) > maior) {
				maior = Integer.parseInt(partidos[i][1]);
				indice = i;
			}
		}
		System.out.println();
		System.out.println("Partido mais comentado: " +partidos[indice][0]);
		System.out.println();
	}

}