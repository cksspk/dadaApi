package com.ckss.project.dada.mp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckss.common.utils.IdWorker;
import com.ckss.project.dadafront.entity.DadaOrderVO;
import com.ckss.project.dadafront.mapper.DdOrderMapper;
import com.ckss.project.dadafront.service.DdOrderService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author cks
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MyBatisPlusTest {

//    idworker Test
    @Autowired
    private IdWorker idWorker;

    @Test
    public void idWorkerTest(){
        long l = System.currentTimeMillis();
        System.out.println(l);
        for (int i = 0; i < 10; i++) {
            System.out.println(idWorker.nextId());
        }
    }


    @Autowired
    private DdOrderMapper dadaMapper;

    @Autowired
    private DdOrderService dadaOrderSer;
    /**
     * 逻辑删除
     */
    @Test
    public void deleteLogicTest(){
        String id = "1272794351667376129";

//        dadaMapper.selectOne(Wrappers.<DadaOrder>lambdaQuery().eq(DadaOrder::getId,id));
        int i = dadaMapper.deleteById(id);
        System.out.println(i);
    }




    /**
     * 分页查找
     */
    @Test
    public void selectPageTest(){
//        dadaOrderSer.page
        Page page = new Page<DadaOrderVO>(1,10);
        Page page1 = dadaOrderSer.page(page);
//        dadaMapper.selectMapsPage(page, null);
        List records = page1.getRecords();
        records.forEach(s-> System.out.println(s));
    }
    /**
     * 分页查找
     */
    @Test
    public void selectPageTest1(){
//        dadaOrderSer.page
        Page page = new Page<DadaOrderVO>(1,10);
        dadaMapper.selectMapsPage(page, null);
        List records = page.getRecords();
        records.forEach(s-> System.out.println(s));
    }


    @Test
    public void selectTest(){
        String id = "1272794351667376129";
        DadaOrderVO dadaOrder = dadaMapper.selectOne(Wrappers.<DadaOrderVO>lambdaQuery()
                .eq(DadaOrderVO::getId, id));

        System.out.println(dadaOrder);
    }


    @Test
    public void listTest(){
        //查询所有
        List<DadaOrderVO> dadaOrders = dadaMapper.selectList(null);
        System.out.println();
        dadaOrders.forEach(s-> System.out.println(s.getId()));
    }

    //add Test
    @Test
    public void addTest(){
        DadaOrderVO dadaOrder = new DadaOrderVO();
//        dadaOrder.setId("22");
        dadaOrder.setOdPrice(BigDecimal.valueOf(1.36));


        int insert = dadaMapper.insert(dadaOrder);

        System.out.println(insert);
    }

    //update Test
    @Test
    public void updateTest(){
        DadaOrderVO dadaOrder = new DadaOrderVO();
//        dadaOrder.setId("22");
        dadaOrder.setId("1272794351667376129");
        dadaOrder.setOdPrice(BigDecimal.valueOf(3.99));
        dadaOrder.setReceiverAddress("111");

        int i = dadaMapper.updateById(dadaOrder);

        System.out.println(i);
    }


    @Test
    public void test(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        Date date = new Date();
        System.out.println(date);
    }

    @Data
    class DadaOrder{

        private String id;

        private BigDecimal odPrice;

        private String receiverName;

        private String receiverAddress;

        private Double receiverLng;

        private Double receiverLat;


        @ApiModelProperty(value = "收货人号码")
        private String receiverPhone;
    }

}
