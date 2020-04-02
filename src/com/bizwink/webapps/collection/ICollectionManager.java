package com.bizwink.webapps.collection;

public interface ICollectionManager {

    public int addCollectionInfo(Collection collection) throws CollectionException;

    public int addCollectionInfo(SimpleCollection collection) throws CollectionException;
}
