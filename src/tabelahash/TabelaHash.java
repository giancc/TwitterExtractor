package tabelahash;

import java.util.LinkedList;
import java.util.List;

public class TabelaHash {
	private int codMes;
	private List<Lista> listaDatas;

	public TabelaHash() {
		listaDatas = new LinkedList<Lista>();
	}
	
	public void insereDataLista(String data, long endereco) {
		Lista l = new Lista(data, endereco);
		listaDatas.add(l);
	}

	public int getCodMes() {
		return codMes;
	}

	public void setCodMes(int codMes) {
		this.codMes = codMes;
	}

	public List<Lista> getListaDatas() {
		return listaDatas;
	}

	public void setListaDatas(List<Lista> listaDatas) {
		this.listaDatas = listaDatas;
	}
	
}
