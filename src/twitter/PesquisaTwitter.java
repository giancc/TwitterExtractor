package twitter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import arquivo.Arquivo;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PesquisaTwitter {

//	private Query query;
//	private QueryResult result;
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;

//	String partidos[] = {"MDB\r\n" +  
//			"PTB\r\n" +  
//			"PDT\r\n" +  
//			"PT\r\n" +  
//			"DEM\r\n" +  
//			"PCdoB\r\n" +  
//			"PSB\r\n" +  
//			"PSDB\r\n" +  
//			"PTC\r\n" +  
//			"PSC\r\n" +  
//			"PMN\r\n" +  
//			"CIDADANIA\r\n" +  
//			"PV\r\n" +  
//			"AVANTE\r\n" +  
//			"PP\r\n" +  
//			"PSTU\r\n" +  
//			"PCB\r\n" +  
//			"PRTB\r\n" +  
//			"DC\r\n" +  
//			"PCO\r\n" +  
//			"PODE\r\n" +  
//			"PSL\r\n" +  
//			"REPUBLICANOS�\r\n" +  
//			"PSOL\r\n" +  
//			"PL\r\n" +  
//			"PSD\r\n" +  
//			"PATRIOTA\r\n" +  
//			"PROS\r\n" +  
//			"SOLIDARIEDADE\r\n" +  
//			"NOVO\r\n" +  
//			"REDE\r\n" +  
//			"PMB\r\n" +  
//	"UP\r\n"};

	public PesquisaTwitter() {

		this.cb = new ConfigurationBuilder();
		this.cb.setDebugEnabled(true)
		.setOAuthConsumerKey("wZCHBUjihkWwmGopmWZWKuHeE")
		.setOAuthConsumerSecret("txuFKWD3apgswacgfA0WBLJ6xCxqP2oLKbLmCbr4Ioe5SVOUy4")
		.setOAuthAccessToken("1539801186-z1xrqXklXtfLzWUh82kHC5M2Fd01BqJxlyDOTZF")
		.setOAuthAccessTokenSecret("zWPltVBJgxOLEd2asnmNmxSQqdbhmAHF1ai13cZWFQh4X");
		this.tf = new TwitterFactory(cb.build());
		this.twitter = tf.getInstance();
	}

	public void pesquisa() {
		
		long contadorTweet = 0;

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
					
					try {
						data = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(data));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}

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
					
					contadorTweet++;

					arquivo.write(contadorTweet+";"+conteudoTweet+";"+ data+";"+hashTags);

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