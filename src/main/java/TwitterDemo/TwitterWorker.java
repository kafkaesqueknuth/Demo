package TwitterDemo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONArray;

import com.googlecode.concurrenttrees.suffix.SuffixTree;

import TwitterDemo.TwitterFactory.HardcodedAccessContext;
import TwitterDemo.TwitterFactory.IAccessContext;
import TwitterDemo.Util.Utils;
import twitter4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterWorker {

	final static private Logger s_logger = Logger.getLogger(TwitterWorker.class);
	private static TwitterWorker s_Instance;
	private final Twitter mTwitterFactory;
	private final AtomicReference<ResponseState> mResponseState = new AtomicReference<ResponseState>() {
		private static final long serialVersionUID = 1L;
	};

	private static final ScheduledExecutorService s_Executor = Executors.newScheduledThreadPool(1);

	public static final TwitterWorker getInstance() {
		if (s_Instance == null) {
			// TODO: throw
		}

		return s_Instance;
	}

	private TwitterWorker(final IAccessContext context) {
		this.mResponseState.set(ResponseState.EMPTY);
		// init twitter factory
		this.mTwitterFactory = new TwitterFactory().getInstance();

		this.mTwitterFactory.setOAuthConsumer(context.getConsumerKey(), context.getConsumerSecretKey());
		this.mTwitterFactory
				.setOAuthAccessToken(new AccessToken(context.getAccessToken(), context.getAccessTokenSecret()));
	}

	public static synchronized void initialize() {
		s_Instance = new TwitterWorker(HardcodedAccessContext.getInstance());
		s_Executor.scheduleAtFixedRate(new TwitterPoll(s_Instance.mTwitterFactory, s_Instance.mResponseState), 5, 60000, TimeUnit.MILLISECONDS); // TODO: time
	}

	public String getLatestTweets() {
		return this.mResponseState.get().getJson();
	}
	
	private Collection<List<Status>> getMatches(final String key) {
		final SuffixTree<List<Status>> suffixTree = this.mResponseState.get().getSuffixTree();
		return suffixTree.getValuesForKeysContaining(key);
		
	}
	
	public String searchTweets(String matchKey) {
		matchKey = matchKey.trim();
		
		Collection<List<Status>> response = getMatches(matchKey);
		if(response == null) {
			return Utils.Constants.EMPTY_JSON;
		}

		final JSONArray ja = new JSONArray();
		for(List<Status> vals : response) {
			if(vals == null) continue;
			
			for(Status val : vals) {
				ja.put(Utils.makeJson(val,null));	
			}
		}
		
		return ja.toString();
		/*final Trie<String, List<Status> >trie = this.mResponseState.get().getTrie();
		final SortedMap<String, List<Status>> response = trie.prefixMap(prefixKey);
		if(response == null) {
			return Utils.Constants.EMPTY_JSON;
		}
		
		if(response.values() == null) {
			return Utils.Constants.EMPTY_JSON; 
		}
		
		final JSONArray ja = new JSONArray();
		for(List<Status> vals : response.values()) {
			for(Status val : vals) {
				ja.put(Utils.makeJson(val,null));	
			}
		}
		
		return ja.toString();*/
		
	}
}