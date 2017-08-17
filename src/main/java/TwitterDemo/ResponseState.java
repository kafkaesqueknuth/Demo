package TwitterDemo;

import java.util.*;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.suffix.ConcurrentSuffixTree;
import com.googlecode.concurrenttrees.suffix.SuffixTree;

import TwitterDemo.Util.Utils;
import twitter4j.Status;

public class ResponseState {

	private final String mJson ;
	//private final Trie<String, List<Status>> mTrie ;
	private final SuffixTree<List<Status>> mSuffixTree ;
	public final static ResponseState EMPTY = new ResponseState();
	
	private ResponseState() {
		this(Utils.Constants.EMPTY_JSON, /*new PatriciaTrie<>(new HashMap<String,List<Status>>()), */ new ConcurrentSuffixTree<List<Status>>(new DefaultCharArrayNodeFactory()));
	}
	
	public ResponseState(final String json, /*final PatriciaTrie<List<Status>> patriciaTrie,*/ SuffixTree<List<Status>> suffixTree) {
		this.mJson = json;
		//this.mTrie = patriciaTrie;
		this.mSuffixTree = suffixTree;
	}
	
	public String getJson() {
		return this.mJson;
	}
	
	/*public Trie<String, List<Status>> getTrie() {
		return this.mTrie;
	}*/
	
	public SuffixTree<List<Status>> getSuffixTree() {
		return this.mSuffixTree;
	}
}
