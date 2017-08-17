package TwitterDemo;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import TwitterDemo.Util.Utils;

@Path("/")
public class Resource {

	CacheControl c = new CacheControl();
	String index;
	private String sIndex;

	public Resource() throws IOException {
		c.setMaxAge(3600);
		c.setMustRevalidate(false);
		InputStream in = AppStart.class.getResourceAsStream("/index.htm");
		index = IOUtils.toString(in, "UTF-8");
		
		InputStream sin = AppStart.class.getResourceAsStream("/search.htm");
		sIndex = IOUtils.toString(sin, "UTF-8");
	}

	@GET
	@Path("")
	@Produces("text/html")
	public Response index() {
		return Response.ok().entity(index).build();
	}
	
	@GET
	@Path("/search")
	@Produces("text/html")
	public Response search(@QueryParam(value = "searchTerm") String searchTerm) {
		return Response.ok().entity(sIndex).build();
	}

	@GET
	@Path("/ping")
	@Produces("text/html")
	public Response ping() {
		return Response.ok().entity("pong").build();
	}

	@GET
	@Path("/getTweets")
	@Produces("application/json")
	public Response getTweets() {
		return Response.ok().entity(TwitterWorker.getInstance().getLatestTweets()).build();
	}
	
	@GET
	@Path("/searchTweets/{searchTerm}")
	@Produces("application/json")
	public Response searchTweets(@PathParam(value = "searchTerm") String searchTerm) {
		String ans = Utils.Constants.EMPTY_JSON;
		if(searchTerm != null && !"".equals(searchTerm)) {
			ans = TwitterWorker.getInstance().searchTweets(searchTerm);
		}
		return Response.ok().entity(ans).build();
	}
}