package jeevkulk.service;

import jeevkulk.domain.CreditRating;
import jeevkulk.util.DataLoader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AverageCalculator implements IService {

    private Logger logger = LogManager.getLogger(AverageCalculator.class);

    @Autowired
    protected DataLoader dataLoader;

    private Map<String, BigDecimal> euroConversionMap;

    @Override
    public Map<String, BigDecimal> doProcess() throws Exception {
        List<CreditRating> list = dataLoader.readFile();

        Map<String, BigDecimal> sumMap = new HashMap<>();
        Map<String, BigDecimal> avgMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        String key = null;
        String currency = null;
        for (CreditRating cr : list) {
            key = getGroupingBy(cr);
            if (sumMap.containsKey(key)) {
                BigDecimal amount = sumMap.get(key).add(getAmountInEuro(cr));
                sumMap.put(key, amount);
                Integer count = countMap.get(key);
                countMap.put(key, ++count);
            } else {
                sumMap.put(key, cr.getAmount());
                countMap.put(key, 1);
            }
        }
        Set<String> set = sumMap.keySet();
        for(String keyStr : set) {
            BigDecimal average = sumMap.get(keyStr).divide(new BigDecimal(countMap.get(keyStr)));
            avgMap.put(keyStr, average);
            logger.info("keyStr "+keyStr+ "   Average="+average);
        }

        //using streams
        //list.stream().collect(Collectors.groupingBy((creditRating) -> creditRating.getCreditRating()));

        return avgMap;
    }

    private String getGroupingBy(CreditRating cr) {
        return ((cr.getCountry() != null && !"".equals(cr.getCountry())) ? cr.getCountry() : cr.getCity()) + "-" + cr.getCreditRating();
    }

    private BigDecimal getAmountInEuro(CreditRating cr) {
        return cr.getAmount().multiply(euroConversionMap.get(cr.getCurrency()));
    }

    @PostConstruct
    private void getCurrencyConversionRates() {
        euroConversionMap = new HashMap<>();

        BigDecimal usdToEuroConversionRate = new BigDecimal(1).divide(new BigDecimal(1.35), BigDecimal.ROUND_FLOOR);
        BigDecimal gbpToEuroConversionRate = usdToEuroConversionRate.multiply(new BigDecimal(1.654));
        BigDecimal chfToEuroConversionRate = usdToEuroConversionRate.multiply(new BigDecimal(1.1));

        euroConversionMap.put("USD", usdToEuroConversionRate);
        euroConversionMap.put("GBP", gbpToEuroConversionRate);
        euroConversionMap.put("CHF", chfToEuroConversionRate);
    }
}
