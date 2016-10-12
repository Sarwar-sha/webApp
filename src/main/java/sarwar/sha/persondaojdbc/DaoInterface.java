package sarwar.sha.persondaojdbc;

import sarwar.sha.domain.Person;

public interface DaoInterface {

	public void create(Person person);

	public void delete(Person person);

	public Person getById(long id);

}
