package ee.hm.dop.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;

import ee.hm.dop.common.test.DatabaseTestBase;
import ee.hm.dop.model.CrossCurricularTheme;

public class CrossCurricularThemeDAOTest extends DatabaseTestBase {

    @Inject
    private CrossCurricularThemeDAO crossCurricularThemeDAO;

    @Test
    public void findAll() {
        List<CrossCurricularTheme> result = crossCurricularThemeDAO.findAll();

        assertEquals(2, result.size());

        List<String> expected = Arrays.asList("Lifelong_learning_and_career_planning",
                "Environment_and_sustainable_development");
        List<String> actual = result.stream().map(r -> r.getName()).collect(Collectors.toList());

        assertTrue(actual.containsAll(expected));
    }

}