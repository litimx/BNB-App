package learn.masteryweek;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import learn.masteryweek.ui.*;
import learn.masteryweek.domain.*;

public class App {
    public static void main(String[] args) {
        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-configuration.xml");
        Controller controller = container.getBean(Controller.class);
        controller.run();

    }
}