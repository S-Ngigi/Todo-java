import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import static spark.Spark.*;
import org.sql2o.Sql2o;

public class App {
    static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {
        BasicConfigurator.configure();
    }
}