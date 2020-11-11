package arquivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import twitter.Registro;

public class LeArquivo {
	public Registro criaRegistro(String[] data) {
		Registro novo;

		String id = data[0];
		String texto = data[1];
		String date = data[2];
		String hashtags = data[3];
		try {
			novo = new Registro(id, texto, date, hashtags);
		} catch (NumberFormatException e) {
			System.out.println("Erro nas informações " + data[1]);
			novo = null;
		}
		return novo;
	}

	public Registro achaRegistro(RandomAccessFile file, long pos) {
		try {

			Registro aux = null;

			file.seek(pos);

			String id = file.readUTF();
			String texto = file.readUTF();
			String date = file.readUTF();
			String hashtags = file.readUTF();

			aux = new Registro(id, texto, date, hashtags);
			return aux;

		} catch (IOException e) {
			System.out.println("Erro na leitura:" + e.getMessage());
			return null;
		}

	}

	public void converterArquivo(String arqTexto, String arqBin) throws IOException {

		File arqEntrada = new File(arqTexto);
		Scanner leitor = new Scanner(arqEntrada);

		int contador = 0;
		try {

			File arqSaida = new File(arqBin);
			RandomAccessFile dadosSaida = new RandomAccessFile(arqSaida, "rw");
			dadosSaida.setLength(0);
			dadosSaida.writeInt(0);

			while (leitor.hasNextLine()) {
				String linha = leitor.nextLine();
				String[] dados = linha.split(";");
				Registro novo = criaRegistro(dados);
				long pos = dadosSaida.getFilePointer();
				System.out.println("Registro ID " + novo.getId() + " na posição " + pos);
				novo.saveToFile(dadosSaida);
				contador++;

			}
			leitor.close();

			dadosSaida.seek(0);
			dadosSaida.writeInt(contador);
			dadosSaida.close();

		} catch (FileNotFoundException e) {
			System.out.println("Aconteceu um problema: " + e.getMessage());
		}
	}
	
	
	public void converterArquivoHashtags(String arqTexto, String arqBin) throws IOException {

		File arqEntrada = new File(arqTexto);
		Scanner leitor = new Scanner(arqEntrada);

		int contador = 0;
		try {

			File arqSaida = new File(arqBin);
			RandomAccessFile dadosSaida = new RandomAccessFile(arqSaida, "rw");
			dadosSaida.setLength(0);
			dadosSaida.writeInt(0);

			while (leitor.hasNextLine()) {
				String linha = leitor.nextLine();
				
				String[] dados = linha.split(";");
				Registro novo = criaRegistro(dados);
				long pos = dadosSaida.getFilePointer();
				System.out.println("HashTag: " + novo.getHashtags().replace("0", "") + ", na posição " + pos);
				novo.saveToFile(dadosSaida);
				contador++;

			}
			leitor.close();

			dadosSaida.seek(0);
			dadosSaida.writeInt(contador);
			dadosSaida.close();

		} catch (FileNotFoundException e) {
			System.out.println("Aconteceu um problema: " + e.getMessage());
		}
	}

	public void achaPosicao(String path) throws IOException {
		File arqLeitura = new File(path);
		RandomAccessFile dadosLeitura = new RandomAccessFile(arqLeitura, "rw");
		int contRegistros = dadosLeitura.readInt();
		System.out.println("Total de registros: " + contRegistros);
		Scanner teclado = new Scanner(System.in);
		System.out.print("Digite a posição do arquivo para busca: ");
		long posicao = Long.parseLong(teclado.nextLine());
		Registro teste = achaRegistro(dadosLeitura, posicao);
		System.out.println(teste);
		dadosLeitura.close();
//		teclado.close();
	}
}
