package jeevkulk.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jeevkulk.domain.CreditRating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {

    private Logger logger = LogManager.getLogger(DataLoader.class);

    @Value("${data.filename}")
    public String dataFileName;

    public List<CreditRating> readFile() throws IOException {
        List<CreditRating> list = new ArrayList<>(25);
        File file = null;
        FileReader fileReader = null;
        BufferedReader br = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            file = new File(classLoader.getResource(dataFileName).getFile());
            fileReader = new java.io.FileReader(file);
            br = new BufferedReader(fileReader);
            int i = 0;
            boolean headerLine = true;
            while((i = br.read()) != -1) {
                String data = br.readLine();
                if(!headerLine) {
                    CreditRating cr = loadDomain(data);
                    list.add(cr);
                }
                headerLine = false;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if(fileReader != null)
                fileReader.close();
            if(br != null)
                br.close();
        }
        return list;
    }

    private CreditRating loadDomain(String data) {

        CreditRating cr = new CreditRating();
        String[] str = data.split("\t");
        cr.setCompanyCode(str[0]);
        cr.setAccount(str[1]);
        cr.setCity(str[2]);
        cr.setCountry(str[3]);
        cr.setCreditRating(str[4]);
        cr.setCurrency(str[5]);
        cr.setAmount(new BigDecimal(str[6]));

        return cr;
    }


}
