package com.my.attence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.attence.modal.Vo.AttachVo;
import com.my.attence.modal.Vo.AttachVoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemoMapper extends BaseMapper {
    long countByExample(AttachVoExample example);

    int deleteByExample(AttachVoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AttachVo record);

    int insertSelective(AttachVo record);

    List<AttachVo> selectByExample(AttachVoExample example);

    AttachVo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByExample(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByPrimaryKeySelective(AttachVo record);

    int updateByPrimaryKey(AttachVo record);
}