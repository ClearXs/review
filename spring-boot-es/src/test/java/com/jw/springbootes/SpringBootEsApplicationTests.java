package com.jw.springbootes;

import com.jw.springbootes.dto.ESProductDTO;
import com.jw.springbootes.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@SpringBootTest
class SpringBootEsApplicationTests {

    @Autowired
    private ProductRepository repository;

    @Test
    void contextLoads() {

    }

    /**
     * 保存操作，传递实体对象
     */
    @Test
    public void testInsert() {
        ESProductDTO productDTO = new ESProductDTO();
        productDTO.setId(1);
        productDTO.setName("测试");
        productDTO.setSellPoint("测试点");
        productDTO.setDescription("测试描述");
        productDTO.setCategoryId(1);
        productDTO.setCategoryName("测试分类");
        repository.save(productDTO);
    }

    /**
     * 更新操作，与保存调用的是同一个方法
     */
    @Test
    public void testUpdate() {
        ESProductDTO productDTO = new ESProductDTO();
        productDTO.setId(1);
        productDTO.setName("测试1");
        repository.save(productDTO);
    }

    /**
     * 删除操作
     */
    @Test
    public void testDelete() {
        repository.deleteById(1);
    }

    /**
     * 查询全部
     */
    @Test
    public void testSelectAll() {
        Iterable<ESProductDTO> all = repository.findAll();
        for (ESProductDTO productDTO : all) {
            System.out.println(productDTO);
        }
    }

    /**
     * 查找一个
     */
    @Test
    public void testSelectById() {
        if (repository.findById(1).isPresent()) {
            ESProductDTO productDTO = repository.findById(1).get();
            System.out.println(productDTO);
        }
    }

    @Test
    public void testBatchInsert() {
        for (int i = 0; i < 100; i++) {
            ESProductDTO productDTO = new ESProductDTO();
            productDTO.setId(i);
            productDTO.setName("测试" + i);
            productDTO.setSellPoint("测试点" + i);
            productDTO.setDescription("测试描述");
            productDTO.setCategoryId(1);
            productDTO.setCategoryName("测试分类");
            repository.save(productDTO);
        }
    }

    @Test
    public void testFindByName() {
        ESProductDTO productDTO = repository.findByName("测试0");
        System.out.println(productDTO);
    }

    @Test
    public void testFindByNameLike() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(0, 10, sort);
        Page<ESProductDTO> productDTOS = repository.findByNameLike("测试", page);
        System.out.println(productDTOS.getTotalElements());
    }

    @Test
    public void testSearch() {
        Page<ESProductDTO> search = repository.search(1, "测试1", PageRequest.of(0, 100, Sort.Direction.DESC, "id"));
        for (ESProductDTO productDTO : search.getContent()) {
            System.out.println(productDTO);
        }
    }
}
