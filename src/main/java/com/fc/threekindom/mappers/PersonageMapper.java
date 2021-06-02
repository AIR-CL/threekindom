package com.fc.threekindom.mappers;

import com.fc.threekindom.pojo.Personage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonageMapper {
    List<Personage> findAll();
    //新增三国人物资料
    int addNewPerson(Personage personage);
    //通过id查找人物
    Personage findPersonById(Integer id);
    //通过id修改人物信息
    int modifyInfo(Personage personage);
    //不修改图片修改信息
    int modifyInfoWithoutPic(Personage personage);
    //通过id删除
    int deletePersonById(Integer id);
    //模糊搜索
    List<Personage> likeSearch(String keyWord);


}
