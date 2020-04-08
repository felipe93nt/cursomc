package com.felipeaugusto.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.felipeaugusto.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
