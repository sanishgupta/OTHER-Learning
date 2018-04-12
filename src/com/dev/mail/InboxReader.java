
package com.dev.mail;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


public class InboxReader
{

	public static void main(String args[])
	{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try
		{
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			//store.connect("imap.gmail.com", "<username>", "password");
			store.connect("imap.gmail.com", "abc@gmail.com", "eeee");
			System.out.println(store);

			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message messages[] = inbox.getMessages();
			for (Message message : messages)
			{
				System.out.println(message);
			}
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
			System.exit(2);
		}

	}

}
