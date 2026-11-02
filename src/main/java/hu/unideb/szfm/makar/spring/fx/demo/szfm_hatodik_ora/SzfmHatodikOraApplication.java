package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora;

import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.view.PersonView;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SzfmHatodikOraApplication {
	//teszt kommit
	public static void main(String[] args) {

		//SpringApplication.run(SzfmHatodikOraApplication.class, args);

		ConfigurableApplicationContext context
				= SpringApplication.run(SzfmHatodikOraApplication.class, args);
		PersonView.setSpringContext(context);

		try {
			Application.launch(PersonView.class, args);
        }
		finally {
            context.close();
        }

	}

}
