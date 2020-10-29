package principal;
import twitter.PesquisaTwitter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
    	long tempo = 16; //tempo em minutos
    	
    	tempo = tempo * 60000; //tempo definido * 60000 para passar para milissegundos
    	
    	int contExtracao = 1;
    	
        try {
        	PesquisaTwitter twitter = new PesquisaTwitter();
            while(contExtracao<=10) {
            	
            	System.out.println("Extração " +(contExtracao));
                        	
            	twitter.pesquisa();
//            	Thread.sleep(tempo);
            	
            	System.out.println("Extração " +(contExtracao) +" concluída!");
            	
            	System.out.println();
            	contExtracao++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Programa finalizado!");

    }
}
