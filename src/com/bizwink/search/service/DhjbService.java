package com.bizwink.search.service;

import com.bizwink.search.domain.ReportGangdom;
import com.bizwink.search.persistence.DhjbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kang on 2018/8/15.
 */
@Service
public class DhjbService {

    @Autowired
    private DhjbMapper dhjbMapper;

    public int submitMessage(ReportGangdom reportGangdom){
        return dhjbMapper.submitMessage(reportGangdom);
    }


}
