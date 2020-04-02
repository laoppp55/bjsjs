package com.bizwink.search.service;


import com.bizwink.search.domain.Letter;
import com.bizwink.search.persistence.LetterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by kang on 2018/12/17.
 */
@Service
public class LetterService {
    @Autowired
    private LetterMapper letterMapper;

    public int insert(Letter record){
        return letterMapper.insert(record);
    }

    public List<Letter> getDepartment(){
        return letterMapper.getDepartment();
    }

    public List<Letter> getCategory(){
        return letterMapper.getCategory();
    }

    public Letter getLetter(Map<String, Object> param){
        return letterMapper.getLetter(param);
    }

    public int countLetterList(Map<String, Object> param){
        return  letterMapper.countLetterList(param);
    }

    public List<Letter> getLetterList(Map<String, Object> param){
        return letterMapper.getLetterList(param);
    }

}


