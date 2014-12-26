package com.mastek.topcoders.smartkanteen.junit;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class CatererTestCase
{
	@Test
	public void testAddCaterer() throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Caterer caterer = new Caterer();

		MenuService service = new MenuServiceImpl();
		caterer = service.addCaterer(caterer);

		Caterer catererDB = (Caterer) session.get(Caterer.class, caterer.getCatererId());
		Assert.assertEquals(caterer.getCatererName(), catererDB.getCatererName());
	}

	@Test
	public void testUpdateCaterer() throws Exception
	{
		Session session = DatabaseUtil.getSession();

		Caterer caterer = new Caterer();
		caterer.setCatererId(3);
		caterer.setCatererName("Rahul Caterer");

		MenuServiceImpl service = new MenuServiceImpl();
		caterer = service.updateCaterer(caterer.getCatererId(), caterer.getCatererName());

		Caterer catererDB = (Caterer) session.get(Caterer.class, 3);
		Assert.assertEquals(caterer.getCatererName(), catererDB.getCatererName());
	}

	@Test
	public void testDeleteCaterer() throws ObjectNotFoundException, Exception
	{
		boolean isDeleted = false;

		MenuService service = new MenuServiceImpl();
		boolean resultDB = service.deleteCaterer(1);
		Caterer caterer = (Caterer) service.getCaterer(1);

		if (caterer == null)
		{
			isDeleted = true;
		}

		Assert.assertEquals(isDeleted, resultDB);
	}

	@Test
	public void testGetCaterers()
	{
		MenuService service = new MenuServiceImpl();
		List<Caterer> catererList = service.getCaterers();
		Assert.assertEquals(4, catererList.size());
	}

}
