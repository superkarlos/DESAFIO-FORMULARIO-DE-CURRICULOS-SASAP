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
import br.com.formulario.model.Curriculo;
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

    public void enviar( byte[] anexo, String nomeAnexo,Curriculo curriculo) {

        String texto = "Olá " + curriculo.getNome().toUpperCase() + ",\n\n" +
        "Seu currículo foi avaliado e ficamos felizes em saber do seu interesse na vaga de " + curriculo.getCargoDesejado().toUpperCase() + ". " +
        "É ótimo ver que você está no nível de escolaridade: " + curriculo.getEscolaridade() + ". " +
        "Em breve, você receberá um SMS no seu número de telefone: " + curriculo.getTelefone() + ".\n\n" +
        "Obrigado por se candidatar!\n\n" +
        "Atenciosamente,\n Sesap";

        EmailModel emailModel = new EmailModel();
        emailModel.setReferencia_ip(curriculo.getIp());
        emailModel.setEnviador("sistemaenvioemail08@gmail.com");
        emailModel.setReceptor(curriculo.getEmail());
        emailModel.setSubjt_titutlo("Envio de curriculo");
        emailModel.setTexto("Seu curriculo foi recebido! \n" + texto );
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