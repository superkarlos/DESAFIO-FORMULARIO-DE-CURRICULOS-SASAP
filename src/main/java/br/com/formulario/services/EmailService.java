package br.com.formulario.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.formulario.enumtipo.Status;
import br.com.formulario.model.EmailModel;
import br.com.formulario.repository.EmailRespository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import java.time.LocalDateTime;





@Service
public class EmailService {

    @Autowired
    private EmailRespository respository;

    @Autowired
    private JavaMailSender emailSender;

    public void enviar(EmailModel emailModel, byte[] anexo, String nomeAnexo) {
        emailModel.setData(LocalDateTime.now());
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(emailModel.getEnviador());
            helper.setTo(emailModel.getReceptor());
            helper.setSubject(emailModel.getSubjt_titutlo());
            helper.setText(emailModel.getTexto());

            if (anexo != null && nomeAnexo != null) {
                helper.addAttachment(nomeAnexo, new ByteArrayDataSource(anexo, "application/octet-stream"));
                emailModel.setArquivo(anexo);
            }

            emailSender.send(message);
            emailModel.setStatus(Status.ENVIADO);
        } catch (MailException | MessagingException e) {
            emailModel.setStatus(Status.FALHA);
            // logger.error("Erro ao enviar email: ", e);
        } finally {
            respository.save(emailModel);
        }
    }
}










































/*@Service
public class EmailService {

    @Autowired
    EmailRespository respository;

    @Autowired
    private JavaMailSender emailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void enviar(EmailModel emailModel){
        emailModel.setData(LocalDateTime.now());

        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEnviador());
            message.setTo(emailModel.getReceptor());
            message.setSubject(emailModel.getSubjt_titutlo());
            message.setText(emailModel.getTexto());
           
            emailSender.send(message);
            emailModel.setStatus(Status.ENVIADO);
            logger.info("Email enviado com sucesso para {}", emailModel.getReceptor());

        } catch (MailException e) {
            emailModel.setStatus(Status.FALHA);
            logger.error("Erro ao enviar email: ", e);
        } finally {
            respository.save(emailModel);
            logger.info("Status do email salvo: {}", emailModel.getStatus());
        }
    }
}
*/