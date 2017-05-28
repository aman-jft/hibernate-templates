package org.agile4tyro.nytcoder;

import org.agile4tyro.nytcoder.db.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by aman on 14/5/17.
 */
public class HibernateConfig {

	public static SessionFactory getConfiguration() {
		return new Configuration()

		.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
		.setProperty("hibernate.connection.driver_class", "org.h2.Driver")
		.setProperty("hibernate.connection.url", "jdbc:h2:mem:Template1")
		.setProperty("hibernate.hbm2ddl.auto", "create-drop")
		.setProperty("hibernate.current_session_context_class", "thread")
			.addAnnotatedClass(User.class)
			.buildSessionFactory();
	}
}
