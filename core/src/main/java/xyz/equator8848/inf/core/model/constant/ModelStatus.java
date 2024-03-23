package xyz.equator8848.inf.core.model.constant;

/**
 * @author libinkai
 * @date 2020/11/1 1:54 下午
 */
public class ModelStatus {
    /**
     * 无意义的主键
     */
    public static final Long DUMMY_ID = 0L;

    public static final class BooleanVal {
        public final static Integer TRUE = 1;
        public final static Integer FALSE = 0;

        public static Boolean getBoolean(Integer val) {
            return TRUE.equals(val) ? Boolean.TRUE : Boolean.FALSE;
        }

        public static Integer getInteger(boolean val) {
            return val ? TRUE : FALSE;
        }
    }

    public static class DelFlag {
        public final static Short DELETED = 1;
        public final static Short NORMAL = 0;

        public static String getStatusStr(short status) {
            if (status == DELETED) {
                return "删除";
            }
            return "正常";
        }
    }

}
