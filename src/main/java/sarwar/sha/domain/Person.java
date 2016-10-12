package sarwar.sha.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person
    implements Comparable<Person> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "db_id")
    private long db_id;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public Person(Long constructorId) {

        this.id = constructorId;
    }

    public Person(Long constructorId, String constructorFirstname,
                  String constructorLastName, String phoneNumber,
                  String constructorEmailAddress, Date constructorStartDate,
                  Date constructorendDate, String login, String password) {

        this.id = constructorId;
        this.firstName = constructorFirstname;
        this.lastName = constructorLastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = constructorEmailAddress;
        this.startDate = new Date(constructorStartDate.getTime());
        this.endDate = new Date(constructorendDate.getTime());
        this.login = login;
        this.password = password;
    }

    public Person(Long constructorId, String constructorFirstname,
                  String constructorLastName, String phoneNumber,
                  String constructorEmailAddress, Date constructorStartDate,
                  Date constructorendDate) {

        this(constructorId, constructorFirstname, constructorLastName,
            phoneNumber, constructorEmailAddress, constructorStartDate,
            constructorendDate, null, null);
    }

    public Person(Long constructorId, String constructorFirstname,
                  String constructorLastName, String phoneNumber,
                  String constructorEmailAddress, Date constructorStartDate) {

        this.id = constructorId;
        this.firstName = constructorFirstname;
        this.lastName = constructorLastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = constructorEmailAddress;
        this.startDate = new Date(constructorStartDate.getTime());
    }

    public Person(Long constructorId, String constructorFirstname,
                  String constructorLastName) {

        this.id = constructorId;
        this.firstName = constructorFirstname;
        this.lastName = constructorLastName;

    }

    public Person() {

    }

    public Long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    public Date getStartDate() {

        if (this.startDate != null) {
            return (Date) startDate.clone();
        } else
            return null;
    }

    public void setStartDate(Date startDate) {

        this.startDate = (Date) startDate.clone();
    }

    public Date getEndDate() {

        if (this.endDate != null) {
            return (Date) endDate.clone();
        } else
            return null;
    }

    public void setEndDate(Date endDate) {

        this.endDate = (Date) endDate.clone();
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append(id).append(": ").append(lastName).append(", ")
            .append(firstName);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {

        boolean flag = false;

        if (obj == null) {
            flag = false;
        } else if (this.getClass() != obj.getClass()) {
            flag = false;
        } else if (this == obj) {
            flag = true;
        } else {
            Person other = (Person) obj;
            if ((this.getId() != null) && (other.getId() != null)) {

                if (this.getId().equals(other.getId())) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    @Override
    public int hashCode() {

        if (id == null) {
            return 0;
        }
        return (int) (long) this.id;
    }

    public int compareTo(Person o) {

        if (this.lastName != null && o.getLastName() != null
            && this.lastName.compareToIgnoreCase(o.getLastName()) != 0) {
            return this.lastName.compareToIgnoreCase(o.getLastName());
        } else

        if ((this.firstName != null) && (o.getFirstName() != null)
            && this.firstName.compareToIgnoreCase(o.getFirstName()) != 0) {
            return this.firstName.compareToIgnoreCase(o.getFirstName());
        } else if ((this.id != null) && (o.getId() != null)) {
            return Long.compare(this.id, o.getId());
        } else {
            throw new NullPointerException("Comparison fail");
        }
    }
}