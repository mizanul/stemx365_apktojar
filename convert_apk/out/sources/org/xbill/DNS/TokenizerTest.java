package org.xbill.DNS;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.p010ws.commons.util.Base64;
import org.xbill.DNS.Tokenizer;

public class TokenizerTest extends TestCase {
    private Tokenizer m_t;

    /* access modifiers changed from: protected */
    public void setUp() {
        this.m_t = null;
    }

    private void assertEquals(byte[] exp, byte[] act) {
        assertTrue(Arrays.equals(exp, act));
    }

    public void test_get() throws IOException {
        Tokenizer tokenizer = new Tokenizer((InputStream) new BufferedInputStream(new ByteArrayInputStream("AnIdentifier \"a quoted \\\" string\"\r\n; this is \"my\"\t(comment)\nanotherIdentifier (\ramultilineIdentifier\n)".getBytes())));
        this.m_t = tokenizer;
        Tokenizer.Token tt = tokenizer.get(true, true);
        assertEquals(3, tt.type);
        assertTrue(tt.isString());
        assertFalse(tt.isEOL());
        assertEquals("AnIdentifier", tt.value);
        Tokenizer.Token tt2 = this.m_t.get(true, true);
        assertEquals(2, tt2.type);
        assertFalse(tt2.isString());
        assertFalse(tt2.isEOL());
        assertNull(tt2.value);
        Tokenizer.Token tt3 = this.m_t.get(true, true);
        assertEquals(4, tt3.type);
        assertTrue(tt3.isString());
        assertFalse(tt3.isEOL());
        assertEquals("a quoted \\\" string", tt3.value);
        Tokenizer.Token tt4 = this.m_t.get(true, true);
        assertEquals(1, tt4.type);
        assertFalse(tt4.isString());
        assertTrue(tt4.isEOL());
        assertNull(tt4.value);
        Tokenizer.Token tt5 = this.m_t.get(true, true);
        assertEquals(5, tt5.type);
        assertFalse(tt5.isString());
        assertFalse(tt5.isEOL());
        assertEquals(" this is \"my\"\t(comment)", tt5.value);
        Tokenizer.Token tt6 = this.m_t.get(true, true);
        assertEquals(1, tt6.type);
        assertFalse(tt6.isString());
        assertTrue(tt6.isEOL());
        assertNull(tt6.value);
        Tokenizer.Token tt7 = this.m_t.get(true, true);
        assertEquals(3, tt7.type);
        assertTrue(tt7.isString());
        assertFalse(tt7.isEOL());
        assertEquals("anotherIdentifier", tt7.value);
        assertEquals(2, this.m_t.get(true, true).type);
        Tokenizer.Token tt8 = this.m_t.get(true, true);
        assertEquals(3, tt8.type);
        assertTrue(tt8.isString());
        assertFalse(tt8.isEOL());
        assertEquals("amultilineIdentifier", tt8.value);
        assertEquals(2, this.m_t.get(true, true).type);
        Tokenizer.Token tt9 = this.m_t.get(true, true);
        assertEquals(0, tt9.type);
        assertFalse(tt9.isString());
        assertTrue(tt9.isEOL());
        assertNull(tt9.value);
        Tokenizer.Token tt10 = this.m_t.get(true, true);
        assertEquals(0, tt10.type);
        assertFalse(tt10.isString());
        assertTrue(tt10.isEOL());
        assertNull(tt10.value);
        Tokenizer tokenizer2 = new Tokenizer("onlyOneIdentifier");
        this.m_t = tokenizer2;
        Tokenizer.Token tt11 = tokenizer2.get();
        assertEquals(3, tt11.type);
        assertEquals("onlyOneIdentifier", tt11.value);
        Tokenizer tokenizer3 = new Tokenizer("identifier ;");
        this.m_t = tokenizer3;
        assertEquals("identifier", tokenizer3.get().value);
        assertEquals(0, this.m_t.get().type);
        Tokenizer tokenizer4 = new Tokenizer("identifier \nidentifier2; junk comment");
        this.m_t = tokenizer4;
        Tokenizer.Token tt12 = tokenizer4.get(true, true);
        assertEquals(3, tt12.type);
        assertEquals("identifier", tt12.value);
        this.m_t.unget();
        Tokenizer.Token tt13 = this.m_t.get(true, true);
        assertEquals(3, tt13.type);
        assertEquals("identifier", tt13.value);
        assertEquals(2, this.m_t.get(true, true).type);
        this.m_t.unget();
        assertEquals(2, this.m_t.get(true, true).type);
        assertEquals(1, this.m_t.get(true, true).type);
        this.m_t.unget();
        assertEquals(1, this.m_t.get(true, true).type);
        Tokenizer.Token tt14 = this.m_t.get(true, true);
        assertEquals(3, tt14.type);
        assertEquals("identifier2", tt14.value);
        Tokenizer.Token tt15 = this.m_t.get(true, true);
        assertEquals(5, tt15.type);
        assertEquals(" junk comment", tt15.value);
        this.m_t.unget();
        Tokenizer.Token tt16 = this.m_t.get(true, true);
        assertEquals(5, tt16.type);
        assertEquals(" junk comment", tt16.value);
        assertEquals(0, this.m_t.get(true, true).type);
        Tokenizer tokenizer5 = new Tokenizer("identifier ( junk ; comment\n )");
        this.m_t = tokenizer5;
        assertEquals(3, tokenizer5.get().type);
        assertEquals(3, this.m_t.get().type);
        assertEquals(0, this.m_t.get().type);
    }

    public void test_get_invalid() throws IOException {
        Tokenizer tokenizer = new Tokenizer("(this ;");
        this.m_t = tokenizer;
        tokenizer.get();
        try {
            this.m_t.get();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer2 = new Tokenizer("\"bad");
        this.m_t = tokenizer2;
        try {
            tokenizer2.get();
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
        Tokenizer tokenizer3 = new Tokenizer(")");
        this.m_t = tokenizer3;
        try {
            tokenizer3.get();
            fail("TextParseException not thrown");
        } catch (TextParseException e3) {
        }
        Tokenizer tokenizer4 = new Tokenizer("\\");
        this.m_t = tokenizer4;
        try {
            tokenizer4.get();
            fail("TextParseException not thrown");
        } catch (TextParseException e4) {
        }
        Tokenizer tokenizer5 = new Tokenizer("\"\n");
        this.m_t = tokenizer5;
        try {
            tokenizer5.get();
            fail("TextParseException not thrown");
        } catch (TextParseException e5) {
        }
    }

    public void test_File_input() throws IOException {
        File tmp = File.createTempFile("dnsjava", "tmp");
        try {
            FileWriter fw = new FileWriter(tmp);
            fw.write("file\ninput; test");
            fw.close();
            Tokenizer tokenizer = new Tokenizer(tmp);
            this.m_t = tokenizer;
            Tokenizer.Token tt = tokenizer.get();
            assertEquals(3, tt.type);
            assertEquals(HttpPostBodyUtil.FILE, tt.value);
            assertEquals(1, this.m_t.get().type);
            Tokenizer.Token tt2 = this.m_t.get();
            assertEquals(3, tt2.type);
            assertEquals("input", tt2.value);
            Tokenizer.Token tt3 = this.m_t.get(false, true);
            assertEquals(5, tt3.type);
            assertEquals(" test", tt3.value);
            this.m_t.close();
        } finally {
            tmp.delete();
        }
    }

    public void test_unwanted_comment() throws IOException {
        Tokenizer tokenizer = new Tokenizer("; this whole thing is a comment\n");
        this.m_t = tokenizer;
        assertEquals(1, tokenizer.get().type);
    }

    public void test_unwanted_ungotten_whitespace() throws IOException {
        Tokenizer tokenizer = new Tokenizer(" ");
        this.m_t = tokenizer;
        Tokenizer.Token token = tokenizer.get(true, true);
        this.m_t.unget();
        assertEquals(0, this.m_t.get().type);
    }

    public void test_unwanted_ungotten_comment() throws IOException {
        Tokenizer tokenizer = new Tokenizer("; this whole thing is a comment");
        this.m_t = tokenizer;
        Tokenizer.Token token = tokenizer.get(true, true);
        this.m_t.unget();
        assertEquals(0, this.m_t.get().type);
    }

    public void test_empty_string() throws IOException {
        Tokenizer tokenizer = new Tokenizer("");
        this.m_t = tokenizer;
        assertEquals(0, tokenizer.get().type);
        Tokenizer tokenizer2 = new Tokenizer(" ");
        this.m_t = tokenizer2;
        assertEquals(0, tokenizer2.get().type);
    }

    public void test_multiple_ungets() throws IOException {
        Tokenizer tokenizer = new Tokenizer("a simple one");
        this.m_t = tokenizer;
        Tokenizer.Token token = tokenizer.get();
        this.m_t.unget();
        try {
            this.m_t.unget();
            fail("IllegalStateException not thrown");
        } catch (IllegalStateException e) {
        }
    }

    public void test_getString() throws IOException {
        Tokenizer tokenizer = new Tokenizer("just_an_identifier");
        this.m_t = tokenizer;
        assertEquals("just_an_identifier", tokenizer.getString());
        Tokenizer tokenizer2 = new Tokenizer("\"just a string\"");
        this.m_t = tokenizer2;
        assertEquals("just a string", tokenizer2.getString());
        Tokenizer tokenizer3 = new Tokenizer("; just a comment");
        this.m_t = tokenizer3;
        try {
            String out = tokenizer3.getString();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
    }

    public void test_getIdentifier() throws IOException {
        Tokenizer tokenizer = new Tokenizer("just_an_identifier");
        this.m_t = tokenizer;
        assertEquals("just_an_identifier", tokenizer.getIdentifier());
        Tokenizer tokenizer2 = new Tokenizer("\"just a string\"");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getIdentifier();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
    }

    public void test_getLong() throws IOException {
        Tokenizer tokenizer = new Tokenizer("2147483648");
        this.m_t = tokenizer;
        assertEquals(2147483648L, tokenizer.getLong());
        Tokenizer tokenizer2 = new Tokenizer("-10");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getLong();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer3 = new Tokenizer("19_identifier");
        this.m_t = tokenizer3;
        try {
            tokenizer3.getLong();
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
    }

    public void test_getUInt32() throws IOException {
        Tokenizer tokenizer = new Tokenizer("2882400018");
        this.m_t = tokenizer;
        assertEquals(2882400018L, tokenizer.getUInt32());
        Tokenizer tokenizer2 = new Tokenizer("4294967296");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getUInt32();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer3 = new Tokenizer("-12345");
        this.m_t = tokenizer3;
        try {
            tokenizer3.getUInt32();
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
    }

    public void test_getUInt16() throws IOException {
        Tokenizer tokenizer = new Tokenizer("43981");
        this.m_t = tokenizer;
        assertEquals(43981, (long) tokenizer.getUInt16());
        Tokenizer tokenizer2 = new Tokenizer("65536");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getUInt16();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer3 = new Tokenizer("-125");
        this.m_t = tokenizer3;
        try {
            tokenizer3.getUInt16();
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
    }

    public void test_getUInt8() throws IOException {
        Tokenizer tokenizer = new Tokenizer("205");
        this.m_t = tokenizer;
        assertEquals(205, (long) tokenizer.getUInt8());
        Tokenizer tokenizer2 = new Tokenizer("256");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getUInt8();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer3 = new Tokenizer("-12");
        this.m_t = tokenizer3;
        try {
            tokenizer3.getUInt8();
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
    }

    public void test_getTTL() throws IOException {
        Tokenizer tokenizer = new Tokenizer("59S");
        this.m_t = tokenizer;
        assertEquals(59, tokenizer.getTTL());
        Tokenizer tokenizer2 = new Tokenizer("2147483647");
        this.m_t = tokenizer2;
        assertEquals(TTL.MAX_VALUE, tokenizer2.getTTL());
        Tokenizer tokenizer3 = new Tokenizer("2147483648");
        this.m_t = tokenizer3;
        assertEquals(TTL.MAX_VALUE, tokenizer3.getTTL());
        Tokenizer tokenizer4 = new Tokenizer("Junk");
        this.m_t = tokenizer4;
        try {
            tokenizer4.getTTL();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
    }

    public void test_getTTLLike() throws IOException {
        Tokenizer tokenizer = new Tokenizer("59S");
        this.m_t = tokenizer;
        assertEquals(59, tokenizer.getTTLLike());
        Tokenizer tokenizer2 = new Tokenizer("2147483647");
        this.m_t = tokenizer2;
        assertEquals(TTL.MAX_VALUE, tokenizer2.getTTLLike());
        Tokenizer tokenizer3 = new Tokenizer("2147483648");
        this.m_t = tokenizer3;
        assertEquals(2147483648L, tokenizer3.getTTLLike());
        Tokenizer tokenizer4 = new Tokenizer("Junk");
        this.m_t = tokenizer4;
        try {
            tokenizer4.getTTLLike();
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
    }

    public void test_getName() throws IOException, TextParseException {
        Name root = Name.fromString(".");
        this.m_t = new Tokenizer("junk");
        assertEquals(Name.fromString("junk."), this.m_t.getName(root));
        Name rel = Name.fromString("you.dig");
        Tokenizer tokenizer = new Tokenizer("junk");
        this.m_t = tokenizer;
        try {
            tokenizer.getName(rel);
            fail("RelativeNameException not thrown");
        } catch (RelativeNameException e) {
        }
        Tokenizer tokenizer2 = new Tokenizer("");
        this.m_t = tokenizer2;
        try {
            tokenizer2.getName(root);
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
    }

    public void test_getEOL() throws IOException {
        Tokenizer tokenizer = new Tokenizer("id");
        this.m_t = tokenizer;
        tokenizer.getIdentifier();
        try {
            this.m_t.getEOL();
        } catch (TextParseException e) {
            fail(e.getMessage());
        }
        Tokenizer tokenizer2 = new Tokenizer(Base64.LINE_SEPARATOR);
        this.m_t = tokenizer2;
        try {
            tokenizer2.getEOL();
            this.m_t.getEOL();
        } catch (TextParseException e2) {
            fail(e2.getMessage());
        }
        Tokenizer tokenizer3 = new Tokenizer("id");
        this.m_t = tokenizer3;
        try {
            tokenizer3.getEOL();
            fail("TextParseException not thrown");
        } catch (TextParseException e3) {
        }
    }

    public void test_getBase64() throws IOException {
        byte[] exp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Tokenizer tokenizer = new Tokenizer("AQIDBAUGBwgJ");
        this.m_t = tokenizer;
        assertEquals(exp, tokenizer.getBase64());
        Tokenizer tokenizer2 = new Tokenizer("AQIDB AUGB   wgJ");
        this.m_t = tokenizer2;
        assertEquals(exp, tokenizer2.getBase64());
        Tokenizer tokenizer3 = new Tokenizer("AQIDBAUGBwgJ\nAB23DK");
        this.m_t = tokenizer3;
        assertEquals(exp, tokenizer3.getBase64());
        Tokenizer tokenizer4 = new Tokenizer(Base64.LINE_SEPARATOR);
        this.m_t = tokenizer4;
        assertNull(tokenizer4.getBase64());
        Tokenizer tokenizer5 = new Tokenizer(Base64.LINE_SEPARATOR);
        this.m_t = tokenizer5;
        try {
            tokenizer5.getBase64(true);
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer6 = new Tokenizer("not_base64");
        this.m_t = tokenizer6;
        try {
            tokenizer6.getBase64(false);
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
        Tokenizer tokenizer7 = new Tokenizer("not_base64");
        this.m_t = tokenizer7;
        try {
            tokenizer7.getBase64(true);
            fail("TextParseException not thrown");
        } catch (TextParseException e3) {
        }
    }

    public void test_getHex() throws IOException {
        byte[] exp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Tokenizer tokenizer = new Tokenizer("0102030405060708090A0B0C0D0E0F");
        this.m_t = tokenizer;
        assertEquals(exp, tokenizer.getHex());
        Tokenizer tokenizer2 = new Tokenizer("0102030 405 060708090A0B0C      0D0E0F");
        this.m_t = tokenizer2;
        assertEquals(exp, tokenizer2.getHex());
        Tokenizer tokenizer3 = new Tokenizer("0102030405060708090A0B0C0D0E0F\n01AB3FE");
        this.m_t = tokenizer3;
        assertEquals(exp, tokenizer3.getHex());
        Tokenizer tokenizer4 = new Tokenizer(Base64.LINE_SEPARATOR);
        this.m_t = tokenizer4;
        assertNull(tokenizer4.getHex());
        Tokenizer tokenizer5 = new Tokenizer(Base64.LINE_SEPARATOR);
        this.m_t = tokenizer5;
        try {
            tokenizer5.getHex(true);
            fail("TextParseException not thrown");
        } catch (TextParseException e) {
        }
        Tokenizer tokenizer6 = new Tokenizer("not_hex");
        this.m_t = tokenizer6;
        try {
            tokenizer6.getHex(false);
            fail("TextParseException not thrown");
        } catch (TextParseException e2) {
        }
        Tokenizer tokenizer7 = new Tokenizer("not_hex");
        this.m_t = tokenizer7;
        try {
            tokenizer7.getHex(true);
            fail("TextParseException not thrown");
        } catch (TextParseException e3) {
        }
    }
}
