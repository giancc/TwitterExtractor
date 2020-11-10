package tabelahash;

public class Lista {
	private String data;
	private long enderecoArquivo;
	
	public Lista(String data, long endereco) {
		this.data = data;
		this.enderecoArquivo = endereco;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getEnderecoArquivo() {
		return enderecoArquivo;
	}

	public void setEnderecoArquivo(long endereco) {
		this.enderecoArquivo = endereco;
	}

	@Override
	public String toString() {
		return "Data=" + data + ", Endereco Arquivo=" + enderecoArquivo;
	}

}
