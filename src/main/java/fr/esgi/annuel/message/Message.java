package fr.esgi.annuel.message;

import java.util.Date;

/**
 *
 * class Message
 * Elle sert � formater les packets transmis
 * */
public class Message
{
	String message;
	Date receiveDate;

	public String getMessage()
	{
		return this.message;
	}

	public Date getReceiveDate()
	{
		return this.receiveDate;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setReceiveDate(Date receiveDate)
	{
		this.receiveDate = receiveDate;
	}

}
