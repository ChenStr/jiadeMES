package com.BSMES.jd.common.service.impl;

import com.BSMES.jd.common.dto.BaseDTO;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.tools.ConvertUtils;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 浮桥的Service层实现类(方法围绕DTO)
 *
 * M 为 DAO 层
 *
 * T 为 Entity
 *
 * D 为 DTO
 *
 * @author DNYY
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T> , T , D> extends DeepServiceImpl<M,T> implements BaseService<T , D> {

    //获得T(Entity)的类名
    protected Class<T> currentEntityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    protected Class<D> currentDtoClass(){
        return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    /**
     * 保存方法
     * @param dto
     */
    @Override
    public void insert(D dto) {
        //保存方法前使用的方法
        beforeInsert(dto);
        T data = ConvertUtils.convert(dto,currentEntityClass());
        this.save(data);
    }

    /**
     * 通过id查找一条数据
     * @param id
     * @return
     */
    @Override
    public D selectById(String id){
        T data = this.getById(id);
        D d = ConvertUtils.convert(data,currentDtoClass());
        return d;
    }

    /**
     * 根据许多id来查找多条数据
     * @param ids
     * @return
     */
    @Override
    public List<D> selectByIds(List<String> ids){
        List<T> data = (List<T>) this.listByIds(ids);
        List<D> nmsl = new ArrayList<>();
        for (T entity : data) {
            D d = ConvertUtils.convert(entity,currentDtoClass());
            nmsl.add(d);
        }
        return nmsl;
    }

    /**
     * 修改方法
     * @param dto
     */
    @Override
    public void edit(D dto){
        //编辑方法前的前置方法
        beforEedit(dto);
        T data = ConvertUtils.convert(dto,currentEntityClass());
        this.updateById(data);
    }


    /**
     * 根据id来删除单条数据
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(String id){
        Boolean flag = this.removeById(id);
        return flag;
    }

    /**
     * 根据许多id来删除多条数据
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteByIds(List<String> ids){
        Boolean flag = this.removeByIds(ids);
        return flag;
    }

    /**
     * 自定义查找方法
     * @param data
     * @return
     */
    @Override
    public List<D> select(Map<String,Object> data){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for ( String key : data.keySet() ) {
            if (data.get(key)!=null){
                wrapper.eq(key,data.get(key));
            }
        }
        List<T> list = this.list(wrapper);
        List<D> dList = ConvertUtils.convert(list, currentDtoClass());
        return dList;
    }

    /**
     * 自定义查找方法
     * @param wrapper
     * @return
     */
    @Override
    public List<D> select(QueryWrapper<T> wrapper){
        List<T> list = this.list(wrapper);
        List<D> dList = ConvertUtils.convert(list, currentDtoClass());
        return dList;
    }

    /**
     * mybatisPlus分页方法
     * @param page
     * @return
     */
    @Override
//    public List<D> selectPage(Object page,Object pageSize, QueryWrapper<T> queryWrapper){
    public IPage<T> selectPage(Object page,Object pageSize, QueryWrapper<T> queryWrapper){
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        int Page,PageSize;
        try{
            Page = (int) page;
            PageSize = (int) pageSize;
        }catch (Exception e){
            page = 1;
            pageSize = 10;
        }
        IPage<T> Ipage = new Page<>((int)page, (int)pageSize);
        //获取页数
        baseMapper.selectPage(Ipage,queryWrapper);
//        List<T> pages = Ipage.getRecords();
//        List<D> date = ConvertUtils.convert(pages,currentDtoClass());
        return baseMapper.selectPage(Ipage,queryWrapper);
    }

    @Override
    public D selectOne(QueryWrapper<T> wrapper) {
        //单条查询，如果查询到多条数据不抛出错误 true：抛出错误 false：不抛出错误
        T data = this.getOne(wrapper,false);
        D d = ConvertUtils.convert(data,currentDtoClass());
        return d;
    }








    /**
     * 在添加方法之前执行的方法
     * @param dto
     * @return dto
     */
    public abstract void beforeInsert(D dto);

    /**
     * 在编辑之前执行的方法
     * @param dto
     */
    public abstract void beforEedit(D dto);


    /**
     * 生成单号的方法
     * @param codeval
     * @param val
     * @param baseService
     * @param baseDTO
     * @return
     */
    public synchronized String getKey(String codeval, String val, BaseService baseService, BaseDTO baseDTO){
        //首先先找到编码规则
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sname",codeval);
        String code = (String) MyUtils.getFieldValueByFieldName("sbds",baseService.selectOne(queryWrapper));
        String string = "";
        //获取括号前的数据{
        Integer place1 = code.indexOf('%');
        if (place1>=0 ){
            String befor = code.substring(0,place1);
            String after = code.substring(place1+1,code.length());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date());
            //将年月日加到后面
            string = string+befor+dateNowStr;
            //查询数据库里的全部值
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.likeRight(val,string);
            queryWrapper1.select(val);
            List<BaseDTO> list = select(queryWrapper1);
            if (list!=null && list.size()!=0){
                List<Integer> ints = new ArrayList<>();
                //将单号后面的字母转换为数字
                for (int i = 0 ; i < list.size() ; i++){
//                    ints.add(Integer.valueOf(list.get(i).getSid().substring(string.length(),list.get(i).getSid().length())));

                    String temp = (String)MyUtils.getFieldValueByFieldName(MyUtils.lineToHump(val),list.get(i));
                    ints.add(Integer.valueOf(temp.substring(string.length(),temp.length())));
                }
                ints = ints.stream().sorted((t1,t2)-> t2 - t1).collect(Collectors.toList());
                //返回单号
                string = string + MyUtils.geFourNumber(ints.get(0)+1,after.length());
            }else{
                //返回单号
                string = string + MyUtils.geFourNumber(1,after.length());
            }

            return string;
        }else{
            return code;
        }
    }



}
