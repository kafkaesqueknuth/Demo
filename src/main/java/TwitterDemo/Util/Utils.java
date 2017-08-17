package TwitterDemo.Util;

import org.json.JSONObject;

import twitter4j.Status;

public class Utils {
	public static class Constants {
		public static final String EMPTY_JSON = "{}";
		public static final String USER_TIMELINE_NAME = "Salesforce";
		public static final int NUM_MAX_RESULTS = 10;
	}

	public static JSONObject makeJson(final Status resp, final StringBuilder sb) {
		// limited fields, ok to manually parse jsonobject. alternately, we can create a
		// POJO
		final JSONObject jo = new JSONObject();

		final String name = resp.getUser().getName();
		jo.put("name", name);
		if (sb != null)
			sb.append(name).append(" ");

		final String sname = resp.getUser().getScreenName();
		jo.put("sname", sname);
		if (sb != null)
			sb.append(sname).append(" ");

		final String data = resp.getText();
		jo.put("data", data);
		if (sb != null)
			sb.append(data);

		jo.put("url", resp.getUser().getProfileImageURL());

		jo.put("rtcount", resp.getRetweetCount());
		jo.put("date", resp.getCreatedAt());
		return jo;

	}

}
