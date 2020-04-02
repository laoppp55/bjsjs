package com.bizwink.search.persistence;

import com.bizwink.search.domain.ReportGangdom;

/**
 * Created by kang on 2018/9/7.
 */
public interface JwdhMapper {
    int submitMessage(ReportGangdom reportGangdom);

    int getReportGangdom(String Md5str);
}
