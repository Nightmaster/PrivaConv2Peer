package fr.esgi.annuel.constants;

import static fr.esgi.annuel.constants.Parameters.*;

/**
* @author Gaël B.
**/
public enum ServerAction
{
	REGISTER("webAPI/register")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[] {USERNAME.getValue(), EMAIL.getValue(), PASSWORD.getValue(), FIRSTNAME.getValue(), LASTNAME.getValue(), PASSWORD_KEY.getValue(), KEY_LENGTH.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	CONNECT("webAPI/connect")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[]{USERNAME.getValue(), EMAIL.getValue() , PASSWORD.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	STAY_ALIVE("webAPI/stayAlive")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	DISCONNECT("webAPI/disconnect")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	MODIFY_PROFILE("webAPI/updateInfos")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[] {USERNAME.getValue(), EMAIL.getValue(), PASSWORD.getValue(), FIRSTNAME.getValue(), LASTNAME.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	SEARCH("webAPI/search")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[]{USERNAME.getValue(), EMAIL.getValue(), FIRSTNAME.getValue(), LASTNAME.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	ADD_FRIEND("webAPI/addFriend")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[]{USERNAME.getValue(), EMAIL.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	ANSWER_REQUEST("webAPI/answerRequest")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return new String[]{USERNAME.getValue(), VALIDATION.getValue()};
		}

		@Override
		public boolean isPathParameter()
		{
			return false;
		}
	},
	GET_PRIVATE_KEY("webAPI/getKey/%s")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return true;
		}
	},
	GET_PUBLIC_KEY("webAPI/getPubKey/%s")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return true;
		}
	},
	GET_CLIENT_IP("webAPI/getCliIP/%s")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return true;
		}
	},
	SHOW_PROFILE("webAPI/showProfile/%s")
	{
		@Override
		public String[] getAllowedParameters()
		{
			return null;
		}

		@Override
		public boolean isPathParameter()
		{
			return true;
		}
	};

	private final String pathTo;

	private ServerAction(String pathTo)
	{
		this.pathTo = pathTo;
	}

	public abstract String[] getAllowedParameters();

	public abstract boolean isPathParameter();

	public String  getAddressFor()
	{
		return this.pathTo;
	}
}