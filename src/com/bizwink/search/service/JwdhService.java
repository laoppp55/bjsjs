package com.bizwink.search.service;

import com.bizwink.search.domain.ReportGangdom;
import com.bizwink.search.persistence.JwdhMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kang on 2018/9/7.
 */
@Service
public class JwdhService {

    @Autowired
    private JwdhMapper jwdhMapper;

    public int submitMessage(ReportGangdom reportGangdom){
        return jwdhMapper.submitMessage(reportGangdom);
    }

    public int getReportGangdom(String Md5str){
        return jwdhMapper.getReportGangdom(Md5str);
    }
}
