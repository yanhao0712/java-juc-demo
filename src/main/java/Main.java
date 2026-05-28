/**
 * 建议 1：将类放在平级位置，避免内部类的嵌套麻烦。
 * 建议 2：注意 Java 命名规范（类名首字母大写）。
 */

// 父类
class Employee {
    public void work() {
        System.out.println("员工正在通用办公...");
    }
}

// 子类 1
class Coder extends Employee {
    @Override
    public void work() {
        System.out.println("程序员正在写代码...");
    }

    public void fixBug() {
        System.out.println("程序员正在修 Bug (这是 Coder 的特有功能)");
    }
}

// 子类 2
class Manager extends Employee {
    @Override
    public void work() {
        System.out.println("经理正在开会...");
    }

    public void firePeople() {
        System.out.println("经理正在开除员工 (这是 Manager 的特有功能)");
    }
}

public class Main {

    /**
     * 这里加了 static，所以 main 方法可以直接调用它
     * 核心逻辑：利用多态接收参数，利用向下转型处理特殊业务
     */
    public static void handle(Employee e) {
        // 1. 无论谁进来，先做通用的工作（多态）
        e.work();

        // 2. 向下转型：根据身份触发“特权”
        // 建议使用 Java 14+ 的模式匹配写法（instanceof Coder c），更简洁
        if (e instanceof Coder) {
            Coder c = (Coder) e;
            c.fixBug();
        } else if (e instanceof Manager) {
            Manager m = (Manager) e;
            m.firePeople();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 场景 1：入职一名程序员 ---");
        Employee emp1 = new Coder(); // 向上转型
        handle(emp1);

        System.out.println("\n--- 场景 2：入职一名经理 ---");
        Employee emp2 = new Manager(); // 向上转型
        handle(emp2);
    }
}