package tk.mybatis.mapper.test.country;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.mapper.CountryMapper;
import tk.mybatis.mapper.mapper.MybatisHelper;
import tk.mybatis.mapper.model.Country;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过主键查询
 *
 * @author liuzh
 */
public class TestSelectByPrimaryKey {

    /**
     * 根据PK进行查询
     */
    @Test
    public void testDynamicSelectByPrimaryKey2() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Country country = mapper.selectByPrimaryKey(35);

            Assert.assertNotNull(country);
            Assert.assertEquals(true, country.getId() == 35);
            Assert.assertEquals("China", country.getCountryname());
            Assert.assertEquals("CN", country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 包含主键的对象做参数就行
     */
    @Test
    public void testDynamicSelectByPrimaryKey() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Country country = new Country();
            country.setId(35);
            country = mapper.selectByPrimaryKey(country);
            Assert.assertNotNull(country);
            Assert.assertEquals(true, country.getId() == 35);
            Assert.assertEquals("China", country.getCountryname());
            Assert.assertEquals("CN", country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询不存在的结果
     */
    @Test
    public void testDynamicSelectByPrimaryKeyZero() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Assert.assertNull(mapper.selectByPrimaryKey(new Country()));
            Assert.assertNull(mapper.selectByPrimaryKey(new HashMap<String,Object>()));
            Assert.assertNull(mapper.selectByPrimaryKey(-10));
            Assert.assertNull(mapper.selectByPrimaryKey(0));
            Assert.assertNull(mapper.selectByPrimaryKey(1000));
            Assert.assertNull(mapper.selectByPrimaryKey(null));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Map可以随意
     */
    @Test
    public void testSelectByPrimaryKeyMap() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);

            Map map = new HashMap();
            map.put("id", 35);
            Country country = mapper.selectByPrimaryKey(map);
            Assert.assertNotNull(country);
            Assert.assertEquals(true, country.getId() == 35);
            Assert.assertEquals("China", country.getCountryname());
            Assert.assertEquals("CN", country.getCountrycode());

            map = new HashMap();
            map.put("countryname", "China");
            Assert.assertNull(mapper.selectByPrimaryKey(map));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 对象不包含主键
     */
    @Test(expected = Exception.class)
    public void testDynamicDeleteNotFoundKeyProperties() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.selectByPrimaryKey(new Key());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 主键格式错误
     */
    @Test
    public void testDynamicDeleteException() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.selectByPrimaryKey(100);
        } finally {
            sqlSession.close();
        }
    }

    class Key {
    }
}
