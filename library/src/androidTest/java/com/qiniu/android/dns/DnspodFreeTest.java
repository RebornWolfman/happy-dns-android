package com.qiniu.android.dns;

import android.test.AndroidTestCase;

import com.qiniu.android.dns.http.DnspodFree;

import junit.framework.Assert;

import java.io.IOException;

/**
 * Created by bailong on 15/6/17.
 */
public class DnspodFreeTest extends AndroidTestCase {
    public void testFound() throws IOException {
        DnspodFree resolver = new DnspodFree();
        try {
            Record[] records = resolver.resolve(new Domain("baidu.com"), null);
            Assert.assertNotNull(records);
            Assert.assertTrue(records.length > 0);
            for (Record r : records) {
                Assert.assertTrue(r.ttl >= 600);
                Assert.assertTrue(r.isA());
                Assert.assertFalse(r.isExpired());
            }

        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void testNotFound() throws IOException {
        DnspodFree resolver = new DnspodFree();
        try {
            Record[] records = resolver.resolve(new Domain("7777777.qiniu.com"), null);
            Assert.assertNull(records);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void testTimeout() throws IOException {
        DnspodFree resolver = new DnspodFree("8.1.1.1", 5);
        try {
            Record[] records = resolver.resolve(new Domain("baidu.com"), null);
            Assert.fail("no timeout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
