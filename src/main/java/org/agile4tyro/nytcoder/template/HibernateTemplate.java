package org.agile4tyro.nytcoder.template;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by aman on 14/5/17.
 */
public class HibernateTemplate {
	public static void runQuery(SessionFactory sf, Consumer<Session> consumer) {
		Session session = sf.openSession();
		session.beginTransaction();
		consumer.accept(session);
		session.getTransaction().commit();
		session.close();
	}
}
