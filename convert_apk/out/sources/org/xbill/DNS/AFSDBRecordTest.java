package org.xbill.DNS;

import junit.framework.TestCase;

public class AFSDBRecordTest extends TestCase {
    public void test_getObject() {
        assertTrue(new AFSDBRecord().getObject() instanceof AFSDBRecord);
    }

    public void test_ctor_5arg() throws TextParseException {
        Name n = Name.fromString("My.Name.");
        Name m = Name.fromString("My.OtherName.");
        AFSDBRecord d = new AFSDBRecord(n, 1, 703710, 241, m);
        assertEquals(n, d.getName());
        assertEquals(18, d.getType());
        assertEquals(1, d.getDClass());
        assertEquals(703710, d.getTTL());
        assertEquals(241, d.getSubtype());
        assertEquals(m, d.getHost());
    }
}
