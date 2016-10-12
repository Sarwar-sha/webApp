package sarwar.sha.restfultest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import javax.annotation.PostConstruct;
// import javax.annotation.Resource;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import sarwar.sha.Application;
import sarwar.sha.conttroller.Controller;
import sarwar.sha.domain.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class RestfulTest {

    @Autowired
    private Controller controller;

    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {

        IDataSet dataSet =
            new FlatXmlDataSetBuilder().build(new File("C:/Users/ssha/workspace/ssha/src/main/resources/dataSet.xml"));
        IDatabaseConnection dbConn =
            new DatabaseDataSourceConnection(dataSource);
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
    }

    @PostConstruct
    public void setup() {

        mockMvc =
            MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(
                    new MappingJackson2HttpMessageConverter(),
                    new StringHttpMessageConverter()).build();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findPerson() throws Exception {

        mockMvc
            .perform(
                get("/?id={id}", 1034794).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(is(1034794)))
            .andExpect(jsonPath("$.firstName").value(is("Mike")));
        // db does not have wanted id
        Person person = new Person();// empty record
        mockMvc
            .perform(get("/?id={id}", 1).accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(is(person.getId())));
        Long id = null;
        mockMvc
            .perform(get("/?id={id}", id).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createPerson() throws Exception {

        mockMvc
            .perform(
                post(
                    "/?id={id}&firstName={firstName}"
                        + "&lastName={lastName}&phoneNumber={phoneNumber}"
                        + "&emailAddress={emailAddress}"
                        + "&startDate={startDate}&endDate={endDate}"
                        + "&login={login}&password={password}", 17l, "name",
                    "lastName", "757-753-6511", "example@example.com",
                    "2015-4-1", "2030-4-1", "fist.last", "gjkhsjk").accept(
                    MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is("Person succesfully created!")));
        // null id, first name or last name case tests
        mockMvc.perform(
            post(
                "/?id={id}&firstName={firstName}"
                    + "&lastName={lastName}&phoneNumber={phoneNumber}"
                    + "&emailAddress={emailAddress}&startDate={startDate}"
                    + "&endDate={endDate}&login={login}"
                    + "&password={password}", null, "firstName", "lastName",
                "757-753-6511", "example@example.com", "2015-4-1", "2030-4-1",
                "fist.last", "gjkhsjk").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mockMvc
            .perform(
                post(
                    "/?id={id}&firstName={firstName}"
                        + "&lastName={lastName}&phoneNumber={phoneNumber}"
                        + "&emailAddress={emailAddress}"
                        + "&startDate={startDate}&endDate={endDate}"
                        + "&login={login}&password={password}", 17l, null,
                    "lastName", "757-753-6511", "example@example.com",
                    "2015-4-1", "2030-4-1", "fist.last", "gjkhsjk").accept(
                    MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is("Person creating fail")));
        mockMvc
            .perform(
                post(
                    "/?id={id}&firstName={firstName}"
                        + "&lastName={lastName}&phoneNumber={phoneNumber}"
                        + "&emailAddress={emailAddress}"
                        + "&startDate={startDate}&endDate={endDate}"
                        + "&login={login}&password={password}", 17l, "first",
                    null, "757-753-6511", "example@example.com", "2015-4-1",
                    "2030-4-1", "fist.last", "gjkhsjk").accept(
                    MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is("Person creating fail")));

    }

    @Test
    public void deletePerson() throws Exception {

        mockMvc
            .perform(
                delete("/?id={id}", 1034792)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$").value(
                    is("Person id# 1034792 succesfully deleted!")));

        mockMvc
            .perform(delete("/?id={id}", 1).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is("Person id# 1 does not exist.")));
        Long id = null;
        mockMvc.perform(
            delete("/?id={id}", id).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
