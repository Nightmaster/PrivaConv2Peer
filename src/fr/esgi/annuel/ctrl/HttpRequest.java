package fr.esgi.annuel.ctrl;

import java.io.IOException;
import java.net.*;
import fr.esgi.annuel.constants.ServerAction;

/**
 * @author Gaël B.
 */
public class HttpRequest
{

	private URLConnection connection = null;
	private CookieManager manager = new CookieManager();
	private CookieStore store;
	private MasterController controller;

	public HttpRequest(MasterController controller)
	{
		this.controller = controller;
		CookieHandler.setDefault(this.manager);
		this.store = this.manager.getCookieStore();
	}

	public URLConnection getConnection()
	{
		return connection;
	}

	public final void initConnection(ServerAction action, String... parameters) throws IOException
	{
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (String param : parameters)
		{
			if (0 == i)
				sb.append('?');
			else
				sb.append('&');
			sb.append(param);
			i++ ;
		}
		System.out.println(this.controller.getProperties().getProperty("server.address") + ":" + this.controller.getProperties().getProperty("server.port") + "/" + action.getAddressFor() + sb.toString());
		//connection = new URL(SRV_ADDR + ":" + SRV_PORT + "/" + action.getAddressFor() + sb.toString()).openConnection();
	}

	public final HttpCookie getCookie() throws ConnectException
	{
		if (null != this.connection)
		{
			return store.getCookies().get(0);
		}
		throw new ConnectException("Connection not already established!");
	}
}
