package dk.mfaester.grav;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Morten.Faester on 09-01-14.
 */

public class EquilibratorTest {
    Equilibrator _subjectUnderTest;

    @Before
    public void prepareTest(){
        _subjectUnderTest = new Equilibrator();
    }

    @Test
    public void Move_ThenCloserToAnother(){
        Assert.assertSame("a", "a");
    }
}
