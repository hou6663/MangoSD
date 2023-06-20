package work.hou6663.mango.util;

import java.util.Random;

public  class  GetRandom {
    public static String generateVerificationCode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("验证码长度必须大于0");
        }

        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(random.nextInt(10)); // 生成0-9之间的随机数字
        }
        return codeBuilder.toString();
    }
}
