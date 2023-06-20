package work.hou6663.mango.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SameService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 按条件分页查询
     *
     * @param entity    查询条件
     * @param pageNum   当前页码
     * @param pageSize  一页显示多少条
     * @return
     */
    public IPage<T> findPage(T entity, int pageNum, int pageSize) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<T> findAll() {
        return list();
    }

    /**
     * 通过条件查询
     *
     * @param entity  查询条件
     * @return
     */
    public List<T> findList(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return list(queryWrapper);
    }

    /**
     * 通过条件查询，分页查询
     *
     * @param entity    查询条件
     * @param pageNum   当前页码
     * @param pageSize  一页显示多少条
     * @return
     */
    public IPage<T> findList(T entity, int pageNum, int pageSize) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public T getById(Serializable id) {
        return super.getById(id);
    }


    /**
     * 通过id删除
     *
     * @param id
     */
    public void deleteById(Serializable id) {
        removeById(id);
    }

    /**
     * 添加信息
     *
     * @param entity
     */
    public void add(T entity) {
        save(entity);
    }

    /**
     * 更新信息
     *
     * @param entity
     */
    public void update(T entity) {
        updateById(entity);
    }

    /**
     * 返回对应条件的信息数
     *
     * @param entity
     * @return
     */
    public Integer findCount(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return count(queryWrapper);
    }

    /**
     * 通过指定条件删除
     *
     * @param entity
     */
    public void delete(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        remove(queryWrapper);
    }
}
