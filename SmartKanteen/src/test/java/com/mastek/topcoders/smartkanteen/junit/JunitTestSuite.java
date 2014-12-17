package com.mastek.topcoders.smartkanteen.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestMenuService.class
})
public class JunitTestSuite
{

}
