package com.jw.basics.collection.model;

public class User {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * equals作用: 判断两个对象是否相等
     * equals常用于集合中，主要是set（set中的元素）与map（map中的key）.
     * equals性质：
     *  1.自反性
     *  2.
     */
    @Override
    public boolean equals(Object obj) {
        // 1.判断当前对象是否与参数对象地址（or值）相等。
        if (this == obj) {
            return true;
        }
        // 2.判断参数对象是否是当前类类型
        if (obj instanceof User) {
            User compareUser = (User) obj;
            // 3.比较关键成员值是否相等
            return this.username.equals(compareUser.username) &&
                    this.password.equals(compareUser.password);
        }
        return false;
    }

    public static void main(String[] args) {
        User user = new User();
        System.out.println(user);
        Class<? extends User> userClass = user.getClass();
        System.out.println(userClass);

        ClassLoader classLoader = userClass.getClassLoader();

        System.out.println(classLoader); // App ClassLoader
        System.out.println(classLoader.getParent()); // Ext ClassLoader
        System.out.println(classLoader.getParent().getParent()); // null
    }
}
