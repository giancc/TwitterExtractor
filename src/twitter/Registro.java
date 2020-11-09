package twitter;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Registro {
	private String id;
	private String texto;
	private String data;
	private String hashtags;

	public Registro() {
	}

	public Registro(String id, String texto, String data, String hashtags) {
		super();
		this.id = id;
		this.texto = texto;
		this.data = data;
		this.hashtags = hashtags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	@Override
	public String toString() {
		return this.id + ";" + this.texto + ";" + this.data + ";" + this.hashtags;
	}

	public boolean saveToFile(RandomAccessFile file) throws IOException {
		file.seek(file.length());
		file.writeUTF(id);
		file.writeUTF(this.texto);
		file.writeUTF(this.data);
		file.writeUTF(this.hashtags);
		file.writeInt(1);
		file.writeFloat(0.0f);
		return true;
	}

}
