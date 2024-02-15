/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import static mail.PasswordGenerator.generatePassword;
import security.SymmetricCryptography;

public class SendMail {

    public static String sendPasswordRecovery(String mail) {

        try {
            // Configuración de propiedades
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Credenciales de Gmail
            String[] parts = new SymmetricCryptography().descifrarTexto("clave").split("/");

            // Configuración de la sesión
            //Session session = Session.getInstance(props, null);          
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(parts[0], parts[1]);
                }
            });
            //Generar Contraseña
            int length = 24; // Longitud de la contraseña
            String password = generatePassword(length);

            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(parts[0]));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Correo de recuperación de contraseña");
            message.setText("Su contraseña generada es: " + password);

            // Enviar mensaje
            Transport.send(message);

            System.out.println("El mensaje fue enviado correctamente.");
            return password;
        } catch (MessagingException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
