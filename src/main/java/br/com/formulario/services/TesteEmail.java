package br.com.formulario.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.formulario.enumtipo.Status;
import br.com.formulario.model.Curriculo;
import br.com.formulario.model.EmailModel;
import br.com.formulario.repository.EmailRespository;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class TesteEmail {

    @Autowired
    private EmailRespository emailrespository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Value("${spring.mail.username}")
    private String enviador;
    
    private static final Map<String, String> MIME_TYPES = new HashMap<>() {{
        put("pdf", "application/pdf");
        put("doc", "application/msword");
        put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }};

    public String enviar( Curriculo curriculo ){
         
        
         String destino = curriculo.getEmail();
         String titulo = "Curriculo Recebido";
         String texto ="Olá " + curriculo.getNome().toUpperCase() + ",\n\n" +
         "Seu currículo foi avaliado e ficamos felizes em saber do seu interesse na vaga de " + curriculo.getCargoDesejado().toUpperCase() + ",\n\n" +
         "É ótimo ver que você está no nível de escolaridade: " + curriculo.getEscolaridade()  + ",\n\n" +
         "Em breve, você receberá um SMS no seu número de telefone: " + curriculo.getTelefone() + ".\n\n" +
         "Obrigado por se candidatar!\n\n" +
         "Atenciosamente,\n  SISTEMA DE EMAILS";


        EmailModel emailModel = new EmailModel();
        emailModel.setCurriculo_id(curriculo.getId());
        emailModel.setEnviador(enviador);
        emailModel.setReceptor(curriculo.getEmail());
        emailModel.setSubjt_titutlo("Envio de curriculo");
        emailModel.setTexto("Curriculo No banco!"  );
        emailModel.setData(LocalDateTime.now());
        emailModel.setCandidato(curriculo.getNome());


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setFrom(enviador);
            helper.setTo(destino);
            helper.setSubject(titulo);
            helper.setText(texto);
          
            String fileName = curriculo.getFileName();
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            String mimeType = MIME_TYPES.getOrDefault(fileExtension, "application/octet-stream");

           helper.addAttachment(fileName, new ByteArrayDataSource(curriculo.getArquivo(), mimeType));

           javaMailSender.send(mimeMessage);
           
        //   byte[] anexo = curriculo.getArquivo();
         //  String nomeAnexo = fileName;
        //   if (anexo != null && nomeAnexo != null) {
         //   helper.addAttachment(nomeAnexo, new ByteArrayDataSource(anexo, "application/octet-stream"));
        //    emailModel.setArquivo(anexo);
       // }
           emailModel.setStatus(Status.ENVIADO);
            return "Email enviado!!";

        } catch (Exception e) {
            // TODO: handle exception
            emailModel.setStatus(Status.FALHA);
            return "erro ao enviar";
        }finally{
           emailrespository.save(emailModel);
        }

    }
}
