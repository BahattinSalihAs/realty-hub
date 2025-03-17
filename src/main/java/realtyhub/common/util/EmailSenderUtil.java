package realtyhub.common.util;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class EmailSenderUtil {
    private final JavaMailSender mailSender;

    public final void sendEmail(
            final String toMail,
            final String subject,
            final String body
    ){
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toMail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
        }catch (MailAuthenticationException e){
            throw new MailAuthenticationException(e.getMessage());
        }

    }

}
