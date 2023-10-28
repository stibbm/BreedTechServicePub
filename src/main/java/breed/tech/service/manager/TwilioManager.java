package breed.tech.service.manager;

import breed.tech.service.manager.params.SendSMSArgs;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static breed.tech.service.constants.Constants.*;

@Service
@Slf4j
public class TwilioManager {

    public TwilioManager() {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    }

    // send SMS message
    public void sendSMS(String sourcePhoneNumberString, String destinationPhoneNumberString, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(destinationPhoneNumberString),
                new PhoneNumber(sourcePhoneNumberString),
                messageBody
        ).create();
        System.out.println("message SID = " + message.getSid());
    }

    public void sendSMS(SendSMSArgs sendSMSArgs) {
        this.sendSMS(sendSMSArgs.getSourcePhoneNumberString(), sendSMSArgs.getDestinationPhoneNumberString(),
                sendSMSArgs.getMessageString());
    }

    public void sendSMSFromDefaultNumber(String destinationPhoneNumberString, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(destinationPhoneNumberString),
                new PhoneNumber(SOURCE_TWILIO_PHONE_NUMBER),
                messageBody
        ).create();
    }
}