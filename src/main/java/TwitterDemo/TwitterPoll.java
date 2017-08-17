package TwitterDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;

import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.suffix.ConcurrentSuffixTree;
import com.googlecode.concurrenttrees.suffix.SuffixTree;

import TwitterDemo.Util.Utils;
import twitter4j.Logger;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterPoll implements Runnable {

	private final AtomicReference<ResponseState> mState;
	private final Twitter mFactory;
	private final static Logger s_logger = Logger.getLogger(TwitterPoll.class);

	public TwitterPoll(final Twitter factory, AtomicReference<ResponseState> state) {
		this.mFactory = factory;
		this.mState = state;
	}

	public void run() {
		ResponseList<Status> response = null;
		try {
			response = this.mFactory.getUserTimeline(Utils.Constants.USER_TIMELINE_NAME);
		} catch (TwitterException e) {
			s_logger.error("Exception polling twitter timeline", e);
			return;
		}

		final JSONArray ja = new JSONArray();
		final int end = response.size() > Utils.Constants.NUM_MAX_RESULTS ? Utils.Constants.NUM_MAX_RESULTS : response.size();
		final Map<String, List<Status>> invertedIndex = new HashedMap<>();
		for (int i = 0; i < end; ++i) {
			final Status current = response.get(i);
			final StringBuilder sb = new StringBuilder();
			ja.put(Utils.makeJson(current, sb));
			final String[] tokens = sb.toString().split(" ");
			for(String token : tokens) {
				if(token == null || "".equals(token)) continue;
				
				final String normalized = token.trim();
				if(!invertedIndex.containsKey(normalized)) {
					invertedIndex.put(normalized, new ArrayList<Status>());
				}

				invertedIndex.get(normalized).add(current); // maps text to index of tweet	
			}
		}

		final SuffixTree<List<Status>> suffixTree = new ConcurrentSuffixTree<List<Status>>(new DefaultCharArrayNodeFactory());
		for( Entry<String, List<Status>> entry : invertedIndex.entrySet()) {
			suffixTree.put(entry.getKey(), entry.getValue()); 
		}
		
		final ResponseState newState = new ResponseState(ja.toString(), /*new PatriciaTrie<>(invertedIndex),*/ suffixTree);
		this.mState.set(newState);
	}

	

}
