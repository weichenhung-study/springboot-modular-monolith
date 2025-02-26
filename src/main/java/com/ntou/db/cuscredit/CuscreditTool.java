package com.ntou.db.cuscredit;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
public class CuscreditTool {
    @AllArgsConstructor
    public enum CardApprovalStatus{
        PROCESS       ("00" , "處理中")
        , PASS        ("01" , "審核通過")
        , NOTPASS     ("02" , "審核不通過")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static CuscreditTool.CardApprovalStatus find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }
    @AllArgsConstructor
    public enum ActivationRecord{
        OPEN       ("00" , "已開卡")
        , NOTOPEN        ("01" , "未開卡")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static CuscreditTool.ActivationRecord find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }
    @AllArgsConstructor
    public enum STATUS{
        OK       ("00" , "正常")
        , CXL        ("01" , "註銷")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static CuscreditTool.STATUS find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }
}
