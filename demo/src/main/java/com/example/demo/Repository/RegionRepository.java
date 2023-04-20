package com.example.demo.Repository;

import com.example.demo.model.Region;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegionRepository {
    @Select("SELECT * FROM REGION")
    @Result(property = "shortName", column = "short_name")
    List<Region> findAll();

    @Select("SELECT * FROM REGION WHERE id = #{id}")
    @Result(property = "shortName", column = "short_name")
    Region findById(@Param("id") Long id);

    @Insert("INSERT INTO REGION(NAME,SHORT_NAME) VALUES (#{reg.name}, #{reg.shortName})")
    @Options(useGeneratedKeys = true, flushCache = Options.FlushCachePolicy.TRUE, keyProperty = "reg.id", keyColumn = "id")
    Long insert(@Param("reg") Region region);

    @Select("SELECT NEXT VALUE FOR region_seq - 1")
    Long getLastId();

    @Delete("DELETE FROM REGION WHERE id = #{id}")
    int delete(@Param("id") Long id);

    @Update("UPDATE REGION SET name=#{reg.name}, short_name =#{reg.shortName} WHERE id =#{id}")
    int update(@Param("id") Long id, @Param("reg") Region region);
}
