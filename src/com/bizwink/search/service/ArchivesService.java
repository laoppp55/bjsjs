package com.bizwink.search.service;

import com.bizwink.search.domain.Archives;
import com.bizwink.search.persistence.ArchivesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by kang on 2018/11/23.
 */
@Service
public class ArchivesService {

    @Autowired
    private ArchivesMapper archivesMapper;

    public int countArchivesList(Map<String, Object> param){
        return  archivesMapper.countArchivesList(param);
    }

    public List<Archives> getArchivesList(Map<String, Object> param){
        return archivesMapper.getArchivesList(param);
    }

    public int countArchivessList(Map<String, Object> param){
        return  archivesMapper.countArchivessList(param);
    }

    public List<Archives> getArchivessList(Map<String, Object> param){
        return archivesMapper.getArchivessList(param);
    }

}
