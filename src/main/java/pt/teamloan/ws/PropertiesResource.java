package pt.teamloan.ws;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;


@Path("/properties")
@Produces(MediaType.APPLICATION_JSON)
public class PropertiesResource {

	private static final String FILTER_PASSWORD_KEYWORD = "password";

	@GET
	public Map<String,String> get() {
		Map<String, String> props = new HashMap();
		Config config = ConfigProviderResolver.instance().getConfig();
		for (String propName : config.getPropertyNames()) {
			try {
				if(!propName.toLowerCase().contains(FILTER_PASSWORD_KEYWORD)) {
					props.put(propName, config.getValue(propName, String.class));
				}
			} catch (Exception e) {
				// move on
			}
		}
		return props;
	}
}
