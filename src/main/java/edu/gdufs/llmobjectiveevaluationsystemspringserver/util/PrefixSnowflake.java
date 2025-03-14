package edu.gdufs.llmobjectiveevaluationsystemspringserver.util;

import cn.hutool.core.lang.Snowflake;

public class PrefixSnowflake {

    public static final String PREFIX_USER = "user";
    public static final String PREFIX_STUDENT = "stud";
    public static final String PREFIX_TEACHER = "teac";
    public static final String PREFIX_ADMINISTRATOR = "admi";
    public static final String PREFIX_COURSE = "cour";
    public static final String PREFIX_CLASS = "clas";
    public static final String PREFIX_ASSIGNMENT = "assi";
    public static final String PREFIX_ASSIGNMENT_QUESTION = "asqu";
    public static final String PREFIX_QUESTION = "ques";
    public static final String PREFIX_RELEASE_ASSIGNMENT = "reas";
    public static final String PREFIX_SUBMISSION = "subm";



    private final Snowflake snowflake;

    public PrefixSnowflake(long workerId, long dataCenterId) {
        this.snowflake = new Snowflake(workerId, dataCenterId);
    }

    public String nextPrefixId(String prefix) {
        return prefix + "_" + snowflake.nextId();
    }

    public long nextId() {
        return snowflake.nextId();
    }

}
