package fr.esgi.annuel.constants;

/**
* @author Gaël B.
**/
public enum ServerAction
{
	REGISTER("webAPI/register"),
	CONNECT("webAPI/connect"),
	STAY_ALIVE("webAPI/stayAlive"),
	DISCONNECT("webAPI/disconnect"),
	MODIFY_PROFILE("webAPI/updateInfos"),
	SEARCH("webAPI/search"),
	ADD_FRIEND("webAPI/addFriend"),
	ANSWER_REQUEST("webAPI/answerRequest"),
	GET_PRIVATE_KEY("webAPI/getKey/%s"),
	GET_PUBLIC_KEY("webAPI/getPubKey/%s"),
	GET_CLIENT_IP("webAPI/getCliIP/%s"),
	SHOW_PROFILE("webAPI/showProfile/%s");

	private final String pathTo;
	private static final String[]
		REGISTER_PARAM = {"login", "email", "hashPw", "lName", "fName", "hashPwK", "lengthKey"},
		CONNECT_PARAM = {"login" ,"email" ,"hashPW"},
		MODIFY_PROFILE_PARAM = {"login", "email", "fName", "lName", "hashPW"},
		SEARCH_PARAM = {"username", "email", "firstname", "name"},
		ADD_FRIEND_PARAM = {"username", "email"},
		ANSWER_REQUEST_PARAM = {"username", "validate"},
		LOGIN_ALONE = {"login"};

	private ServerAction(String pathTo)
	{
		this.pathTo = pathTo;
	}


	public String  getAddressFor()
	{
		return this.pathTo;
	}

	public String[] getParameters()
	{
		if(this == REGISTER)
			return REGISTER_PARAM;
		else if (this == CONNECT)
			return CONNECT_PARAM;
		else if(this == STAY_ALIVE)
			return null;
		else if(this == DISCONNECT)
			return null;
		else if(this == MODIFY_PROFILE)
			return MODIFY_PROFILE_PARAM;
		else if(this == SEARCH)
			return SEARCH_PARAM;
		else if(this == ADD_FRIEND)
			return ADD_FRIEND_PARAM;
		else if(this == ANSWER_REQUEST)
			return ANSWER_REQUEST_PARAM;
		else if(this == GET_PRIVATE_KEY)
			return LOGIN_ALONE;
		else if(this == GET_PUBLIC_KEY)
			return null;
		else if(this == GET_CLIENT_IP)
			return null;
		else
			return LOGIN_ALONE;
	}
}