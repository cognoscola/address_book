package com.guillermo.addressbookapplication;



import com.guillermo.addressbookapplication.utils.StringUtils;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UtilityTest extends TestCase {
    @Test
    public void test_StringCapitalization() throws Exception {
        assertEquals("Small", StringUtils.capitalizeFirst("small"));
    }

}