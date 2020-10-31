package principal;
import java.io.IOException;
import java.util.Scanner;

import arquivo.Arquivo;
import twitter.PesquisaTwitter;

public class Main {

	public int menu() {
		int op;
		Scanner s = new Scanner(System.in);

		System.out.println("Escolha uma opção");
		System.out.println("1 - Extrair dados");
		System.out.println("2 - Tratar dados");
		System.out.println("0 - Sair");

		op = Integer.parseInt(s.nextLine());

		return op;
	}

	public void extracao() {
		long tempo = 16; //tempo em minutos

		tempo = tempo * 60000; //tempo definido * 60000 para passar para milissegundos

		int contExtracao = 1;

		try {
			PesquisaTwitter twitter = new PesquisaTwitter();
			while(contExtracao<=10) {

				System.out.println("Extração " +(contExtracao));

				twitter.pesquisa();
				//Thread.sleep(tempo);

				System.out.println("Extração " +(contExtracao) +" concluída!");

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
				case 2:{
					m.tratamento();
					break;
				}
				case 0:{
					break;
				}
				default:
					System.out.println("Opção inválida");
				}
		}while(op!=0);

		System.out.println("Programa finalizado!");

	}
}
