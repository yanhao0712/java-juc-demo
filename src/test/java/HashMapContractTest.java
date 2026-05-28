import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class HashMapContractTest {

    // 1. 这是一个故意写错的内部类，模拟只重写 equals 的反面教材
    static class BrokenUser {
        String name;
        BrokenUser(String name) { this.name = name; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            BrokenUser that = (BrokenUser) obj;
            return this.name.equals(that.name);
        }
        // 故意【不重写】hashCode，让它默认去比内存地址
    }

    // 2. 贴上 @Test 标签，这就是你的 JUnit 练习开关
    @Test
    public void 验证只重写Equals会导致Map重复插入() {
        HashMap<BrokenUser, String> map = new HashMap<>();

        BrokenUser user1 = new BrokenUser("yy");
        BrokenUser user2 = new BrokenUser("yy"); // 名字相同，但内存地址不同

        map.put(user1, "第一份数据");
        map.put(user2, "第二份数据");

        // 【断言验证一】：由于 hashCode 契约失效，这两个逻辑上相同的人会被错误地当作两个人存进去。
        // 我们用 assertEquals(期待的size是2, 实际的size) 来验证这个漏洞
        assertEquals(2, map.size());

        // 【断言验证二】：由于散列地址对不上，这时候你用新对象 user2 去拿数据，拿出来的一定是 null
        assertNull(map.get(user2));
    }
}