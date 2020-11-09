package arquivo;

import java.io.BufferedReader;
import java.io.FileReader;

public class LeArquivoss {

	private String path;

	public LeArquivoss(String path) {
		this.path = path;
	}

	public void mostraLeitura(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
