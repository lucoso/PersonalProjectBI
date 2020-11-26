package br.com.lucaswagner.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
	
	 private static final String HOSTNAME = "smtp.gmail.com";
	 private static final String USERNAME = "usuario@gmail.com";
	 private static final String PASSWORD = "**********";
	 private static final String EMAILORIGEM = "usuario@gmail.com";
	 
	 @SuppressWarnings("deprecation")
	public static void enviaEmail(String titulo, String destino, String mensagem) throws EmailException {
		 SimpleEmail email = new SimpleEmail();
		 email.setHostName(HOSTNAME);
		 email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		 email.setTLS(true);
		 email.setSmtpPort(587);
		 email.setFrom(EMAILORIGEM);
		 email.setSubject(titulo);
		 email.setMsg(mensagem);
		 email.addTo(destino);
		 bypassSSL();
		 
		 email.send();
	 }
	 
	 public static void bypassSSL(){
		 final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			 public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
				 
			 }
			 
			 public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
				 
			 }
			 
			 public java.security.cert.X509Certificate[] getAcceptedIssuers(){
				 return null;
			 }
		 }
	 };
		 try{
			 final SSLContext sc = SSLContext.getInstance("TLS");
			 sc.init(null, trustAllCerts, new java.security.SecureRandom());
			 SSLContext.setDefault(sc);
		 }catch(final Exception ex){
			 ex.printStackTrace();
		 }
	 }	 

}
