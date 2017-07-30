package jeevkulk.service;

import jeevkulk.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class AverageCalculatorTest {

    @Autowired
    private AverageCalculator averageCalculator;


    @Test
    public void testDoProcessSuccess() throws Exception {

        Map<String, BigDecimal> map = averageCalculator.doProcess();
        String testKey = "IRL-Aa";
        Assert.assertTrue("Map does not contain key for test result"+testKey, map.containsKey(testKey));
    }
}
