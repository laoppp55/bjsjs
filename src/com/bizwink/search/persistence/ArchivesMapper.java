package com.bizwink.search.persistence;

import com.bizwink.search.domain.Archives;

import java.util.List;
import java.util.Map;

/**
 * Created by kang on 2018/11/23.
 */
public interface ArchivesMapper {

    int countArchivesList(Map<String, Object> param);

    List<Archives> getArchivesList(Map<String, Object> param);

    int countArchivessList(Map<String, Object> param);

    List<Archives> getArchivessList(Map<String, Object> param);
}
