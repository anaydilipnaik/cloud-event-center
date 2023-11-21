package com.cmpe275.finalProject.cloudEventCenter.mail.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationMailService {

	@Autowired
	JavaMailSender mailSender;

	public boolean sendNotificationEmail(String receiver, String type, HashMap<String, String> params) {

		String toAddress = receiver;
		String fromAddress = "donotreply@cec.com";
		String senderName = "Cloud Event Center";
		String content = "";
		String subject = "";
		if (type.isEmpty()) {
			return false;
		}

		switch (type) {
		case "signup":
			subject = "Signup to CEC Successful!";
			content = "Dear user,<br><br>"
					+ "Thank you for signing up to Cloud Event Center. Please verify your email by clicking the link on the verification email."
					+ "<br><br> Best,<br>" + "CEC";
			break;
		case "verify":
			subject = "Email Verification to CEC Successful!";
			content = "Dear user,<br><br>"
					+ "Thank you for verifying your email address. "
					+ "<br>You will be able to sign-in to our website now."
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;
		case "eventCreation":
			subject = "Event Successfully created!";
			content = "Dear Organizer,<br><br>"
					+ "The event <b>[EVENT_NAME]</b> has been successfully created.Users will be able to singup for the event now. "
					+ "<br>Best of luck with your event!"
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;
		case "signupUser":
			subject = "New user signup for event!";
			content = "Dear Organizer,<br><br>"
					+ "A new user, <b>[USER_NAME]</b> has signed up for the event,"
					+ " <b>[EVENT_NAME]</b> that you are organizing. "
					+ "<br>Best of luck with your event!"
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;	
		case "signupThanks":
			subject = "Thank you for signing up to the event!";
			content = "Dear user,<br><br>"
					+ "Thank you for signing up for the event,"
					+ " <b>[EVENT_NAME]</b>. The event starts at <b>[START_TIME]</b>, and you can access the participant forum on <b>[DEADLINE_TIME]</b>. "
					+ "<br>See you soon at the event!"
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;	
		case "signupApproval":
			subject = "Event registration approved!";
			content = "Dear user,<br><br>"
					+ "Congratulations! Your participant request for the event [EVENT_NAME] has been approved.You now have access to the event's participation forum "
					+ "<br>Hope you have a great time at the event!"
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;
		case "signupReject":
			subject = "Event registration rejected :(";
			content = "Dear user,<br><br>"
					+ "We are sorry to inform you that your registration request for the event <b>[EVENT_NAME]</b> has been rejected."
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;
		case "eventCancel":
			subject = "Event cancellation alert :(";
			content = "Dear participant,<br><br>"
					+ "We are sorry to inform you that the event <b>[EVENT_NAME]</b> has been cancelled."
					+ " Any fee paid for the event will be refunded in 7 business days."
					+ "<br><br>" +
					"Best,<br>" + "CEC";
			break;
		case "eventStart":
			subject = "Event start alert";
			content = "Dear participant,<br><br>"
					+ "The event <b>[EVENT_NAME]</b>, that you have registered for has begun."
					+ "<br>Hope you have a great time at the event! Share your experience at the participant forum. <br>" +
					"<br>Best,<br>" + "CEC";
			break;
		case "review":
			subject = "Review recieved!";
			content = "Dear user,<br><br>"
					+ "You have recieved a new review from user <b>[USER_NAME]</b> for the event, <b>[EVENT_NAME]</b> <br>." +
					"<br>Best,<br>" + "CEC";
			break;
		case "message":
			subject = "Message posted in forum!";
			content = "Dear Organizer,<br><br>"
					+ "A new message has been posted by the user, <b>[USER_NAME]</b> on the forum for the event <b>[EVENT_NAME]</b> <br>." +
					"<br>Best,<br>" + "CEC";
			break;
			
		case "event_cancel_forum_message":
				subject = "Cancellation message posted on forum(s)";
				content = "Dear Organizer, <br><br>"
						+ "An event cancelleation message has been posted on the signup and participant forum. The forums are now closed due to cancellation<br><br>"
						+ "<br>Best,<br>" + "CEC";
			break;
		default:
			break;
		}

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		if (params!=null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				content = content.replace(entry.getKey(), entry.getValue());
			}
		}

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setFrom(new InternetAddress(fromAddress, senderName));
			mimeMessageHelper.setTo(toAddress);
			mimeMessageHelper.setText(content, true);

			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
