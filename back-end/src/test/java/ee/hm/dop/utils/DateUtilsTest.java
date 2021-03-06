package ee.hm.dop.utils;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DateUtilsTest {

    @Test
    public void x() {
        String s = DateUtils.toString_ddMMyyyy(new DateTime(2000, 1, 2, 3, 4));
        assertEquals("02.01.2000", s);
    }

    @Test
    public void fromJson() {
        String json = "2014-10-30T08:37:05Z";
        DateTime fromJson = DateUtils.fromJson(json);
        assertEquals(new DateTime(json), fromJson);

        json = "2014-10-30T08:37:05.009Z";
        fromJson = DateUtils.fromJson(json);
        assertEquals(new DateTime(json), fromJson);
    }

    @Test
    public void toJson() {
        DateTime date = new DateTime("2014-10-30T08:37:05Z");
        String json = "2014-10-30T08:37:05.000Z";
        String toJson = DateUtils.toJson(date);
        assertEquals(json, toJson);

        json = "2014-10-30T08:37:05.009Z";
        date = new DateTime(json);
        toJson = DateUtils.toJson(date);
        assertEquals(json, toJson);
    }

    @Test
    public void toStringWithoutMillis() {
        DateTime date = new DateTime("2014-10-30T08:37:05Z");
        String expected = "2014-10-30T08:37:05Z";
        String result = DateUtils.toStringWithoutMillis(date);
        assertEquals(expected, result);

        date = new DateTime("2014-10-30T08:37:05.452Z");
        result = DateUtils.toStringWithoutMillis(date);
        assertEquals(expected, result);
    }
}
