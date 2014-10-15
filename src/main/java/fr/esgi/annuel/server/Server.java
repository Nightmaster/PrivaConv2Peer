package fr.esgi.annuel.server;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Map;
import fr.esgi.annuel.ctrl.MasterController;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

public class Server implements Runnable
{
    public static int samplePort= 1024;
    private MasterController controller;
    private boolean portMapped = false;

    public Server(MasterController controller)
    {
        this.controller = controller;
    }
	@Override
	public void run(){
        GatewayDevice activeGW = null;
        try {
            //décrouvrir la box
            GatewayDiscover gatewayDiscover = new GatewayDiscover();

            Map<InetAddress, GatewayDevice> gateways = gatewayDiscover.discover();
            //si pas de box
            if (gateways.isEmpty()) {
                return;
            }

            // choose the first active gateway for the tests
            // prend la box
            activeGW = gatewayDiscover.getValidGateway();

            if (null == activeGW) {
                return;
            }

            // testing getGenericPortMappingEntry
            PortMappingEntry portMapping = new PortMappingEntry();
            // local address = adresse ip machine
            InetAddress localAddress = activeGW.getLocalAddress();

            while (portMapped != true) {
                try {

                    if (activeGW.getSpecificPortMappingEntry(samplePort, "TCP", portMapping)) {
                        samplePort++;
                    } else {
                        if (activeGW.addPortMapping(samplePort, samplePort, localAddress.getHostAddress(), "TCP", "test")) {
                            portMapped = true;
                        }
                    }
            } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.controller.setListeningPort(samplePort);
            ServerSocket server = new ServerSocket(samplePort);
            System.out.println("Server Launched");
            Socket socket = server.accept();
            while (true) {
                System.out.println(socket.getInetAddress() + " " + socket.getPort());
                //FIXME ajouter la couche de décryptage
                byte[] b = new byte[256]; //FIXME voir comment gérer des messages très long sans splitter
                int count;
                while ((count = socket.getInputStream().read(b)) > 0)
                    System.out.println(new String(Arrays.copyOf(b, count)));
                socket.close();
            }
        }
    } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}