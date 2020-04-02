package com.bizwink.cms.server;

import com.bizwink.cms.util.*;
import com.bizwink.webapps.collection.ICollectionManager;
import com.bizwink.webapps.survey.answer.IAnswerManager;
import com.bizwink.webapps.survey.define.IDefineManager;
import com.bizwink.webapps.survey.result.IResultManager;


public interface IFactory {
    //EncodingUtil                 getEncodingUtil();
    ISequenceManager             getSequenceManager();

    //for survey
    IAnswerManager                  getAnswerManager();
    IDefineManager                  getDefineManager();
    IResultManager                  getResultManager();

    //for Collcetion
    ICollectionManager              getCollectionManager();
}
