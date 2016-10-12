package sarwar.sha.persondaojdbc;


import java.sql.ResultSet;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sarwar.sha.domain.Person;

@Repository("Jdbctemplate")
@Transactional
public class Jdbctemplate
    implements DaoInterface {

    private StringBuilder builder;

    private String query;

    private Person person;

   
    private DataSource dataSource;
    
    @Autowired
    protected JdbcTemplate template;

    
    @Autowired
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Person person) {

        builder = new StringBuilder();
        query =
            builder
                .append("INSERT INTO person ")
                .append(" (id, first_name, last_name, phone_number, email_address,")
                .append(
                    " start_date, end_date, login, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")
                .toString();
        template.update(query, person.getId(), person.getFirstName(),
            person.getLastName(), person.getPhoneNumber(),
            person.getEmailAddress(), person.getStartDate(),
            person.getEndDate(), person.getLogin(), person.getPassword());

    }

    @Override
    public void delete(Person person) {

        query = "DELETE FROM person WHERE id=?";
        template.update(query, person.getId());
    }

    @Override
    public Person getById(long id) {

        person = new Person();
        query = "SELECT * FROM person WHERE id = ?;";

        person =
            template.queryForObject(query, new Object[] {id}, (ResultSet rs,
                                                               int rowNum) -> {

                Person wantedPerson = new Person();
                wantedPerson.setId(rs.getLong("id"));
                wantedPerson.setFirstName(rs.getString("first_name"));
                wantedPerson.setLastName(rs.getString("last_name"));
                wantedPerson.setPhoneNumber(rs.getString("phone_number"));
                wantedPerson.setEmailAddress(rs.getString("email_address"));
                if (rs.getDate("start_date") != null) {
                    wantedPerson.setStartDate(rs.getDate("start_date"));
                }
                if (rs.getDate("end_date") != null) {
                    wantedPerson.setEndDate(rs.getDate("end_date"));
                }
                wantedPerson.setLogin(rs.getString("login"));
                wantedPerson.setPassword(rs.getString("password"));
                return wantedPerson;

            });

        return person;

    }

}
