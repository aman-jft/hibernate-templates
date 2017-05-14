package org.agile4tyro.nytcoder;

import org.agile4tyro.nytcoder.db.User;
import org.agile4tyro.nytcoder.template.HibernateTemplate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import junit.framework.TestCase;

;

/**
 * Created by aman on 14/5/17.
 */
public class UserTest extends TestCase {
	private final static SessionFactory sessionFactory = HibernateConfig.getConfiguration();

	public void testConnection() {
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			User user = (User) session.createQuery("FROM User").uniqueResult();
			assertNull(user);
		});
	}

	public void testUserInsert() {
		final User user = User.builder()
			.username("aman")
			.password("Goel")
			.build();
		HibernateTemplate.runQuery(sessionFactory, (Session session) -> {
			session.save(user);
			User dbUser = session.get(User.class, 1l);
			System.out.println(dbUser);
			assertNotNull(dbUser);
			assertEquals(dbUser.getId(), 1l);
			assertEquals(user.getUsername(), dbUser.getUsername());
			assertEquals(user.getPassword(), dbUser.getPassword());
			assertEquals(user.getCreatedOn(), dbUser.getCreatedOn());
			assertEquals(user.getDeleted(), dbUser.getDeleted());
		});
	}

}
