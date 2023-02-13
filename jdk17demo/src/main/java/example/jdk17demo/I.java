package example.jdk17demo;

/**
 * @author 沉鱼代码
 * @date 2023/2/13
 */
public interface I {


    default String getName() {
        return getName("张三");
    }

    private String getName(String name) {
        return name;
    }

}
