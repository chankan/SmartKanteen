package com.mastek.topcoders.smartkanteen.junit;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class TagTestCase
{
	//TODO Needs more test cases
	@Test
	public void testAddTag() throws Exception
	{
		Tag tag = new Tag();
		tag.setTagName("Kolhapuri");

		MenuService service = new MenuServiceImpl();
		tag = service.addTag(tag);

		Session session = DatabaseUtil.getSession();
		Tag tagDB = (Tag) session.get(Tag.class, tag.getTagId());
		Assert.assertEquals(tag.getTagName(), tagDB.getTagName());
	}

	@Test
	public void testUpdateTag() throws Exception
	{
		Tag tag = new Tag();
		tag.setTagId(3);
		tag.setTagName("Rajasthani");

		MenuService service = new MenuServiceImpl();
		Tag tagDB = service.updateTag(tag);

		Assert.assertEquals(tag, tagDB);
	}

	@Test
	public void testDeleteTag() throws ObjectNotFoundException, Exception
	{
		Tag tag = new Tag();
		tag.setTagId(4);
		
		MenuService service = new MenuServiceImpl();
		boolean result = service.deleteTag(tag);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testGetTags()
	{
		MenuService service = new MenuServiceImpl();
		List<Tag> tags = service.getTags();
		Assert.assertEquals(2, tags.size());
	}

	@Test
	public void testGetTag()
	{
		MenuService service = new MenuServiceImpl();
		Tag tag = service.getTag(5);
		Assert.assertEquals("Kolhapuri", tag.getTagName());
	}
}
