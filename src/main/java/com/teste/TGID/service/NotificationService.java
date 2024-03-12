package com.teste.TGID.service;


import com.teste.TGID.dto.NotificationDTO;
import com.teste.TGID.entity.Transaction;
import com.teste.TGID.entity.Usuario;
import com.teste.TGID.exception.IntegracaoWebhookException;
import com.teste.TGID.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void envioNotificacao(Usuario usuario, Transaction transaction) throws Exception {

        try {
            String email = usuario.getEmail();
 //           NotificationDTO notificationRequest = new NotificationDTO(email);

            ResponseEntity<String> notificationResponse = restTemplate.postForEntity
                    ("https://webhook.site/4bc22922-2d31-43f7-9fc5-fe938aa01e5d", transaction, String.class);

            if ((notificationResponse.getStatusCode() == HttpStatus.OK)){
                log.info("Notificação enviada");
            }


        } catch (HttpServerErrorException exception) {
            log.error("Problema de conexao com webhook", exception.getMessage());
            throw new IntegracaoWebhookException(exception.getMessage());

        } catch (Exception exception){
            log.error("Erro interno da integracao do webhook", exception.getMessage());
            throw new InternalServerErrorException(exception.getMessage());
        }
    }

}
