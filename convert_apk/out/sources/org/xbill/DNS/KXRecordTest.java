package org.xbill.DNS;

import junit.framework.TestCase;

public class KXRecordTest extends TestCase {
    public void test_getObject() {
        assertTrue(new KXRecord().getObject() instanceof KXRecord);
    }

    public void test_ctor_5arg() throws TextParseException {
        Name n = Name.fromString("My.Name.");
        Name m = Name.fromString("My.OtherName.");
        KXRecord d = new KXRecord(n, 1, 703710, 241, m);
        assertEquals(n, d.getName());
        assertEquals(36, d.getType());
        assertEquals(1, d.getDClass());
        assertEquals(703710, d.getTTL());
        assertEquals(241, d.getPreference());
        assertEquals(m, d.getTarget());
        assertEquals(m, d.getAdditionalName());
    }
}
