package fr.esgi.annuel.server;

import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.List;
import fr.esgi.annuel.ctrl.MasterController;
import fr.esgi.annuel.message.Message;
import fr.esgi.annuel.message.MessageQueue;

public class Client implements Runnable
{
	private PublicKey publicKey;
	private String address;
    private int port;
    private String user ;
    private List<Message> mess;
	public Client(String address, int port, String user, MasterController controller)
	{
		this.address = address;
        this.port = port;
        this.user = user;
		CertificateFactory certificateFactory = null;
		Certificate certificate = null;
		try
		{
			certificateFactory = CertificateFactory.getInstance("X509");
			certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(controller.getPubliceKey(user))));
			this.publicKey = certificate.getPublicKey();
		}
		catch (CertificateException e)
		{
			e.printStackTrace();
			this.publicKey = null;
		}
	}

	@Override
	public void run()
	{
        while(true) {
            try {
                // Connexion à la box de l'interlocuteur
                Socket s = new Socket(this.address, this.port);
                System.out.println("Client lancé");
                MessageQueue mq = new MessageQueue();

                mess = mq.getAllMessages(this.user);
                for (Message messag : mess) {
                    s.getOutputStream().write(messag.getMessage().getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}