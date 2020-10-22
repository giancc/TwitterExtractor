package arquivo;

import java.io.*;
import java.net.URL;

public class Arquivo {
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public Arquivo() {
        this.open();
    }

    public void open() {
    	String dir = System.getProperty("user.dir");

		if (dir.contains("\\")) {
			dir = dir.replace("\\", "\\\\");
		}

		String caminhoArquivo = dir.concat("\\\\extracao.txt");

		System.out.println("Arquivo com tweets salvo em: " +caminhoArquivo);
    	this.file = new File(caminhoArquivo);
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
    
}
