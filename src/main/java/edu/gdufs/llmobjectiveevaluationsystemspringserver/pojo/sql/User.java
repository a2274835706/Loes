package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long userId;
    private String userName;
    private String password;
    private String nickname;
    private LocalDate createAt;
    private LocalDate updateAt;

}
