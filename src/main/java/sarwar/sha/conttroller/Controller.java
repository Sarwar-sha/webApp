package sarwar.sha.conttroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sarwar.sha.domain.Person;
import sarwar.sha.persondaojdbc.DaoInterface;

@RestController
public class Controller {

	@Resource(name = "jpaDaoImpl")
	private DaoInterface personDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Person person(@RequestParam(value = "id", required = false) long id) {

		Person person = new Person();
		if (personDao.getById(id) == null)
			return person;
		else
			return personDao.getById(id);

	}

	@RequestMapping(value = "/", method = RequestMethod.POST, params = { "id", "firstName", "lastName", "phoneNumber",
			"emailAddress", "startDate", "endDate", "login", "password" })
	public String create(@RequestParam(value = "id") long id, @RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "emailAddress", required = false) String emailAddress,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "login", required = false) String login,
			@RequestParam(value = "password", required = false) String password) {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Person person = new Person();
			person.setId(id);
			if (firstName.equals("") || lastName.equals(""))
				return "Person creating fail";
			person.setFirstName(firstName);
			person.setLastName(lastName);
			person.setPhoneNumber(phoneNumber);
			person.setEmailAddress(emailAddress);
			if (!startDate.equals(""))
				person.setStartDate(formatter.parse(startDate));
			if (!endDate.equals(""))
				person.setEndDate(formatter.parse(endDate));
			person.setLogin(login);
			person.setPassword(password);
			personDao.create(person);
			return "Person succesfully created!";
		} catch (ParseException e) {
			return "Person creating fail";
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public String delete(@RequestParam(value = "id", required = true) long id) {

		try {
			Person person = new Person(id);
			personDao.delete(person);
		} catch (EmptyResultDataAccessException ex) {
			return "Person id# " + id + " does not exist.";
		}
		return "Person id# " + id + " succesfully deleted!";
	}

}
