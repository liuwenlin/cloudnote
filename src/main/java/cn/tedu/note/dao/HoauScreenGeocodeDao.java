package cn.tedu.note.dao;

import cn.tedu.note.entity.HoauScreenGeocodeEntity;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/19 15:05
 */
@Repository
public interface HoauScreenGeocodeDao {

    /**
     * 返回经纬度为空的数据
     * @return
     */
    @MapKey("id")
    Map<String, HoauScreenGeocodeEntity> getHoauScreenGeocodeMapInfo();

    /**
     * 更新查询出来的数据中的经纬度
     * @param list
     * @return
     */
    int updateHoauScreenGeocode(List<HoauScreenGeocodeEntity> list);

}
