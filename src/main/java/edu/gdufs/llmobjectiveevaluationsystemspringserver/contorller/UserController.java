package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AddIdentityDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AddUserDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.LoginDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.UserService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${jwt.duration-hours}")
    private long durationHours;

    /**
     * 登录
     * <p>1.使用用户名{@code username}查询数据库{@code user}表。</p>
     * <p>2.校验密码 {@code password}</p>
     * <p>3.校验成功则生成JWT令牌，并存储到Redis中</p>
     * <p>4.返回结果</p>
     * @return {@link NormalResult}
     */
    @PostMapping("/login")
    public NormalResult<?> login(@RequestBody LoginDto dto) {
        User user = userService.getUser(dto.getUsername());
        if (user != null && user.getPassword().equals(dto.getPassword())) {
            UserInfo userInfo = userService.getUserInfo(user.getUserId());
            userInfo.setUserid(user.getUserId());
            userInfo.setUsername(user.getUserName());
            userInfo.setNickname(user.getNickname());
            String token = jwtUtil.generateToken(user.getUserId(), user.getNickname(), userInfo.getIdentity());
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token, token, durationHours, TimeUnit.HOURS);
            userInfo.setToken(token);
            return NormalResult.success(userInfo);
        }
        return NormalResult.error(NormalResult.IDENTIFICATION_ERROR);
    }

    /**
     * 添加用户
     * <p>1.检查管理员令牌</p>
     * <p>2.检查用户名{@code username}是否已存在</p>
     * <p>3.不存在则添加新用户</p>
     * <p>3.返回结果</p>
     * @return {@link NormalResult}
     */
    @PostMapping("/add")
    public NormalResult<?> addUser(@RequestBody AddUserDto dto) {
        if (userService.checkAccessToken(dto.getAccessToken())) {
            User user = userService.getUser(dto.getUsername());
            if (user == null) {
                userService.addUser(dto.getUsername(), dto.getPassword(), dto.getNickname());
                return NormalResult.success();
            }
            return NormalResult.error(NormalResult.EXISTENCE_ERROR);
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    /**
     * 添加用户身份
     * <p>1.检查管理员令牌</p>
     * <p>2.检查通过则为该{@code userId}添加新身份</p>
     * <p>3.返回结果</p>
     * @return {@link NormalResult}
     */
    @PostMapping("/identity")
    public NormalResult<?> addIdentity(@RequestBody AddIdentityDto dto) {
        if (userService.checkAccessToken(dto.getAccessToken())) {
            userService.addIdentity(dto.getUserId(), dto.getIdentity());
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    /**
     * 获取用户信息
     * <p>1.从除了{@code userId}的参数中列表中，查询其相应的{@code userId}组成集合</p>
     * <p>2.使用{@code userId}查询用户信息</p>
     * <p>3.返回结果</p>
     * @return {@link NormalResult}
     */
    @GetMapping("/info")
    public NormalResult<?> listUsers(@RequestParam("userId") List<Long> userId,
                                     @RequestParam("teacherId") List<Long> teacherId,
                                     @RequestParam("studentId") List<Long> studentId,
                                     @RequestParam("administratorId") List<Long> administratorId) {
        Set<Long> set = userService.getUserSet(userId, teacherId, studentId, administratorId);
        List<UserInfo> userList = new ArrayList<>();
        for (Long id : set) {
            User user = userService.getUser(id);
            UserInfo userInfo = userService.getUserInfo(user.getUserId());
            userInfo.setUserid(user.getUserId());
            userInfo.setNickname(user.getNickname());
            userInfo.setUsername(user.getUserName());
            userList.add(userInfo);
        }
        return NormalResult.success(userList);
    }

    /**
     * 添加用户身份
     * <p>1.检查管理员令牌</p>
     * <p>2.检查通过则删除用户{@code userId}</p>
     * <p>3.返回结果</p>
     * @return {@link NormalResult}
     */
    @DeleteMapping("/remove")
    public NormalResult<?> removeUser(@RequestParam("userId") long userId, @RequestParam("accessToken") String accessToken) {
        if (userService.checkAccessToken(accessToken)) {
            userService.removeUser(userId);
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    /**
     * 修改昵称
     * @return {@link NormalResult}
     */
    @PatchMapping("/modify")
    public NormalResult<?> modifyNickname(@RequestParam("userId") long userId, @RequestParam("nickname") String nickname) {
        userService.modifyNickname(userId, nickname);
        return NormalResult.success();
    }

    /**
     * 使用昵称模糊搜索用户
     * @return {@link NormalResult}
     */
    @GetMapping("/list")
    public NormalResult<?> searchUsers(@RequestParam("nickname") String nickname) {
        List<User> list = userService.searchUser(nickname);
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (User user : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userid", user.getUserId());
            map.put("nickname", user.getNickname());
            result.add(map);
        }
        return NormalResult.success(result);
    }

}
