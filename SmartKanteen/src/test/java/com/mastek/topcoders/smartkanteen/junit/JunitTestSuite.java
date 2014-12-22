package com.mastek.topcoders.smartkanteen.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CatererTestCase.class, MenuTestCase.class, DailyMenuTestCase.class })
public class JunitTestSuite
{

}
