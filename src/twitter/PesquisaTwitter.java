package twitter;

import arquivo.Arquivo;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.Map;

public class PesquisaTwitter {

	private Query query;
	private QueryResult result;
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;

	String partidos[] = {"MDB\r\n" +  
			"PTB\r\n" +  
			"PDT\r\n" +  
			"PT\r\n" +  
			"DEM\r\n" +  
			"PCdoB\r\n" +  
			"PSB\r\n" +  
			"PSDB\r\n" +  
			"PTC\r\n" +  
			"PSC\r\n" +  
			"PMN\r\n" +  
			"CIDADANIA\r\n" +  
			"PV\r\n" +  
			"AVANTE\r\n" +  
			"PP\r\n" +  
			"PSTU\r\n" +  
			"PCB\r\n" +  
			"PRTB\r\n" +  
			"DC\r\n" +  
			"PCO\r\n" +  
			"PODE\r\n" +  
			"PSL\r\n" +  
			"REPUBLICANOS \r\n" +  
			"PSOL\r\n" +  
			"PL\r\n" +  
			"PSD\r\n" +  
			"PATRIOTA\r\n" +  
			"PROS\r\n" +  
			"SOLIDARIEDADE\r\n" +  
			"NOVO\r\n" +  
			"REDE\r\n" +  
			"PMB\r\n" +  
	"UP\r\n"};

	public PesquisaTwitter() {

		this.cb = new ConfigurationBuilder();
		this.cb.setDebugEnabled(true)
		.setOAuthConsumerKey("0lpJb47xzdWKv00wKXTwmKatr")
		.setOAuthConsumerSecret("vcyyQS78WUWiWmyc99g1dg5uKJYPRn0wthgGPLA1tvclchU3RF")
		.setOAuthAccessToken("1539801186-AMheQp7cf7DcTEJCAM3gmDEt6XSSXO87m4mtEXr")
		.setOAuthAccessTokenSecret("xFPETLICDCWHdx2ULsvBZivoFQWNPCS5lMZ2x834Wxx3o");
		this.tf = new TwitterFactory(cb.build());
		this.twitter = tf.getInstance();
	}

	public void pesquisa() {

		try {			
			Query query = new Query("eleicoes2020");
			query.setCount(100);

			int searchResultCount;
			long lowestTweetId = Long.MAX_VALUE;
			Arquivo arquivo = new Arquivo();
			do {
				QueryResult queryResult = twitter.search(query);

				searchResultCount = queryResult.getTweets().size();

				for (Status tweet : queryResult.getTweets()) {

					String data = tweet.getCreatedAt().toString();
					String mes = data.split(" ")[1].trim();
					String codMes;

					if(mes.equals("Jan")) {
						codMes = "01";
					} else if(mes.equals("Feb")) {
						codMes = "02";
					} else if(mes.equals("Mar")) {
						codMes = "03";
					} else if(mes.equals("Apr")) {
						codMes = "04";
					} else if(mes.equals("May")) {
						codMes = "05";
					} else if(mes.equals("Jun")) {
						codMes = "06";
					} else if(mes.equals("Jul")) {
						codMes = "07";
					} else if(mes.equals("Aug")) {
						codMes = "08";
					} else if(mes.equals("Sep")) {
						codMes = "09";
					} else if(mes.equals("Oct")) {
						codMes = "10";
					} else if(mes.equals("Nov")) {
						codMes = "11";
					} else {
						codMes = "12";
					}

					String dataFormatada = data.split(" ")[2]+"/"+codMes+"/"+data.split(" ")[5];

					String conteudoTweet = tweet.getText();
					conteudoTweet = conteudoTweet.replaceAll("\n", "").replaceAll("\r", "");
					HashtagEntity[] hashTag = tweet.getHashtagEntities();
					StringBuilder hashTags = new StringBuilder("(");

					if(hashTag.length > 0 ) {
						for (int i = 0; i < hashTag.length; i++) {
							hashTags.append(hashTag[i].getText());

							if ( (i+1)  != hashTag.length) {
								hashTags.append(",");
							}
						}
					}
					hashTags.append(")");

					arquivo.write(tweet.getId()+";"+conteudoTweet+";"+ dataFormatada+";"+hashTags);

					if (tweet.getId() < lowestTweetId) {
						lowestTweetId = tweet.getId();
						query.setMaxId(lowestTweetId);
					}
				}

			} while (searchResultCount != 0 && searchResultCount % 100 == 0);

		} catch(TwitterException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void limites() {
		Map<String, RateLimitStatus> rateLimitStatus;

		try {
			rateLimitStatus = twitter.getRateLimitStatus();

			for (String endpoint : rateLimitStatus.keySet()) {
				RateLimitStatus status = rateLimitStatus.get(endpoint);
				System.out.println("Endpoint: " + endpoint);
				System.out.println("Limit: " + status.getLimit());
				System.out.println("Remaining: " + status.getRemaining());
				System.out.println("ResetTimeInSeconds: " + status.getResetTimeInSeconds());
				System.out.println("SecondsUntilReset: " + status.getSecondsUntilReset());

				System.out.println();
				System.out.println();
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

}
