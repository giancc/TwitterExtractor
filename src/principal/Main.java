package principal;

import java.io.IOException;
import java.util.Scanner;

import arquivo.Arquivo;
import arquivo.LeArquivo;
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
		System.out.println("5 - Consulta índice");
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
			while (contExtracao <= 4) {

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