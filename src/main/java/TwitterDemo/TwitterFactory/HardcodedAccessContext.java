package TwitterDemo.TwitterFactory;

public class HardcodedAccessContext implements IAccessContext {

	private static final HardcodedAccessContext s_instance = new HardcodedAccessContext();

	static final String s_ConsumerKey = "xxxx";
	static final String s_ConsumerSecret = "xxxx";
	static final String s_AccessToken = "xx-xxx";
	static final String s_AccessTokenSecret = "xxx";

	private HardcodedAccessContext() {
		// disable new
	}

	public static HardcodedAccessContext getInstance() {
		return s_instance;
	}

	public String getConsumerKey() {
		return s_ConsumerKey;
	}

	public String getConsumerSecretKey() {
		return s_ConsumerSecret;
	}

	public String getAccessToken() {
		return s_AccessToken;
	}

	public String getAccessTokenSecret() {
		return s_AccessTokenSecret;
	}

}
