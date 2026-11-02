
package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class PersonView extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void setSpringContext(
            ConfigurableApplicationContext context) {
        springContext = context;
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/PersonView.fxml")
        );

        // A controllert a Spring példányosítja.
        loader.setControllerFactory(springContext::getBean);

        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 450);

        stage.setTitle("Person Manager");
        stage.setScene(scene);
        stage.show();
    }
}
