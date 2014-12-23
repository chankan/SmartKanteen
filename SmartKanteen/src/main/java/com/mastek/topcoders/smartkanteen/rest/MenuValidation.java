package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;

import com.mastek.topcoders.smartkanteen.bean.Menu;

public class MenuValidation {
//	private Menu menu = new Menu();

	static public boolean validate(Menu menu)
	{
//		this.menu=menu;
//		int itemId=menu.getItemId();
		String itemName=menu.getItemName();
		String description=menu.getDescription();
		Integer prepTime=menu.getPrepTime();
		BigDecimal price=menu.getPrice();


		if(itemName != null && itemName.matches(".*\\w.*"))
		{
			if(itemName.trim().length()<=10)
			{
				if(description !=null)
				{
					if(description.length()<255)
					{
						if(prepTime !=null)
						{
							if(prepTime > 0)
							{
								if(!(price.equals(null)) )
								{
									if(price.compareTo(new BigDecimal(0))!=0)
									{
										return true;
									}
									else
									{
										System.out.println("price can't be zero!!");
										return false;
									}
								}
								else
								{
									System.out.println("price can't be null");
									return false;
								}
							}
							else
							{
								System.out.println("prepTime can't be zero!!");
								return false;
							}
						}
						else
						{
							System.out.println("prepTime can't be null");
							return false;
						}
					}
					else
					{
						System.out.println("Description should be of less than 255 characters!!");
						return false;
					}

				}
				else
				{
					System.out.println("Description can not be blank!!");
					return false;
				}
			}
			else
			{
				System.out.println("Name should not be greater than 10 characters!");
				return false;
			}
		}
		else
		{	
			System.out.println("Name can not be blank!");
			return false;
		}

	}




}
