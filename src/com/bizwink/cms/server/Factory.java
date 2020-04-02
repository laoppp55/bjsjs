package com.bizwink.cms.server;

import com.bizwink.cms.util.*;

import com.bizwink.webapps.collection.CollectionPeer;
import com.bizwink.webapps.collection.ICollectionManager;
import com.bizwink.webapps.survey.answer.*;
import com.bizwink.webapps.survey.define.*;
import com.bizwink.webapps.survey.result.*;

public class Factory implements IFactory {
    PoolServer cpool;

    ISequenceManager         sequencePeer;
    //EncodingUtil             encodingUtil;

    //for survey
    IAnswerManager         answerPeer;
    IDefineManager         definePeer;
    IResultManager         resultPeer;

    //For Collection
    ICollectionManager     collectionPeer;


    public Factory(PoolServer cpool) {
        this.cpool = cpool;

        sequencePeer = new SequencePeer(cpool);
        //encodingUtil = new EncodingUtil();

        //for survey
        answerPeer = new AnswerPeer(cpool);
        definePeer = new DefinePeer(cpool);
        resultPeer = new ResultPeer(cpool);

        //For Collection
        collectionPeer = new CollectionPeer(cpool);
    }

    public PoolServer getConnectionPool() {
        return cpool;
    }


    public ISequenceManager getSequenceManager() {
        return sequencePeer;
    }
    /*public EncodingUtil getEncodingUtil() {
        return encodingUtil;
    }*/

    //for survye
    public IAnswerManager getAnswerManager()
    {
        return answerPeer;
    }
    public IDefineManager getDefineManager(){
        return definePeer;
    }
    public IResultManager getResultManager() { return  resultPeer;}

    //For Collection
    public ICollectionManager getCollectionManager() {return collectionPeer;}
}