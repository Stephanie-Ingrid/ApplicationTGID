package com.teste.TGID.service;


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

    public void envioNotificacao(Usuario usuario, String transaction) throws Exception {

        try {
            String email = usuario.getEmail();

            ResponseEntity<String> notificationResponse = restTemplate.postForEntity
                    ("https://webhook.site/93742b05-75b8-46e8-b0e9-39ca20b5d917", transaction, String.class);

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
