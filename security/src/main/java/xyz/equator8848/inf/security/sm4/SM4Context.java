package xyz.equator8848.inf.security.sm4;

/**
 * @author Equator
 */
public class SM4Context {
    public int mode = 1;
    public long[] sk = new long[32];
    public boolean isPadding = true;

    protected SM4Context() {
    }
}
