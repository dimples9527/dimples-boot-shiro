package com.dimples.vo;

import com.dimples.monitor.dto.StatisticDTO;
import com.dimples.system.dto.UserDetailDTO;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/8
 */
@Data
@Builder
public class IndexVO {

    private UserDetailDTO userDetail;

    private StatisticDTO statistic;

}












