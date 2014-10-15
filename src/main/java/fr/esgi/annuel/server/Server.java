package fr.esgi.annuel.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.net.InetAddress;
import java.util.Map;

import fr.esgi.annuel.ctrl.MasterController;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;

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
	public void run() {
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
                        portMapped = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(samplePort);
                }
            }
            System.out.println(samplePort);
            this.controller.setListeningPort(samplePort);
            ServerSocket server = new ServerSocket(samplePort);
            System.out.println("Server Launched");
            while (true) {
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress() + " " + socket.getPort());
                //FIXME ajouter la couche de décryptage
                byte[] b = new byte[256]; //FIXME voir comment gérer des messages très long sans splitter
                int count;
                while ((count = socket.getInputStream().read(b)) > 0)
                    System.out.println(new String(Arrays.copyOf(b, count)));
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                activeGW.deletePortMapping(samplePort, "TCP");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}