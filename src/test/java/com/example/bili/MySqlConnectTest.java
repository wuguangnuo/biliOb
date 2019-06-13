package com.example.bili;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bili.entity.PmsBrand;
import com.example.bili.mapper.PmsBrandMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySqlConnectTest {

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Test
    public void contextLoads() {

        PmsBrand p = pmsBrandMapper.selectById(1);
        List<PmsBrand> l = pmsBrandMapper.selectList(
                new QueryWrapper<PmsBrand>().lambda()
                        .eq(PmsBrand::getShowStatus, 1)
        );
        IPage<PmsBrand> i = pmsBrandMapper.selectPage(
                new Page<PmsBrand>(1, 10),
                new QueryWrapper<PmsBrand>().lambda()
                        .eq(PmsBrand::getShowStatus, 1)
        );

    }

}
