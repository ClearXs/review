package com.jw.anno;

@GatewayClass(desc = "用户")
public class UserRepository extends Repository<String> {

    @GatewayMethod(methodName = "query")
    @Override
    protected String query(int id) {
        return String.valueOf(id);
    }
}
