package com.github.spirylics.xgwt.polymer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolymerTest {
    @Test
    public void toCamelCase() throws Exception {
        assertEquals("thisIsSomeText", Polymer.toCamelCase("THIS_IS_SOME_TEXT"));
    }

}