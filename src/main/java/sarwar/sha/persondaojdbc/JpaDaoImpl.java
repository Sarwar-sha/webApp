package sarwar.sha.persondaojdbc;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sarwar.sha.domain.Person;

@Repository
@Transactional
public class JpaDaoImpl implements DaoInterface {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Person person) {

		try {
			entityManager.createQuery("from Person p where p.id = :id").setParameter("id", person.getId())
					.getSingleResult();
		} catch (NoResultException e) {
			entityManager.persist(person);
		}
	}

	@Override
	public void delete(Person person) {

		long id = person.getId();
		Person p = (Person) entityManager.createQuery("from Person p where p.id = :id").setParameter("id", id)
				.getSingleResult();

		if (entityManager.contains(p))
			entityManager.remove(p);
		else
			throw new NoResultException();

	}

	@Override
	public Person getById(long id) {

		try {
			return (Person) entityManager.createQuery("from Person p where p.id = :id").setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
