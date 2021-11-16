package com.jw.unittest.springboottest.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.entity.Role;
import com.jw.unittest.mapper.RoleMapper;
import com.jw.unittest.springboottest.ContextTest;
import org.junit.jupiter.api.Test;

public class RoleMapperTest extends ContextTest {

    @Test
    void testGetRoleByIdOfXmlSql() {
        RoleMapper mapper = context.getBean(RoleMapper.class);
        assertNotNull(mapper);
        Role role = mapper.get("1299305738418200578");
        assertEquals("1299305738418200578", role.getId());
    }

}
