package jeevkulk.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import jeevkulk.service.AverageCalculator;
import jeevkulk.service.IService;

@SpringBootApplication( scanBasePackages = {"jeevkulk"})
public class Application {

    private Logger logger = LogManager.getLogger(Application.class);

    @Autowired
    @Qualifier("averageCalculator")
    private AverageCalculator averageCalculator;

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.execute(args);
    }

    private void execute(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        IService service = (IService) ctx.getBean("averageCalculator");
        service.execute();
    }
}
