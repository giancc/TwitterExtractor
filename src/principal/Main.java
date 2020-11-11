package principal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import arquivo.Arquivo;
import arquivo.LeArquivo;
import tabelahash.TabelaHash;
import twitter.PesquisaTwitter;

public class Main {

	public int menu() {
		int op;
		Scanner s = new Scanner(System.in);

		System.out.println("Escolha uma opção");
		System.out.println("1 - Extrair dados");
		System.out.println("2 - Tratar dados");
		System.out.println("3 - Pesquisa Binaria");
		System.out.println("4 - Resposta hipótese: \"Partido mais comentado\" ");
		System.out.println("5 - Consulta índice - ID Tweet");
		System.out.println("6 - Consulta índice - HashTags");
		System.out.println("7 - Consulta data - Tabela Hash");
		System.out.println("0 - Sair");

		op = Integer.parseInt(s.nextLine());

		//		s.close();

		return op;
	}

	public void extracao() {
		long tempo = 16; // tempo em minutos

		tempo = tempo * 60000; // tempo definido * 60000 para passar para milissegundos

		int contExtracao = 1;

		try {
			PesquisaTwitter twitter = new PesquisaTwitter();
			while (twitter.getContadorTweet() <= 10000) {

				System.out.println("Extração " + (contExtracao));

				twitter.pesquisa();
				// Thread.sleep(tempo);

				System.out.println("Extração " + (contExtracao) + " concluída!");

				System.out.println();
				contExtracao++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tratamento() {
		Arquivo a = new Arquivo("extracao");
		a.trataArquivo();

	}

	public void pesquisa() throws IOException {
		Scanner s = new Scanner(System.in);
		System.out.println("Digite o código da linha a ser buscada:");
		System.out.println(Arquivo.binarySearch(s.nextLine()));
		//		s.close();
	}

	public void pesquisaIndices() {
		String path = "novo.txt";

		LeArquivo readArchive = new LeArquivo();

		try {
			readArchive.converterArquivo(path, "path.bin");
			readArchive.achaPosicao("path.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pesquisaHashtags() {
		String path = "novo.txt";

		LeArquivo readArchive = new LeArquivo();

		try {
			readArchive.converterArquivoHashtags(path, "path.bin");
			readArchive.achaPosicao("path.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void thash() {
		TabelaHash[] t = new TabelaHash[12];
		
		File f;
		RandomAccessFile randomAcessFile = null;
		
		for(int i=0; i<t.length; i++) {
			t[i] = new TabelaHash();
			t[i].setCodMes(i+1);
		}

		String linha = "";

		try {

			f = new File(System.getProperty("user.dir") + "\\novo.txt");
			randomAcessFile = new RandomAccessFile(f, "r");

			System.out.println("Arquivo tratado em " + f.getAbsolutePath());

			while (true) {
				linha = randomAcessFile.readLine();
				if (linha == null) {
					break;
				}
				long endereco = randomAcessFile.getFilePointer();
				String[] quebrado=linha.split(";");
				String dataQuebrada[] = quebrado[2].split("/");
				t[Integer.parseInt(dataQuebrada[1])-1].insereDataLista(quebrado[2], endereco);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<t.length; i++) {
			System.out.println("Mes " +t[i].getCodMes());
			for(int j=0; j<t[i].getListaDatas().size(); j++) {
				System.out.println("Data " +t[i].getListaDatas().get(j).getData() +" no endereço "+t[i].getListaDatas().get(j).getEnderecoArquivo());
			}
			
		}
		
		System.out.println("Digite o endereço do registro que quer visualizar:");
		Scanner leitor = new Scanner(System.in);
		
		try {
			randomAcessFile.seek(Long.parseLong(leitor.nextLine())-550);
			
			String[] split = randomAcessFile.readLine().split(";");
			
			String retorno = "";
			retorno = retorno.concat(split[0]);
			retorno = retorno.concat(";");
			retorno = retorno.concat(split[1].replaceAll("0", ""));
			retorno = retorno.concat(";");
			retorno = retorno.concat(split[2]);
			retorno = retorno.concat(";");
			retorno = retorno.concat(split[3].replaceAll("0", ""));
			System.out.println(retorno);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {

		Main m = new Main();
		int op;

		do {
			op = m.menu();

			switch (op) {
			case 1: {
				m.extracao();
				break;
			}
			case 2: {
				m.tratamento();
				break;
			}
			case 3: {
				m.pesquisa();
				break;
			}
			case 4: {
				Arquivo ar = new Arquivo("extracao");
				ar.retornaPartidoMaisComentado();
				break;
			}
			case 5: {
				m.pesquisaIndices();
				break;
			}
			case 6: {
				m.pesquisaHashtags();
				break;
			}
			case 7: {
				m.thash();
				break;
			}
			case 0: {
				break;
			}
			default:
				System.out.println("Opção inválida");
			}
		} while (op != 0);

		System.out.println("Programa finalizado!");

	}
}