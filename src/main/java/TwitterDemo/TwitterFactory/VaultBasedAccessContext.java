package TwitterDemo.TwitterFactory;

import org.apache.commons.lang3.NotImplementedException;

// TODO:
// Implements secure vault based access keys retriver 
public class VaultBasedAccessContext implements IAccessContext {

	@Override
	public String getConsumerKey() {
		throw new NotImplementedException("getConsumerKey");
	}

	@Override
	public String getConsumerSecretKey() {
		throw new NotImplementedException("getConsumerSecret");
	}

	@Override
	public String getAccessToken() {
		throw new NotImplementedException("getAccessToken");
	}

	@Override
	public String getAccessTokenSecret() {
		throw new NotImplementedException("getAccessSecret");
	}

}
