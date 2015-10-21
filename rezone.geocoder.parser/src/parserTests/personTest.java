package parserTests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import parser.Person;


@RunWith(JUnitParamsRunner.class)
public class personTest {
    @Test
    @Parameters({
            "17, false",
            "22, true" })
    public void personIsAdult(int age, boolean valid) throws Exception {
        assertThat(new Person(age).isAdult(), is(valid));
    }
}
