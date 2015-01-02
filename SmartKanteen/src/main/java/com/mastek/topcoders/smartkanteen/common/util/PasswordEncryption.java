package com.mastek.topcoders.smartkanteen.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption
{
	public static String encryptPassword(String input) throws NoSuchAlgorithmException
	{
		String md5 = null;

		if (null == input || "".equalsIgnoreCase(input.trim()))
		{
			return null;
		}

		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			throw e;
		}

		return md5;
	}
}
